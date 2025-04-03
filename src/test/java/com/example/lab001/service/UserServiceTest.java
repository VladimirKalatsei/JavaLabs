package com.example.lab001.service;

import com.example.lab001.exception.ResourceAlreadyExistsException;
import com.example.lab001.exception.ResourceNotFoundException;
import com.example.lab001.model.User;
import com.example.lab001.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_shouldReturnSavedUser_whenEmailIsUnique() {
        User user = new User(null, "user1", "user1@example.com", null);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.create(user);

        assertNotNull(createdUser);
        assertEquals("user1@example.com", createdUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createUser_shouldThrowException_whenEmailAlreadyExists() {
        User user = new User(null, "user1", "user1@example.com", null);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.create(user));
        verify(userRepository, never()).save(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"user1@example.com", "user2@example.com"})
    void findByEmail_shouldReturnUser_whenEmailExists(String email) {
        User user = new User(1L, "user1", email, null);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User foundUser = userService.findByEmail(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
    }

    @Test
    void findAll_shouldReturnListOfUsers() {
        User user1 = new User(1L, "user1", "user1@example.com", null);
        User user2 = new User(2L, "user2", "user2@example.com", null);
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> users = userService.findAll();

        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
    }

    @Test
    void updateUser_shouldReturnUpdatedUser_whenUserExists() {
        User existingUser = new User(1L, "user1", "user1@example.com", null);
        User updatedUser = new User(1L, "updatedUser", "user1@example.com", null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.update(1L, updatedUser);

        assertNotNull(result);
        assertEquals("updatedUser", result.getUsername());
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void updateUser_shouldThrowException_whenUserToUpdateDoesNotExist() {
        User updatedUser = new User(1L, "updatedUser", "updated@example.com", null);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.update(1L, updatedUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUser_shouldThrowException_whenEmailIsEmptyDuringUpdate() {
        User updatedUser = new User(1L, "updatedUser", "", null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(updatedUser));

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.update(1L, updatedUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUser_shouldInvokeDeleteById() {
        doNothing().when(userRepository).deleteById(1L);

        userService.delete(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void findById_shouldReturnNull_whenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User foundUser = userService.findById(1L);

        assertNull(foundUser);
    }

    @Test
    void findById_shouldReturnUser_whenUserExists() {
        User user = new User(1L, "user1", "user1@example.com", null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1L);

        assertNotNull(foundUser);
        assertEquals("user1", foundUser.getUsername());
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoUsersExist() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> users = userService.findAll();

        assertTrue(users.isEmpty());
    }
}