package com.example.fitnessserver;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HistoryController {

    private final FitnessDataRepository repository;

    public HistoryController(FitnessDataRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/history")
    public List<FitnessData> getHistory(
            @RequestParam String userId,
            @RequestParam long startTimestamp,
            @RequestParam long endTimestamp) {

        return repository.findByUserIdAndTimestampBetween(userId, startTimestamp, endTimestamp);
    }
}
