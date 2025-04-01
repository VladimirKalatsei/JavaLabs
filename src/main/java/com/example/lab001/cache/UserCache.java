package com.example.lab001.cache;

import com.example.lab001.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserCache {
    private final Map<Long, User> userByIdCache = new HashMap<>();
    private final Map<String, User> userByEmailCache = new HashMap<>();
    private List<User> allUsersCache;

    public User getById(Long id) {
        return userByIdCache.get(id);
    }

    public User getByEmail(String email) {
        return userByEmailCache.get(email);
    }

    public List<User> getAllUsers() {
        return allUsersCache;
    }

    public void putById(Long id, User user) {
        userByIdCache.put(id, user);
    }

    public void putByEmail(String email, User user) {
        userByEmailCache.put(email, user);
    }

    public void putAllUsers(List<User> users) {
        allUsersCache = users;
    }

    public void clear() {
        userByIdCache.clear();
        userByEmailCache.clear();
        allUsersCache = null;
    }
}