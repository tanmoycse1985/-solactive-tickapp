package com.solactive.solactivetickapp.repository;

import com.solactive.solactivetickapp.model.StatisticsEntity;
import com.solactive.solactivetickapp.model.TickEntity;


public interface TickerRepository {

    void save(final TickEntity tickEntity);
    StatisticsEntity getStatisticsByInstrumentId(final String instrumentIdentifier);
    StatisticsEntity getStatistics();
    void resetAllTicks();
}
