package com.example.fitnessserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FitnessDataServiceTest {

    private FitnessDataRepository repository;
    private FitnessDataService service;

    @BeforeEach
    public void setUp() {
        repository = mock(FitnessDataRepository.class);
        service = new FitnessDataService(repository);
    }

    @Test
    public void testFindAllByUserId() {
        FitnessData d1 = new FitnessData(1L, "user_1", "steps", 1000.0, 111L);
        FitnessData d2 = new FitnessData(2L, "user_1", "calories", 200.0, 222L);

        when(repository.findByUserId("user_1")).thenReturn(Arrays.asList(d1, d2));

        List<FitnessData> result = service.findAllByUserId("user_1");

        assertEquals(2, result.size());
        assertEquals("steps", result.get(0).getMetric());
        verify(repository, times(1)).findByUserId("user_1");
    }

    @Test
    public void testSave() {
        FitnessData d = new FitnessData(null, "user_1", "steps", 1500.0, 333L);
        FitnessData saved = new FitnessData(1L, "user_1", "steps", 1500.0, 333L);

        when(repository.save(d)).thenReturn(saved);

        FitnessData result = service.save(d);

        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(d);
    }

    @Test
    public void testUpdateSuccess() {
        FitnessData existing = new FitnessData(1L, "user_1", "steps", 1000.0, 111L);
        FitnessData newData = new FitnessData(null, "user_2", "calories", 200.0, 222L);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(FitnessData.class))).thenAnswer(invocation -> invocation.getArgument(0));

        FitnessData result = service.update(1L, newData);

        assertEquals("user_2", result.getUserId());
        assertEquals("calories", result.getMetric());
        assertEquals(200.0, result.getValue());
        assertEquals(222L, result.getTimestamp());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(existing);
    }

    @Test
    public void testUpdateNotFound() {
        FitnessData newData = new FitnessData(null, "user_x", "sleep", 8.0, 999L);

        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.update(99L, newData));

        assertEquals("Data not found", ex.getMessage());
        verify(repository, times(1)).findById(99L);
        verify(repository, never()).save(any());
    }

    @Test
    public void testDelete() {
        service.delete(5L);

        verify(repository, times(1)).deleteById(5L);
    }
}
