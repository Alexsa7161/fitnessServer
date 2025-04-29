package com.example.fitnessserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.*;

import java.io.IOException;

import static org.mockito.Mockito.*;

class MessageWebSocketHandlerTest {

    private MessageWebSocketHandler handler;
    private WebSocketSession mockSession;

    @BeforeEach
    void setUp() {
        handler = new MessageWebSocketHandler();
        mockSession = mock(WebSocketSession.class);

        when(mockSession.getId()).thenReturn("session123");
        when(mockSession.isOpen()).thenReturn(true);
    }

    @Test
    void testAfterConnectionEstablished() {
        handler.afterConnectionEstablished(mockSession);

    }

    @Test
    void testHandleTextMessage() throws Exception {
        TextMessage message = new TextMessage("user1");

        handler.handleTextMessage(mockSession, message);
        handler.sendToUser("user1", "test message");
        verify(mockSession, times(1)).sendMessage(new TextMessage("test message"));
    }

    @Test
    void testAfterConnectionClosed() throws IOException {
        TextMessage message = new TextMessage("user1");

        handler.handleTextMessage(mockSession, message);
        handler.afterConnectionClosed(mockSession, CloseStatus.NORMAL);
        handler.sendToUser("user1", "should not be delivered");
        verify(mockSession, never()).sendMessage(new TextMessage("should not be delivered"));
    }
}
