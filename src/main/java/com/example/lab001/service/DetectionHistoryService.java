package com.example.lab001.service;

import com.example.lab001.model.DetectionHistory;
import com.example.lab001.repository.DetectionHistoryRepository;
import com.example.lab001.cache.CommonCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetectionHistoryService {
    private final DetectionHistoryRepository detectionHistoryRepository;
    private final CommonCache commonCache;
    private final UserService userService;

    public List<DetectionHistory> findAll() {
        List<DetectionHistory> cachedHistories = commonCache.getAllDetectionHistories();
        if (cachedHistories != null) {
            return cachedHistories;
        }
        List<DetectionHistory> histories = detectionHistoryRepository.findAll();
        commonCache.putAllDetectionHistories(histories);
        return histories;
    }

    public DetectionHistory findById(Long id) {
        DetectionHistory cachedHistory = commonCache.getDetectionHistoryById(id);
        if (cachedHistory != null) {
            return cachedHistory;
        }
        DetectionHistory history = detectionHistoryRepository.findById(id).orElse(null);
        if (history != null) {
            commonCache.putDetectionHistory(history);
        }
        return history;
    }

    public List<DetectionHistory> findByUserId(Long userId) {
        List<DetectionHistory> cachedHistories = commonCache.getDetectionHistoriesByUserId(userId);
        if (cachedHistories != null) {
            return cachedHistories;
        }
        List<DetectionHistory> histories = detectionHistoryRepository.findByUserId(userId);
        commonCache.putDetectionHistoriesByUserId(userId, histories);
        return histories;
    }

    public List<DetectionHistory> findByEmail(String email) {
        List<DetectionHistory> cachedHistories = commonCache.getDetectionHistoriesByEmail(email);
        if (cachedHistories != null) {
            return cachedHistories;
        }
        List<DetectionHistory> histories = detectionHistoryRepository.findByEmail(email);
        commonCache.putDetectionHistoriesByEmail(email, histories);
        return histories;
    }

    public List<DetectionHistory> findByUsername(String username) {
        List<DetectionHistory> cachedHistories = commonCache.getDetectionHistoriesByUsername(username);
        if (cachedHistories != null) {
            return cachedHistories;
        }
        List<DetectionHistory> histories = detectionHistoryRepository.findByUsername(username);
        commonCache.putDetectionHistoriesByUsername(username, histories);
        return histories;
    }

    @Transactional
    public DetectionHistory create(DetectionHistory detectionHistory) {
        DetectionHistory createdHistory = detectionHistoryRepository.save(detectionHistory);
        commonCache.clearDetectionHistoryCache();
        return createdHistory;
    }

    @Transactional
    public DetectionHistory update(Long id, DetectionHistory detectionHistory) {
        detectionHistory.setId(id);
        DetectionHistory updatedHistory = detectionHistoryRepository.save(detectionHistory);
        commonCache.clearDetectionHistoryCache();
        return updatedHistory;
    }

    @Transactional
    public void delete(Long id) {
        detectionHistoryRepository.deleteById(id);
        commonCache.clearDetectionHistoryCache(); // Полная очистка кэша истории
    }
}