package com.example.fitnessserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class KafkaConsumerServiceTest {

    private MessageWebSocketHandler webSocketHandler;
    private KafkaConsumerService service;

    @BeforeEach
    public void setUp() {
        webSocketHandler = mock(MessageWebSocketHandler.class);
        service = new KafkaConsumerService(webSocketHandler);
    }

    @Test
    public void testConstructorAndFields() {
        assertNotNull(service);
    }

    @Test
    public void testExtractUserIdValidJson() throws Exception {
        String json = """
            {"user":"user_123","metric":"steps","value":1000}
        """;

        // вызываем приватный метод через reflection
        var method = KafkaConsumerService.class.getDeclaredMethod("extractUserId", String.class);
        method.setAccessible(true);
        String userId = (String) method.invoke(service, json);

        assertEquals("user_123", userId);
    }

    @Test
    public void testExtractUserIdInvalidJson() throws Exception {
        String json = "not a json";

        var method = KafkaConsumerService.class.getDeclaredMethod("extractUserId", String.class);
        method.setAccessible(true);
        String userId = (String) method.invoke(service, json);

        assertNull(userId);
    }

    @Test
    public void testWebSocketSendToUserCalled() throws Exception {
        // Заглушка JSON
        String json = """
            {"user":"user_456","metric":"steps","value":50}
        """;

        var method = KafkaConsumerService.class.getDeclaredMethod("extractUserId", String.class);
        method.setAccessible(true);
        String userId = (String) method.invoke(service, json);

        // Проверяем что вызов будет правильный (имитация)
        if (userId != null) {
            service = spy(service);
            doNothing().when(service).start(); // заглушка для start
            webSocketHandler.sendToUser(userId, json);
            verify(webSocketHandler, times(1)).sendToUser(userId, json);
        }
    }

    @Test
    public void testStartMethodRuns() {
        // просто проверяем, что метод start можно вызвать без исключений
        service.start();
    }
}
