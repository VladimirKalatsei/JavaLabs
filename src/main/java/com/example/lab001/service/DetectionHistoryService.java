package com.example.lab001.service;

import com.example.lab001.cache.DetectionHistoryCache;
import com.example.lab001.model.DetectionHistory;
import com.example.lab001.repository.DetectionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetectionHistoryService {
    private final DetectionHistoryRepository detectionHistoryRepository;
    private final DetectionHistoryCache detectionHistoryCache;
    private final UserService userService;

    public List<DetectionHistory> findAll() {
        List<DetectionHistory> cachedHistories = detectionHistoryCache.getAllHistories();
        if (cachedHistories != null) {
            return cachedHistories;
        }
        List<DetectionHistory> histories = detectionHistoryRepository.findAll();
        detectionHistoryCache.putAllHistories(histories);
        return histories;
    }

    public DetectionHistory findById(Long id) {
        DetectionHistory cachedHistory = detectionHistoryCache.getById(id);
        if (cachedHistory != null) {
            return cachedHistory;
        }
        DetectionHistory history = detectionHistoryRepository.findById(id).orElse(null);
        if (history != null) {
            detectionHistoryCache.putById(id, history);
        }
        return history;
    }

    public List<DetectionHistory> findByUserId(Long userId) {
        List<DetectionHistory> cachedHistories = detectionHistoryCache.getByUserId(userId);
        if (cachedHistories != null) {
            return cachedHistories;
        }
        List<DetectionHistory> histories = detectionHistoryRepository.findByUserId(userId);
        detectionHistoryCache.putByUserId(userId, histories);
        return histories;
    }

    public List<DetectionHistory> findByEmail(String email) {
        List<DetectionHistory> cachedHistories = detectionHistoryCache.getByEmail(email);
        if (cachedHistories != null) {
            return cachedHistories;
        }
        List<DetectionHistory> histories = detectionHistoryRepository.findByEmail(email);
        detectionHistoryCache.putByEmail(email, histories);
        return histories;
    }

    public List<DetectionHistory> findByUsername(String username) {
        List<DetectionHistory> cachedHistories = detectionHistoryCache.getByUsername(username);
        if (cachedHistories != null) {
            return cachedHistories;
        }
        List<DetectionHistory> histories = detectionHistoryRepository.findByUsername(username);
        detectionHistoryCache.putByUsername(username, histories);
        return histories;
    }

    @Transactional
    public DetectionHistory create(DetectionHistory detectionHistory) {
        DetectionHistory createdHistory = detectionHistoryRepository.save(detectionHistory);
        detectionHistoryCache.clear();
        return createdHistory;
    }

    @Transactional
    public DetectionHistory update(Long id, DetectionHistory detectionHistory) {
        detectionHistory.setId(id);
        DetectionHistory updatedHistory = detectionHistoryRepository.save(detectionHistory);
        detectionHistoryCache.clear();
        return updatedHistory;
    }

    @Transactional
    public void delete(Long id) {
        DetectionHistory history = findById(id);
        if (history != null) {
            detectionHistoryRepository.deleteById(id);
            detectionHistoryCache.clear();
        }
    }
}