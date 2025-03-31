package com.example.lab001.cache;

import com.example.lab001.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserCache {
    private final Map<Long, User> cache = new HashMap<>();

    public User get(Long id) {
        return cache.get(id);
    }

    public void put(Long id, User user) {
        cache.put(id, user);
    }

    public void remove(Long id) {
        cache.remove(id);
    }
}