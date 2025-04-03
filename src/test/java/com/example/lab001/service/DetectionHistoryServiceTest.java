package com.example.lab001.service;

import com.example.lab001.exception.ResourceNotFoundException;
import com.example.lab001.model.DetectionHistory;
import com.example.lab001.model.User;
import com.example.lab001.repository.DetectionHistoryRepository;
import com.example.lab001.cache.CommonCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DetectionHistoryServiceTest {

    @InjectMocks
    private DetectionHistoryService detectionHistoryService;

    @Mock
    private DetectionHistoryRepository detectionHistoryRepository;

    @Mock
    private CommonCache commonCache;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDetectionHistory_ShouldSaveHistory_WhenValidDataProvided() {
        DetectionHistory history = new DetectionHistory();
        history.setText("Test text");
        history.setDetectedLanguage("en");
        history.setUser(new User(1L, "username", "user@example.com", null));

        when(detectionHistoryRepository.save(any(DetectionHistory.class))).thenReturn(history);

        DetectionHistory createdHistory = detectionHistoryService.create(history);

        assertNotNull(createdHistory);
        assertEquals("Test text", createdHistory.getText());
        verify(detectionHistoryRepository, times(1)).save(history);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void findById_ShouldReturnHistory_WhenExists(Long id) {
        DetectionHistory history = new DetectionHistory();
        when(detectionHistoryRepository.findById(id)).thenReturn(Optional.of(history));

        DetectionHistory foundHistory = detectionHistoryService.findById(id);

        assertNotNull(foundHistory);
    }

    @Test
    void findById_ShouldThrowException_WhenNotExists() {
        when(detectionHistoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> detectionHistoryService.findById(1L));
    }

    @Test
    void findAll_ShouldReturnListOfHistories() {
        DetectionHistory history1 = new DetectionHistory();
        DetectionHistory history2 = new DetectionHistory();
        when(detectionHistoryRepository.findAll()).thenReturn(Arrays.asList(history1, history2));

        List<DetectionHistory> histories = detectionHistoryService.findAll();

        assertEquals(2, histories.size());
    }

    @Test
    void findByUserId_ShouldReturnHistories_WhenExists() {
        DetectionHistory history = new DetectionHistory();
        when(detectionHistoryRepository.findByUserId(1L)).thenReturn(Arrays.asList(history));

        List<DetectionHistory> histories = detectionHistoryService.findByUserId(1L);

        assertEquals(1, histories.size());
    }

    @Test
    void findByUserId_ShouldReturnEmptyList_WhenNoHistoriesFound() {
        when(detectionHistoryRepository.findByUserId(1L)).thenReturn(Collections.emptyList());

        List<DetectionHistory> histories = detectionHistoryService.findByUserId(1L);

        assertTrue(histories.isEmpty());
    }

    @Test
    void deleteDetectionHistory_ShouldInvokeDeleteById() {
        doNothing().when(detectionHistoryRepository).deleteById(1L);

        detectionHistoryService.delete(1L);

        verify(detectionHistoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateDetectionHistory_ShouldReturnUpdatedHistory() {
        DetectionHistory existingHistory = new DetectionHistory();
        existingHistory.setId(1L);
        existingHistory.setText("Old text");

        DetectionHistory updatedHistory = new DetectionHistory();
        updatedHistory.setText("Updated text");

        when(detectionHistoryRepository.findById(1L)).thenReturn(Optional.of(existingHistory));
        when(detectionHistoryRepository.save(any(DetectionHistory.class))).thenReturn(updatedHistory);

        DetectionHistory result = detectionHistoryService.update(1L, updatedHistory);

        assertEquals("Updated text", result.getText());
        verify(detectionHistoryRepository, times(1)).save(updatedHistory);
    }

    @Test
    void findByEmail_ShouldReturnHistories_WhenExists() {
        DetectionHistory history = new DetectionHistory();
        history.setUser(new User(1L, "username", "user@example.com", null));
        when(detectionHistoryRepository.findByEmail("user@example.com")).thenReturn(Arrays.asList(history));

        List<DetectionHistory> histories = detectionHistoryService.findByEmail("user@example.com");

        assertEquals(1, histories.size());
    }

    @Test
    void findByEmail_ShouldReturnEmptyList_WhenNoHistoriesFound() {
        when(detectionHistoryRepository.findByEmail("user@example.com")).thenReturn(Collections.emptyList());

        List<DetectionHistory> histories = detectionHistoryService.findByEmail("user@example.com");

        assertTrue(histories.isEmpty());
    }
}