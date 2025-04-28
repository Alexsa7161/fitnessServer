package com.example.fitnessserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/data")
public class FitnessDataController {

    private final FitnessDataRepository repository;

    public FitnessDataController(FitnessDataRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Void> addRecord(@RequestBody FitnessData data) {
        repository.save(data);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateRecord(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return repository.findById(id)
                .map(existing -> {
                    if (updates.containsKey("value")) {
                        existing.setValue(Double.parseDouble(updates.get("value").toString()));
                    }
                    repository.save(existing);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // ← обязательно .build()
    }
}
