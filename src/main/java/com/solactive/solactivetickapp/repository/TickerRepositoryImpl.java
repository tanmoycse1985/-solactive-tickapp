package com.solactive.solactivetickapp.repository;

import com.solactive.solactivetickapp.model.StatisticsEntity;
import com.solactive.solactivetickapp.model.TickEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Repository
public class TickerRepositoryImpl implements TickerRepository {

    private Logger log = LoggerFactory.getLogger(TickerRepositoryImpl.class);

    private static final ReentrantLock lock = new ReentrantLock();
    private Map<String, List<TickEntity>> ticks  = new ConcurrentHashMap<>();
    private StatisticsEntity validStatistics = null;
    private Map<String, StatisticsEntity> tickStatisticsMap = new ConcurrentHashMap<>();

    @Override
    public void save(final TickEntity tickEntity) {
        lock.lock();
        try {
            log.info("Saving the input received :: "+tickEntity);
            ticks.computeIfAbsent(tickEntity.getInstrument(), x -> new CopyOnWriteArrayList<TickEntity>()).add(tickEntity);
            cleanup();
        } finally {
            lock.unlock();
        }
    }

    private StatisticsEntity getUpdatedStatistics(List<TickEntity> ticks){
        StatisticsEntity statisticsEntity = new StatisticsEntity();
        final List<Double> finalPrice = ticks.stream().map(x -> x.getPrice()).collect(Collectors.toList());
        final Long finalCount = finalPrice.stream().count();
        if(finalCount > 0){
            statisticsEntity.setAverage(finalPrice.stream().mapToDouble(Double::doubleValue).average().getAsDouble());
            statisticsEntity.setMax(finalPrice.stream().max(Double::compareTo).get());
            statisticsEntity.setMin(finalPrice.stream().min(Double::compareTo).get());
            statisticsEntity.setCount(finalCount);
        }
        return statisticsEntity;
    }

    private void cleanup() {
        Iterator<List<TickEntity>> ticksIterator = ticks.values().iterator();
        while (ticksIterator.hasNext()){
            ticksIterator.next().removeIf(x-> (System.currentTimeMillis() - x.getTimestamp()) > 60000);
        }
        ticks.values().removeIf(x-> x == null || x.isEmpty()) ;
        updateStatistics();
    }

    private void updateStatistics(){
        lock.lock();
        try {
            List<TickEntity>  entities = ticks.values().stream().flatMap(x -> x.stream()).collect(Collectors.toList());
            validStatistics = getUpdatedStatistics(entities);
            tickStatisticsMap.entrySet().removeIf(x-> !ticks.keySet().contains(x.getKey()));

            for(Map.Entry<String, List<TickEntity>> entry: ticks.entrySet()) {
                tickStatisticsMap.put(entry.getKey(),getUpdatedStatistics(entry.getValue()));
            }
        }finally {
            lock.unlock();
        }
    }

    @Scheduled(fixedRate = 20000)
    public void evict() {
        lock.lock();
        try {
            log.info("Evict method called");
            cleanup();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public StatisticsEntity getStatisticsByInstrumentId(final String instrumentIdentifier) {
        return tickStatisticsMap.getOrDefault(instrumentIdentifier, new StatisticsEntity());
    }

    @Override
    public StatisticsEntity getStatistics() {
        lock.lock();
        try{
            return validStatistics == null || validStatistics.getCount() == null || validStatistics.getCount() == 0
                    ? new StatisticsEntity() : validStatistics;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void resetAllTicks(){
        lock.lock();
        try{
            ticks.clear();
            validStatistics = new StatisticsEntity();
            tickStatisticsMap.clear();
        }finally {
            lock.unlock();
        }
    }
}
