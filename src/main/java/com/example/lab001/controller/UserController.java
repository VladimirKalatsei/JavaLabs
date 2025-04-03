package com.example.lab001.controller;

import com.example.lab001.exception.ResourceAlreadyExistsException;
import com.example.lab001.model.User;
import com.example.lab001.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.findAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.findById(id);
            return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            if (user.getEmail() == null || user.getEmail().isEmpty() ||
                    user.getUsername() == null || user.getUsername().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            User createdUser = userService.create(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<User>> createUsersBulk(@RequestBody List<User> users) {
        try {
            if (users == null || users.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            for (User user : users) {
                if (user.getEmail() == null || user.getEmail().isEmpty() ||
                        user.getUsername() == null || user.getUsername().isEmpty()) {
                    return ResponseEntity.badRequest().build();
                }
            }

            List<User> createdUsers = userService.createUsersBulk(users);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUsers);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            if (user.getEmail() == null || user.getEmail().isEmpty() ||
                    user.getUsername() == null || user.getUsername().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            user.setId(id);
            User updatedUser = userService.update(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}