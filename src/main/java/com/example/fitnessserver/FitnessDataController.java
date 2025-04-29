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
    public ResponseEntity<FitnessData> addRecord(@RequestBody FitnessData data) {
        FitnessData saved = repository.save(data);
        return ResponseEntity.ok(saved);
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
    public @ResponseBody ResponseEntity<FitnessData> updateRecord(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return repository.findById(id)
                .map(existing -> {
                    if (updates.containsKey("value")) {
                        existing.setValue(Double.parseDouble(updates.get("value").toString()));
                    }
                    FitnessData updated = repository.save(existing);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Добавим метод для получения данных по ID
    @GetMapping("/{id}")
    public ResponseEntity<FitnessData> getRecord(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
