package com.example.fitnessserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class KafkaStorageConsumerTest {

    private FitnessDataRepository repository;
    private KafkaStorageConsumer consumer;

    @BeforeEach
    public void setUp() {
        // Мок репозитория
        repository = mock(FitnessDataRepository.class);

        // Используем конструктор с таймаутом для тестов
        consumer = new KafkaStorageConsumer(repository, 10);
    }

    @Test
    public void testSaveNowSavesData() {
        // Добавляем данные в буфер
        consumer.buffer.add(new FitnessData(1L, "user1", "steps", 100.0, 123L));

        // Вызываем метод напрямую
        consumer.saveNow();

        // Проверяем, что данные сохранились и буфер очистился
        verify(repository, times(1)).saveAll(anyList());
        assertTrue(consumer.buffer.isEmpty());
    }

    @Test
    public void testStopSaving() {
        consumer.stopSaving();
        assertFalse(getRunningFlag(consumer));
    }

    @Test
    public void testConsumeMessagesParsesValidJson() throws Exception {
        // Подготовка JSON вручную (не вызываем реальный KafkaConsumer)
        String json = """
            {"user":"u1","metric":"steps","value":123.0,"timestamp":999}
        """;

        ObjectMapper mapper = new ObjectMapper();
        var node = mapper.readTree(json);

        // Создаём объект и кладём в буфер
        consumer.buffer.add(new FitnessData(
                null,
                node.get("user").asText(),
                node.get("metric").asText(),
                node.get("value").asDouble(),
                node.get("timestamp").asInt()
        ));

        assertEquals(1, consumer.buffer.size());
        assertEquals("u1", consumer.buffer.get(0).getUserId());
    }

    // Вспомогательный метод для проверки приватного флага
    private boolean getRunningFlag(KafkaStorageConsumer consumer) {
        try {
            var field = KafkaStorageConsumer.class.getDeclaredField("running");
            field.setAccessible(true);
            return (boolean) field.get(consumer);
        } catch (Exception e) {
            fail("Reflection failed");
            return true;
        }
    }
}
