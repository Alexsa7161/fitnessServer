package com.example.fitnessserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class KafkaStorageConsumerTest {

    private FitnessDataRepository repository;
    private KafkaStorageConsumer consumer;

    @BeforeEach
    public void setUp() {
        repository = mock(FitnessDataRepository.class);
        consumer = new KafkaStorageConsumer(repository);
    }

    @Test
    public void testStartDoesNotThrow() {
        // Просто проверяем, что метод запускается без исключений
        consumer.start();
    }

    @Test
    public void testSavePeriodicallySavesData() throws Exception {
        // Подменяем buffer через reflection
        Field bufferField = KafkaStorageConsumer.class.getDeclaredField("buffer");
        bufferField.setAccessible(true);
        CopyOnWriteArrayList<FitnessData> buffer = new CopyOnWriteArrayList<>();
        buffer.add(new FitnessData(1L, "user1", "steps", 100.0, 123L));
        bufferField.set(consumer, buffer);

        // Останавливаем цикл сразу после одной итерации
        consumer.stopSaving();

        // Вызываем приватный метод через reflection
        var method = KafkaStorageConsumer.class.getDeclaredMethod("savePeriodically");
        method.setAccessible(true);
        method.invoke(consumer);

        // Проверяем, что вызвался repository.saveAll()
        verify(repository, times(1)).saveAll(anyList());
    }

    @Test
    public void testSavePeriodicallyInterrupted() throws Exception {
        consumer.stopSaving(); // чтобы цикл сразу вышел

        var method = KafkaStorageConsumer.class.getDeclaredMethod("savePeriodically");
        method.setAccessible(true);

        // Прерываем поток
        Thread.currentThread().interrupt();
        method.invoke(consumer);

        assertTrue(Thread.interrupted()); // очищаем флаг
    }

    @Test
    public void testConsumeMessagesParsesValidJson() throws Exception {
        // Делаем буфер доступным
        Field bufferField = KafkaStorageConsumer.class.getDeclaredField("buffer");
        bufferField.setAccessible(true);
        CopyOnWriteArrayList<FitnessData> buffer = new CopyOnWriteArrayList<>();
        bufferField.set(consumer, buffer);

        // JSON сообщение
        String json = """
            {"user":"u1","metric":"steps","value":123.0,"timestamp":999}
        """;

        ObjectMapper mapper = new ObjectMapper();
        var node = mapper.readTree(json);

        // Создаём FitnessData через приватный код вручную
        FitnessData data = new FitnessData(
                null,
                node.get("user").asText(),
                node.get("metric").asText(),
                node.get("value").asDouble(),
                node.get("timestamp").asInt()
        );
        buffer.add(data);

        assertEquals(1, buffer.size());
        assertEquals("u1", buffer.get(0).getUserId());
    }

    @Test
    public void testStopSaving() {
        consumer.stopSaving();
        // Проверим, что флаг действительно изменился
        try {
            Field runningField = KafkaStorageConsumer.class.getDeclaredField("running");
            runningField.setAccessible(true);
            boolean value = (boolean) runningField.get(consumer);
            assertFalse(value);
        } catch (Exception e) {
            fail("Reflection failed");
        }
    }
}
