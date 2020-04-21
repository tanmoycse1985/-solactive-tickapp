package com.solactive.solactivetickapp.mapper;

import com.solactive.solactivetickapp.dto.StatisticsDTO;
import com.solactive.solactivetickapp.dto.TicksDTO;
import com.solactive.solactivetickapp.model.StatisticsEntity;
import com.solactive.solactivetickapp.model.TickEntity;
import com.solactive.solactivetickapp.vo.StatisticsVO;
import com.solactive.solactivetickapp.vo.TicksVO;

public class Mapper {

    public static TickEntity getTickEntityFromTicksDTO(TicksDTO ticksDTO){
        TickEntity tickEntity = new TickEntity();
        tickEntity.setInstrument(ticksDTO.getInstrument());
        tickEntity.setPrice(ticksDTO.getPrice());
        tickEntity.setTimestamp(ticksDTO.getTimestamp());
        return tickEntity;
    }

    public static StatisticsDTO getStatisticsDTO(StatisticsEntity statisticsEntity){
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setAverage(statisticsEntity.getAverage());
        statisticsDTO.setCount(statisticsEntity.getCount());
        statisticsDTO.setMax(statisticsEntity.getMax());
        statisticsDTO.setMin(statisticsEntity.getMin());
        return statisticsDTO;
    }

    public static TicksDTO getTickDTOFromTicksVO(TicksVO ticksVO){
        TicksDTO ticksDTO = new TicksDTO();
        ticksDTO.setInstrument(ticksVO.getInstrument());
        ticksDTO.setPrice(ticksVO.getPrice());
        ticksDTO.setTimestamp(ticksVO.getTimestamp());
        return ticksDTO;
    }

    public static StatisticsVO getStatisticsVO(StatisticsDTO statisticsDTO){
        StatisticsVO statisticsVO = new StatisticsVO();
        statisticsVO.setAverage(statisticsDTO.getAverage());
        statisticsVO.setCount(statisticsDTO.getCount());
        statisticsVO.setMax(statisticsDTO.getMax());
        statisticsVO.setMin(statisticsDTO.getMin());
        return statisticsVO;
    }

}
