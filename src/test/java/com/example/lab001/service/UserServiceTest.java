
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

    @Mock
    private RequestCounter requestCounter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnSavedUserWhenEmailIsUnique() {
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("user1@example.com");
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.create(user);

        assertNotNull(createdUser);
        assertEquals("user1@example.com", createdUser.getEmail());
        verify(userRepository, times(1)).save(user);
        verify(requestCounter, times(1)).increment();
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("user1@example.com");
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.create(user));
        verify(userRepository, never()).save(any());
        verify(requestCounter, never()).increment();
    }

    @ParameterizedTest
    @ValueSource(strings = {"user1@example.com", "user2@example.com"})
    void shouldReturnUserWhenEmailExists(String email) {
        User user = mock(User.class);
        when(user.getEmail()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User foundUser = userService.findByEmail(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
        verify(requestCounter, times(1)).increment();
    }

    @Test
    void shouldReturnListOfUsersWhenFindAll() {
        User user1 = mock(User.class);
        when(user1.getUsername()).thenReturn("user1");
        User user2 = mock(User.class);
        when(user2.getUsername()).thenReturn("user2");
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> users = userService.findAll();

        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
        verify(requestCounter, times(1)).increment();
    }

    @Test
    void shouldReturnUpdatedUserWhenUserExists() {
        User existingUser = mock(User.class);
        when(existingUser.getEmail()).thenReturn("user1@example.com");
        when(existingUser.getUsername()).thenReturn("user1");
        User updatedUser = mock(User.class);
        when(updatedUser.getUsername()).thenReturn("updatedUser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.update(1L, updatedUser);

        assertNotNull(result);
        assertEquals("updatedUser", result.getUsername());
        verify(userRepository, times(1)).save(updatedUser);
        verify(requestCounter, times(1)).increment();
    }

    @Test
    void shouldThrowExceptionWhenUserToUpdateDoesNotExist() {
        User updatedUser = mock(User.class);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.update(1L, updatedUser));
        verify(userRepository, never()).save(any());
        verify(requestCounter, never()).increment();
    }

    @Test
    void shouldInvokeDeleteWhenDeletingUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.delete(1L);

        verify(userRepository, times(1)).deleteById(1L);
        verify(requestCounter, times(1)).increment();
    }

    @Test
    void shouldReturnNullWhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User foundUser = userService.findById(1L);

        assertNull(foundUser);
        verify(requestCounter, times(1)).increment();
    }

    @Test
    void shouldReturnUserWhenUserExists() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("user1");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1L);

        assertNotNull(foundUser);
        assertEquals("user1", foundUser.getUsername());
        verify(requestCounter, times(1)).increment();
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersExist() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> users = userService.findAll();

        assertTrue(users.isEmpty());
        verify(requestCounter, times(1)).increment();
    }
}