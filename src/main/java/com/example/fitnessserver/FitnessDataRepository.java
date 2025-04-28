package com.example.fitnessserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FitnessDataRepository extends JpaRepository<FitnessData, Long> {
    @Query("SELECT f FROM FitnessData f WHERE f.userId = :userId AND f.timestamp BETWEEN :start AND :end")
    List<FitnessData> findByUserIdAndTimestampBetween(@Param("userId") String userId,
                                                      @Param("start") long start,
                                                      @Param("end") long end);
    List<FitnessData> findByUserId(String userId);
}