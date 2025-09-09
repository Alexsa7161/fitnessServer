package com.example.fitnessserver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testConstructorAndGetter() {
        User user = new User("u1");
        assertEquals("u1", user.getUserId());
    }

    @Test
    void testSetter() {
        User user = new User("u2");
        user.setUserId("newId");
        assertEquals("newId", user.getUserId());
    }

    @Test
    void testNullUserId() {
        User user = new User(null);
        assertNull(user.getUserId());
        user.setUserId("id123");
        assertEquals("id123", user.getUserId());
    }
}
