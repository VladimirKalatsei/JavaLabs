package com.example.lab001.cache;

import com.example.lab001.model.DetectionHistory;
import com.example.lab001.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommonCache {
    private final Map<String, Object> cache = new HashMap<>();


    public User getUserById(Long id) {
        return (User) cache.get("user_" + id);
    }

    public User getUserByEmail(String email) {
        return (User) cache.get("user_email_" + email);
    }

    public List<User> getAllUsers() {
        return (List<User>) cache.get("all_users");
    }

    public void putUser(User user) {
        cache.put("user_" + user.getId(), user);
        cache.put("user_email_" + user.getEmail(), user);
    }

    public void putAllUsers(List<User> users) {
        cache.put("all_users", users);
    }


    public DetectionHistory getDetectionHistoryById(Long id) {
        return (DetectionHistory) cache.get("history_" + id);
    }

    public List<DetectionHistory> getDetectionHistoriesByUserId(Long userId) {
        return (List<DetectionHistory>) cache.get("histories_user_" + userId);
    }

    public List<DetectionHistory> getDetectionHistoriesByEmail(String email) {
        return (List<DetectionHistory>) cache.get("histories_email_" + email);
    }

    public List<DetectionHistory> getDetectionHistoriesByUsername(String username) {
        return (List<DetectionHistory>) cache.get("histories_username_" + username);
    }

    public List<DetectionHistory> getAllDetectionHistories() {
        return (List<DetectionHistory>) cache.get("all_histories");
    }

    public void putDetectionHistory(DetectionHistory history) {
        cache.put("history_" + history.getId(), history);
    }

    public void putDetectionHistoriesByUserId(Long userId, List<DetectionHistory> histories) {
        cache.put("histories_user_" + userId, histories);
    }

    public void putDetectionHistoriesByEmail(String email, List<DetectionHistory> histories) {
        cache.put("histories_email_" + email, histories);
    }

    public void putDetectionHistoriesByUsername(String username, List<DetectionHistory> histories) {
        cache.put("histories_username_" + username, histories);
    }

    public void putAllDetectionHistories(List<DetectionHistory> histories) {
        cache.put("all_histories", histories);
    }


    public void clearUserCache() {
        cache.keySet().removeIf(key -> key.startsWith("user_") || "all_users".equals(key));
    }

    public void clearDetectionHistoryCache() {
        cache.keySet().removeIf(key -> key.startsWith("history_") || key.startsWith("histories_") || "all_histories".equals(key));
    }

    public void clearAllCache() {
        cache.clear();
    }
}