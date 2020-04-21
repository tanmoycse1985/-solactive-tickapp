package com.solactive.solactivetickapp.service;

import com.solactive.solactivetickapp.dto.StatisticsDTO;
import com.solactive.solactivetickapp.dto.TicksDTO;
import com.solactive.solactivetickapp.mapper.Mapper;
import com.solactive.solactivetickapp.repository.TickerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TickerServiceImpl implements TickerService {

    @Autowired
    private TickerRepository tickerRepository;

    @Override
    public void save(final TicksDTO ticksDTO) {
        tickerRepository.save(Mapper.getTickEntityFromTicksDTO(ticksDTO));
    }

    @Override
    public StatisticsDTO getStatisticsByInstrumentId(final String instrumentIdentifier){
        return Mapper.getStatisticsDTO(tickerRepository.getStatisticsByInstrumentId(instrumentIdentifier));
    }

    @Override
    public StatisticsDTO getStatistics(){
        return Mapper.getStatisticsDTO(tickerRepository.getStatistics());
    }

    @Override
    public void resetAllTicks(){
        tickerRepository.resetAllTicks();
    }
}
