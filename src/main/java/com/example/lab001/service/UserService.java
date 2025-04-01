package com.example.lab001.service;

import com.example.lab001.cache.UserCache;
import com.example.lab001.model.User;
import com.example.lab001.repository.UserRepository;
import com.example.lab001.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserCache userCache;

    public List<User> findAll() {
        List<User> cachedUsers = userCache.getAllUsers();
        if (cachedUsers != null) {
            return cachedUsers;
        }

        List<User> users = userRepository.findAll();
        userCache.putAllUsers(users);
        return users;
    }

    public User findById(Long id) {
        User cachedUser = userCache.getById(id);
        if (cachedUser != null) {
            return cachedUser;
        }

        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userCache.putById(id, user);
        }
        return user;
    }

    public User findByEmail(String email) {
        User cachedUser = userCache.getByEmail(email);
        if (cachedUser != null) {
            return cachedUser;
        }

        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            userCache.putByEmail(email, user);
        }
        return user;
    }

    @Transactional
    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }
        User createdUser = userRepository.save(user);
        userCache.clear();
        return createdUser;
    }

    @Transactional
    public User update(Long id, User user) {
        user.setId(id);
        User updatedUser = userRepository.save(user);
        userCache.clear();
        return updatedUser;
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
        userCache.clear();
    }
}