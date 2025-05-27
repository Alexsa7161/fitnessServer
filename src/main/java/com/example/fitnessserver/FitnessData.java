package com.example.fitnessserver;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fitness_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
