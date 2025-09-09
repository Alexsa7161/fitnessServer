package com.example.fitnessserver;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserConstructorAndGettersSetters() {
        // Создаём объект
        User user = new User("user_123");

        // Проверяем геттер
        assertEquals("user_123", user.getUserId());

        // Проверяем сеттер
        user.setUserId("user_456");
        assertEquals("user_456", user.getUserId());
    }
}
