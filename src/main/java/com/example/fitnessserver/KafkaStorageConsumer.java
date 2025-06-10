//package com.example.fitnessserver;
//
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
//import java.util.concurrent.*;
//
//@Service
//public class KafkaStorageConsumer {
//
//    private final FitnessDataRepository repository;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final List<String> topics = List.of(
//            "heart-rate-topic",
//            "location-topic",
//            "steps-topic",
//            "battery-topic"
//    );
//
//    private final List<FitnessData> buffer = new CopyOnWriteArrayList<>();
//
//    public KafkaStorageConsumer(FitnessDataRepository repository) {
//        this.repository = repository;
//    }
//
//    @PostConstruct
//    public void start() {
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        executor.submit(this::consumeMessages);
//        executor.submit(this::savePeriodically);
//    }
//
//    private void consumeMessages() {
//        Properties props = new Properties();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "fitness-storage-group");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
//
//        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
//            consumer.subscribe(topics);
//
//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
//                for (ConsumerRecord<String, String> record : records) {
//                    try {
//                        JsonNode json = objectMapper.readTree(record.value());
//                        FitnessData data = new FitnessData(
//                                null,
//                                json.get("user").asText(),
//                                json.get("metric").asText(),
//                                json.get("value").asDouble(),
//                                json.get("timestamp").asInt()
//                        );
//                        buffer.add(data);
//                    } catch (Exception e) {
//                    }
//                }
//            }
//        }
//    }
//
//    private void savePeriodically() {
//        while (true) {
//            try {
//                Thread.sleep(60_00);
//                if (!buffer.isEmpty()) {
//                    List<FitnessData> toSave = new ArrayList<>(buffer);
//                    buffer.clear();
//                    repository.saveAll(toSave);
//                }
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                break;
//            }
//        }
//    }
//}
