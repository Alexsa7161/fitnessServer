package com.example.fitnessserver;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(HistoryController.class)
public class HistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FitnessDataRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetHistory() throws Exception {
        FitnessData data1 = new FitnessData();
        data1.setId(1L);
        data1.setUserId("user1");
        data1.setMetric("heart_rate");
        data1.setValue(85.0);
        data1.setTimestamp(1000L);

        FitnessData data2 = new FitnessData();
        data2.setId(2L);
        data2.setUserId("user1");
        data2.setMetric("steps");
        data2.setValue(1200.0);
        data2.setTimestamp(1500L);

        List<FitnessData> mockData = Arrays.asList(data1, data2);

        Mockito.when(repository.findByUserIdAndTimestampBetween(
                        anyString(), anyLong(), anyLong()))
                .thenReturn(mockData);

        mockMvc.perform(get("/api/history")
                        .param("userId", "user1")
                        .param("startTimestamp", "500")
                        .param("endTimestamp", "2000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].userId").value("user1"))
                .andExpect(jsonPath("$[1].metric").value("steps"));
    }
}
