//package com.example.fitnessserver;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.annotation.PostConstruct;
//import org.apache.kafka.clients.consumer.*;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.util.*;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@Service
//public class KafkaConsumerService {
//
//    private final MessageWebSocketHandler webSocketHandler;
//
//    public KafkaConsumerService(MessageWebSocketHandler webSocketHandler) {
//        this.webSocketHandler = webSocketHandler;
//    }
//
//    private final List<String> topics = List.of(
//            "heart-rate-topic",
//            "location-topic",
//            "steps-topic",
//            "battery-topic"
//    );
//
//    @PostConstruct
//    public void start() {
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.submit(this::consumeMessages);
//    }
//
//    private void consumeMessages() {
//        Properties props = new Properties();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "fitness-group");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
//
//        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
//            consumer.subscribe(topics);
//            System.out.println("Kafka consumer запущен");
//
//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
//                for (ConsumerRecord<String, String> record : records) {
//                    String json = record.value();
//                    System.out.println("Получено из Kafka: " + json);
//                    String userId = extractUserId(json);
//                    if (userId != null) {
//                        webSocketHandler.sendToUser(userId, json);
//                    }
//                }
//            }
//        }
//    }
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    private String extractUserId(String json) {
//        try {
//            JsonNode rootNode = objectMapper.readTree(json);
//            return rootNode.get("user").asText();
//        } catch (Exception e) {
//            return null;
//        }
//    }
//}
