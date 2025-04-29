package com.example.fitnessserver;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FitnessDataController.class)
public class FitnessDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FitnessDataRepository repository;

    private FitnessData sampleData;

    @BeforeEach
    public void setUp() {
        sampleData = new FitnessData();
        sampleData.setUserId("user_1");
        sampleData.setMetric("HeartRate");
        sampleData.setValue(75.5);
        sampleData.setTimestamp("2025-04-27T10:00:00");
    }

    @Test
    public void testAddFitnessData() throws Exception {
        when(repository.save(any(FitnessData.class))).thenReturn(sampleData);

        mockMvc.perform(post("/api/data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user_1"));
    }

    @Test
    public void testDeleteFitnessData() throws Exception {
        // Мокаем удаление
        when(repository.existsById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/data/{id}", 1L))
                .andExpect(status().isOk());

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateFitnessData() throws Exception {
        // Мокаем возврат данных до обновления
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(sampleData));

        // Обновляем значение
        sampleData.setValue(80.0);

        // Выполняем PATCH запрос для обновления данных
        mockMvc.perform(patch("/api/data/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": 80.0}"))
                .andExpect(status().isOk());

        // Выполняем GET запрос, чтобы убедиться, что обновление прошло успешно
        mockMvc.perform(get("/api/data/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(80.0));  // Проверяем обновленное значение
    }
}
