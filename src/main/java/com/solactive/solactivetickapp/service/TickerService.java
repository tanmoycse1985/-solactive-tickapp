package com.solactive.solactivetickapp.service;

import com.solactive.solactivetickapp.dto.StatisticsDTO;
import com.solactive.solactivetickapp.dto.TicksDTO;

public interface TickerService {

    void save(final TicksDTO ticksDTO);
    StatisticsDTO getStatisticsByInstrumentId(final String instrumentIdentifier);
    StatisticsDTO getStatistics();
    void resetAllTicks();

}
