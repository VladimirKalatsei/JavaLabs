package com.example.lab001.cache;

import com.example.lab001.model.DetectionHistory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DetectionHistoryCache {
    private final Map<Long, DetectionHistory> historyByIdCache = new HashMap<>();
    private final Map<String, List<DetectionHistory>> historiesByUsernameCache = new HashMap<>();
    private final Map<String, List<DetectionHistory>> historiesByEmailCache = new HashMap<>();
    private final Map<Long, List<DetectionHistory>> historiesByUserIdCache = new HashMap<>();
    private List<DetectionHistory> allHistoriesCache;

    public DetectionHistory getById(Long id) {
        return historyByIdCache.get(id);
    }

    public List<DetectionHistory> getByUsername(String username) {
        return historiesByUsernameCache.get(username);
    }

    public List<DetectionHistory> getByEmail(String email) {
        return historiesByEmailCache.get(email);
    }

    public List<DetectionHistory> getByUserId(Long userId) {
        return historiesByUserIdCache.get(userId);
    }

    public List<DetectionHistory> getAllHistories() {
        return allHistoriesCache;
    }

    public void putById(Long id, DetectionHistory history) {
        historyByIdCache.put(id, history);
    }

    public void putByUsername(String username, List<DetectionHistory> histories) {
        historiesByUsernameCache.put(username, histories);
    }

    public void putByEmail(String email, List<DetectionHistory> histories) {
        historiesByEmailCache.put(email, histories);
    }

    public void putByUserId(Long userId, List<DetectionHistory> histories) {
        historiesByUserIdCache.put(userId, histories);
    }

    public void putAllHistories(List<DetectionHistory> histories) {
        allHistoriesCache = histories;
    }

    public void clear() {
        historyByIdCache.clear();
        historiesByUsernameCache.clear();
        historiesByEmailCache.clear();
        historiesByUserIdCache.clear();
        allHistoriesCache = null;
    }
}