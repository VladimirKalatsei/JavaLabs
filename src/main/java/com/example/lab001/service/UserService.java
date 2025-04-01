package com.example.lab001.service;

import com.example.lab001.model.User;
import com.example.lab001.repository.UserRepository;
import com.example.lab001.exception.ResourceAlreadyExistsException;
import com.example.lab001.cache.CommonCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CommonCache commonCache;

    public List<User> findAll() {
        List<User> cachedUsers = commonCache.getAllUsers();
        if (cachedUsers != null) {
            return cachedUsers;
        }
        List<User> users = userRepository.findAll();
        commonCache.putAllUsers(users);
        return users;
    }

    public User findById(Long id) {
        User cachedUser = commonCache.getUserById(id);
        if (cachedUser != null) {
            return cachedUser;
        }
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            commonCache.putUser(user);
        }
        return user;
    }

    public User findByEmail(String email) {
        User cachedUser = commonCache.getUserByEmail(email);
        if (cachedUser != null) {
            return cachedUser;
        }
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            commonCache.putUser(user);
        }
        return user;
    }

    @Transactional
    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }
        User createdUser = userRepository.save(user);
        commonCache.putUser(createdUser);
        commonCache.clearUserCache();
        return createdUser;
    }

    @Transactional
    public User update(Long id, User user) {
        user.setId(id);
        User updatedUser = userRepository.save(user);
        commonCache.putUser(updatedUser);
        commonCache.clearUserCache();
        return updatedUser;
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
        commonCache.clearUserCache();
    }
}