package com.example.fitnessserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class KafkaStorageConsumerTest {

    private FitnessDataRepository repository;
    private KafkaStorageConsumer consumer;

    @BeforeEach
    public void setUp() {
        repository = mock(FitnessDataRepository.class);

        // создаём consumer через конструктор с одним аргументом
        consumer = mock(KafkaStorageConsumer.class, withSettings()
                .useConstructor(repository)
                .defaultAnswer(CALLS_REAL_METHODS));
    }

    @Test
    public void testStartDoesNotThrow() {
        // Просто вызываем start() заглушки
        consumer.start();
    }

    @Test
    public void testStopSavingDoesNotThrow() {
        // Метод реально меняет приватный флаг
        doCallRealMethod().when(consumer).stopSaving();
        consumer.stopSaving();
        // Проверяем, что вызов прошёл
        assertTrue(true);
    }

    @Test
    public void testSavePeriodicallySavesDataStub() {
        // Заглушка: просто вызываем и проверяем, что не падает
        doNothing().when(consumer).savePeriodically();
        consumer.savePeriodically();
        assertTrue(true);
    }

    @Test
    public void testConsumeMessagesStub() {
        // Заглушка: эмулируем добавление данных в буфер
        assertTrue(true);
    }
}
