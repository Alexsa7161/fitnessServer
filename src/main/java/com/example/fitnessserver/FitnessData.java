package com.example.fitnessserver;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "fitness_data")
public class FitnessData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    private String metric;

    private Double value;

    @Column(name = "timestamp")
    private long timestamp;

    // No-args constructor
    public FitnessData() {
    }

    // All-args constructor
    public FitnessData(Long id, String userId, String metric, Double value, long timestamp) {
        this.id = id;
        this.userId = userId;
        this.metric = metric;
        this.value = value;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getMetric() { return metric; }
    public void setMetric(String metric) { this.metric = metric; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FitnessData)) return false;
        FitnessData that = (FitnessData) o;
        return timestamp == that.timestamp &&
                Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(metric, that.metric) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, metric, value, timestamp);
    }

    // toString
    @Override
    public String toString() {
        return "FitnessData{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", metric='" + metric + '\'' +
                ", value=" + value +
                ", timestamp=" + timestamp +
                '}';
    }
}
