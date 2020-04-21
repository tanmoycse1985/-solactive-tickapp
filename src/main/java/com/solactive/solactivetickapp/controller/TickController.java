package com.solactive.solactivetickapp.controller;

import com.solactive.solactivetickapp.mapper.Mapper;
import com.solactive.solactivetickapp.service.TickerService;
import com.solactive.solactivetickapp.utils.Utility;
import com.solactive.solactivetickapp.vo.StatisticsVO;
import com.solactive.solactivetickapp.vo.TicksVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/", produces = "application/json")
public class TickController {

    @Autowired
    private TickerService tickerService;

    private Logger log = LoggerFactory.getLogger(TickController.class);

    /**
     * Save the tick data
     * @param ticksVO
     * @return Returns 201/204 status depending on condition
     */
    @RequestMapping(value = "/ticks", method = RequestMethod.POST)
    public ResponseEntity<Void> ticks(@Valid @RequestBody final TicksVO ticksVO) {
        log.info("Entering ticks  method with : " + ticksVO);
        if (Utility.validateTimeStamp(ticksVO.getTimestamp())) {
            log.info("Time is greater than a minute: " + ticksVO);
            return ResponseEntity.status(204).build();
        }
        tickerService.save(Mapper.getTickDTOFromTicksVO(ticksVO));
        log.info("Ticks saved : " + ticksVO);
        return ResponseEntity.status(201).build();
    }

    /**
     * This method returns the overall statistics
     * @return ResponseEntity<StatisticsVO>
     */
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ResponseEntity<StatisticsVO> statistics() {
        log.info("Statistics called");
        return ResponseEntity.status(200).body(Mapper.getStatisticsVO(tickerService.getStatistics()));
    }

    /**
     * This method returns the statistics for a partcular instrument id
     * @param instrumentIdentifier
     * @return ResponseEntity<StatisticsVO>
     */
    @RequestMapping(value = "/statistics/{instrumentIdentifier}", method = RequestMethod.GET)
    public ResponseEntity<StatisticsVO> statisticsByInstrumentId(@PathVariable final String instrumentIdentifier) {
        log.info("statistics called for [" + instrumentIdentifier + "]");
        return ResponseEntity.status(200).body(Mapper.getStatisticsVO(tickerService.getStatisticsByInstrumentId(instrumentIdentifier)));
    }
}
