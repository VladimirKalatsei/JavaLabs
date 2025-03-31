package com.example.lab001.service;

import com.example.lab001.cache.UserCache;
import com.example.lab001.exception.ResourceAlreadyExistsException;
import com.example.lab001.model.User;
import com.example.lab001.repository.UserRepository;
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
        return userRepository.findAll();
    }

    public User findById(Long id) {
        User cachedUser = userCache.get(id);
        if (cachedUser != null) {
            return cachedUser;
        }
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userCache.put(id, user);
        }
        return user;
    }

    @Transactional
    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }
        User createdUser = userRepository.save(user);
        userCache.put(createdUser.getId(), createdUser);
        return createdUser;
    }

    @Transactional
    public User update(Long id, User user) {
        user.setId(id);
        User updatedUser = userRepository.save(user);
        userCache.put(id, updatedUser);
        return updatedUser;
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
        userCache.remove(id); // Удаляем из кэша
    }
}