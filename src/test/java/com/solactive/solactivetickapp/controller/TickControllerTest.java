package com.solactive.solactivetickapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solactive.solactivetickapp.service.TickerService;
import com.solactive.solactivetickapp.vo.StatisticsVO;
import com.solactive.solactivetickapp.vo.TicksVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = { "test" })
public class TickControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    TickerService tickerService;

    private int tickValidity = 60;
    private int ticksExpiry = -60;
    private Calendar calender;

    @BeforeEach
    public void setup() throws Exception {
        calender = Calendar.getInstance();
    }

    @AfterEach
    public void reset() throws Exception {
        tickerService.resetAllTicks();
    }

    @Test
    public void testSaveTickWithInvalidTimeStamp_return204() throws Exception {
        int randomNumber = ticksExpiry + (int)(Math.random()*60*(-1));
        calender.add(Calendar.SECOND, randomNumber);
        mockMvc.perform(post("/ticks").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(new TicksVO("Google", 153.25, calender.getTime().getTime()))))
                .andExpect(status().isNoContent()).andReturn();
    }

    @Test
    public void testSaveTickForInstrumentNullReturnsBadRequest() throws Exception {
        int randomNumber = ticksExpiry + (int)(Math.random()*60);
        calender.add(Calendar.SECOND, randomNumber);
        mockMvc.perform(post("/ticks").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(new TicksVO(null, 132.23, calender.getTime().getTime()))))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testStatisticsWithNoData() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andReturn();
        StatisticsVO statisticsVO = mapper.readValue(mvcResult.getResponse().getContentAsString(), StatisticsVO.class);
        Assertions.assertNull(statisticsVO.getAverage());
        Assertions.assertNull(statisticsVO.getCount());
        Assertions.assertNull(statisticsVO.getMax());
        Assertions.assertNull(statisticsVO.getMin());
    }

    @Test
    public void testStatisticsByInstrumentIdWithNoData() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/statistics/Google"))
                .andExpect(status().isOk())
                .andReturn();
        StatisticsVO statisticsVO = mapper.readValue(mvcResult.getResponse().getContentAsString(), StatisticsVO.class);
        Assertions.assertNull(statisticsVO.getAverage());
        Assertions.assertNull(statisticsVO.getCount());
        Assertions.assertNull(statisticsVO.getMax());
        Assertions.assertNull(statisticsVO.getMin());
    }

    @Test
    public void testCreationAndStatisticsForSingleEntry() throws Exception {
        int randomNumber = tickValidity + (int)(Math.random()*60*(-1));
        calender.add(Calendar.SECOND, randomNumber);
        mockMvc.perform(post("/ticks").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(new TicksVO("Google", 132.23, calender.getTime().getTime()))))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andReturn();
        StatisticsVO statisticsVO = mapper.readValue(mvcResult.getResponse().getContentAsString(), StatisticsVO.class);
        Assertions.assertEquals(132.23, statisticsVO.getAverage());
        Assertions.assertEquals(1, statisticsVO.getCount());
        Assertions.assertEquals(132.23, statisticsVO.getMax());
        Assertions.assertEquals(132.23, statisticsVO.getMin());

    }

    @Test
    public void testCreationAndStatisticsForMultipleEntries() throws Exception {
        int randomNumber = tickValidity + (int)(Math.random()*60*(-1));
        calender.add(Calendar.SECOND, randomNumber);
        mockMvc.perform(post("/ticks").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(new TicksVO("Google", 120.00, calender.getTime().getTime()))))
                .andExpect(status().isCreated())
                .andReturn();
        mockMvc.perform(post("/ticks").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(new TicksVO("Ibm", 140.00, calender.getTime().getTime()))))
                .andExpect(status().isCreated())
                .andReturn();
        mockMvc.perform(post("/ticks").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(new TicksVO("Sony", 160.00, calender.getTime().getTime()))))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andReturn();
        StatisticsVO statisticsVO = mapper.readValue(mvcResult.getResponse().getContentAsString(), StatisticsVO.class);
        Assertions.assertEquals(140.00, statisticsVO.getAverage());
        Assertions.assertEquals(3, statisticsVO.getCount());
        Assertions.assertEquals(160.00, statisticsVO.getMax());
        Assertions.assertEquals(120.00, statisticsVO.getMin());
    }

    @Test
    public void testCreateAndStatisticsForInstrumentForSingleEntry() throws Exception {
        int randomNumber = tickValidity + (int)(Math.random()*60*(-1));
        calender.add(Calendar.SECOND, randomNumber);
        mockMvc.perform(post("/ticks").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(new TicksVO("Google", 132.23, calender.getTime().getTime()))))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(get("/statistics/Google"))
                .andExpect(status().isOk())
                .andReturn();
        StatisticsVO statisticsVO = mapper.readValue(mvcResult.getResponse().getContentAsString(), StatisticsVO.class);
        Assertions.assertEquals(132.23, statisticsVO.getAverage());
        Assertions.assertEquals(1, statisticsVO.getCount());
        Assertions.assertEquals(132.23, statisticsVO.getMax());
        Assertions.assertEquals(132.23, statisticsVO.getMin());

    }

}
