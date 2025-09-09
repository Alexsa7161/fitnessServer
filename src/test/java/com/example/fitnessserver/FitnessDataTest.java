package com.example.fitnessserver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FitnessDataTest {

    @Test
    public void testAllArgsConstructorAndGetters() {
        FitnessData data = new FitnessData(1L, "user_123", "steps", 1000.0, 123456789L);

        assertEquals(1L, data.getId());
        assertEquals("user_123", data.getUserId());
        assertEquals("steps", data.getMetric());
        assertEquals(1000.0, data.getValue());
        assertEquals(123456789L, data.getTimestamp());
    }

    @Test
    public void testNoArgsConstructorAndSetters() {
        FitnessData data = new FitnessData();
        data.setId(2L);
        data.setUserId("user_456");
        data.setMetric("calories");
        data.setValue(250.5);
        data.setTimestamp(987654321L);

        assertEquals(2L, data.getId());
        assertEquals("user_456", data.getUserId());
        assertEquals("calories", data.getMetric());
        assertEquals(250.5, data.getValue());
        assertEquals(987654321L, data.getTimestamp());
    }

    @Test
    public void testEqualsAndHashCode() {
        FitnessData d1 = new FitnessData(1L, "user_1", "steps", 1000.0, 111L);
        FitnessData d2 = new FitnessData(1L, "user_1", "steps", 1000.0, 111L);
        FitnessData d3 = new FitnessData(2L, "user_2", "calories", 200.0, 222L);

        // Проверяем equals и hashCode
        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());

        assertNotEquals(d1, d3);
        assertNotEquals(d1.hashCode(), d3.hashCode());

        // Проверка reflexivity и null
        assertEquals(d1, d1);
        assertNotEquals(d1, null);
        assertNotEquals(d1, "some string");
    }

    @Test
    public void testToStringContainsAllFields() {
        FitnessData data = new FitnessData(1L, "user_123", "steps", 1000.0, 123456789L);
        String str = data.toString();

        assertTrue(str.contains("id=1"));
        assertTrue(str.contains("userId='user_123'"));
        assertTrue(str.contains("metric='steps'"));
        assertTrue(str.contains("value=1000.0"));
        assertTrue(str.contains("timestamp=123456789"));
    }
}
