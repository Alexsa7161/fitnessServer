package com.example.fitnessserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FitnessServerApplicationTest {

    @Test
    public void testMainMethod() {
        // Просто проверяем, что метод main вызывается без ошибок
        FitnessServerApplication.main(new String[]{});
    }

    @Test
    public void testSpringApplicationRun() {
        SpringApplication app = new SpringApplication(FitnessServerApplication.class);
        assertNotNull(app);
    }
}
