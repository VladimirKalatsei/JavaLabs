package com.example.lab001.service;

import com.example.lab001.model.DetectionHistory;
import com.example.lab001.model.User;
import com.example.lab001.repository.DetectionHistoryRepository;
import com.example.lab001.cache.CommonCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetectionHistoryService {
    private final DetectionHistoryRepository detectionHistoryRepository;
    private final CommonCache commonCache;
    private final UserService userService;
    private final RequestCounter requestCounter;

    public List<DetectionHistory> findAll()
    {
        requestCounter.increment();
        List<DetectionHistory> cachedHistories = commonCache.getAllDetectionHistories();
        if (cachedHistories != null) {
            return cachedHistories;
        }
        List<DetectionHistory> histories = detectionHistoryRepository.findAll();
        commonCache.putAllDetectionHistories(histories);
        return histories;
    }

    public DetectionHistory findById(Long id)
    {
        requestCounter.increment();
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

    public List<DetectionHistory> findByUserId(Long userId)
    {
        requestCounter.increment();
        List<DetectionHistory> cachedHistories = commonCache.getDetectionHistoriesByUserId(userId);
        if (cachedHistories != null) {
            return cachedHistories;
        }
        List<DetectionHistory> histories = detectionHistoryRepository.findByUserId(userId);
        commonCache.putDetectionHistoriesByUserId(userId, histories);
        return histories;
    }

    public List<DetectionHistory> findByEmail(String email)
    {
        requestCounter.increment();
        List<DetectionHistory> cachedHistories = commonCache.getDetectionHistoriesByEmail(email);
        if (cachedHistories != null) {
            return cachedHistories;
        }
        List<DetectionHistory> histories = detectionHistoryRepository.findByEmail(email);
        commonCache.putDetectionHistoriesByEmail(email, histories);
        return histories;
    }

    public List<DetectionHistory> findByUsername(String username)
    {
        requestCounter.increment();
        List<DetectionHistory> cachedHistories = commonCache.getDetectionHistoriesByUsername(username);
        if (cachedHistories != null) {
            return cachedHistories;
        }
        List<DetectionHistory> histories = detectionHistoryRepository.findByUsername(username);
        commonCache.putDetectionHistoriesByUsername(username, histories);
        return histories;
    }

    @Transactional
    public DetectionHistory create(DetectionHistory detectionHistory)
    {
        requestCounter.increment();
        DetectionHistory createdHistory = detectionHistoryRepository.save(detectionHistory);
        commonCache.clearDetectionHistoryCache();
        return createdHistory;
    }

    @Transactional
    public List<DetectionHistory> createHistoriesBulk(List<DetectionHistory> histories)
    {
        requestCounter.increment();
        Set<Long> userIds = histories.stream()
                .map(history -> history.getUser().getId())
                .collect(Collectors.toSet());

        List<User> existingUsers = userService.findAllById(userIds);
        Set<Long> existingUserIds = existingUsers.stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        List<DetectionHistory> validHistories = histories.stream()
                .filter(history -> existingUserIds.contains(history.getUser().getId()))
                .peek(history -> {
                    User user = existingUsers.stream()
                            .filter(u -> u.getId().equals(history.getUser().getId()))
                            .findFirst()
                            .orElse(null);
                    history.setUser(user);
                })
                .collect(Collectors.toList());

        List<DetectionHistory> savedHistories = detectionHistoryRepository.saveAll(validHistories);
        commonCache.clearDetectionHistoryCache();
        return savedHistories;
    }

    @Transactional
    public DetectionHistory update(Long id, DetectionHistory detectionHistory)
    {
        requestCounter.increment();
        detectionHistory.setId(id);
        DetectionHistory updatedHistory = detectionHistoryRepository.save(detectionHistory);
        commonCache.clearDetectionHistoryCache();
        return updatedHistory;
    }

    @Transactional
    public void delete(Long id)
    {
        requestCounter.increment();
        detectionHistoryRepository.deleteById(id);
        commonCache.clearDetectionHistoryCache();
    }
}