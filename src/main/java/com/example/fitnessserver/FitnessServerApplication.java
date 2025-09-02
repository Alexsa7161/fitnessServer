package com.example.fitnessserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class FitnessServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FitnessServerApplication.class, args);
    }
}
