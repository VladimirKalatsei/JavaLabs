package com.example.lab001.controller;

import com.example.lab001.dto.DetectionHistoryDTO;
import com.example.lab001.model.DetectionHistory;
import com.example.lab001.service.DetectionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/detection-history")
@RequiredArgsConstructor
public class DetectionHistoryController {
    private final DetectionHistoryService detectionHistoryService;

    @PostMapping
    public ResponseEntity<DetectionHistory> createDetectionHistory(@RequestBody DetectionHistory detectionHistory) {
        DetectionHistory createdHistory = detectionHistoryService.create(detectionHistory);
        return ResponseEntity.status(201).body(createdHistory);
    }

    @GetMapping
    public List<DetectionHistoryDTO> getAll() {
        List<DetectionHistory> histories = detectionHistoryService.findAll();
        return histories.stream()
                .map(DetectionHistoryDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetectionHistoryDTO> getById(@PathVariable Long id) {
        DetectionHistory history = detectionHistoryService.findById(id);
        if (history != null) {
            return ResponseEntity.ok(new DetectionHistoryDTO(history));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public List<DetectionHistoryDTO> getByUserId(@PathVariable Long userId) {
        // Получаем записи по userId
        List<DetectionHistory> histories = detectionHistoryService.findByUserId(userId);
        return histories.stream()
                .map(DetectionHistoryDTO::new)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        detectionHistoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/histories")
    public List<DetectionHistoryDTO> getHistoriesByUsername(@RequestParam String username) {
        List<DetectionHistory> histories = detectionHistoryService.findByUsername(username);
        return histories.stream()
                .map(DetectionHistoryDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/email")
    public List<DetectionHistoryDTO> getHistoriesByEmail(@RequestParam String email) {
        List<DetectionHistory> histories = detectionHistoryService.findByEmail(email);
        return histories.stream()
                .map(DetectionHistoryDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetectionHistory> updateDetectionHistory(@PathVariable Long id, @RequestBody DetectionHistory detectionHistory) {
        DetectionHistory updatedHistory = detectionHistoryService.update(id, detectionHistory);
        return ResponseEntity.ok(updatedHistory);
    }

}