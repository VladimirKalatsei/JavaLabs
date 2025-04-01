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

    public List<DetectionHistory> findAll() {
        return detectionHistoryRepository.findAll();
    }

    public DetectionHistory findById(Long id) {
        return detectionHistoryRepository.findById(id).orElse(null);
    }

    public List<DetectionHistory> findByUserId(Long userId) {
        return detectionHistoryRepository.findByUserId(userId);
    }

    @Transactional
    public DetectionHistory create(DetectionHistory detectionHistory) {
        return detectionHistoryRepository.save(detectionHistory);
    }

    @Transactional
    public void delete(Long id) {
        DetectionHistory history = findById(id);
        if (history != null) {
            detectionHistoryRepository.deleteById(id);
            detectionHistoryCache.remove(history.getUser().getUsername()); // Очистка кэша
        }
    }
    public List<DetectionHistory> findByEmail(String email) {
        return detectionHistoryRepository.findByEmail(email);
    }

    public List<DetectionHistory> findByUsername(String username) {
        // Проверяем кэш
        List<DetectionHistory> cachedHistories = detectionHistoryCache.get(username);
        if (cachedHistories != null) {
            return cachedHistories;
        }

        // Если данных нет в кэше, загружаем из БД
        List<DetectionHistory> histories = detectionHistoryRepository.findByUsername(username);
        // Сохраняем в кэш
        detectionHistoryCache.put(username, histories);
        return histories;
    }

    @Transactional
    public DetectionHistory update(Long id, DetectionHistory detectionHistory) {
        detectionHistory.setId(id);
        DetectionHistory updatedHistory = detectionHistoryRepository.save(detectionHistory);
        detectionHistoryCache.remove(detectionHistory.getUser().getUsername()); // Очистка кэша
        return updatedHistory;
    }


}