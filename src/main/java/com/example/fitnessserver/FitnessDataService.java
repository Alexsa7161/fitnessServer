package com.example.fitnessserver;


import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FitnessDataService {
    private final FitnessDataRepository repository;

    public FitnessDataService(FitnessDataRepository repository) {
        this.repository = repository;
    }

    public List<FitnessData> findAllByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public FitnessData save(FitnessData data) {
        return repository.save(data);
    }

    public FitnessData update(Long id, FitnessData newData) {
        FitnessData existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found"));
        existing.setUserId(newData.getUserId());
        existing.setMetric(newData.getMetric());
        existing.setValue(newData.getValue());
        existing.setTimestamp(newData.getTimestamp());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
