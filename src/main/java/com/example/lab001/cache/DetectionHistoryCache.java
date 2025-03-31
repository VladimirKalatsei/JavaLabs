package com.example.lab001.cache;

import com.example.lab001.model.DetectionHistory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DetectionHistoryCache {
    private final Map<String, List<DetectionHistory>> cache = new HashMap<>();

    public List<DetectionHistory> get(String username) {
        return cache.get(username);
    }

    public void put(String username, List<DetectionHistory> histories) {
        cache.put(username, histories);
    }
}