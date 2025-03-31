package com.example.lab001.repository;

import com.example.lab001.model.DetectionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetectionHistoryRepository extends JpaRepository<DetectionHistory, Long> {
    @Query("SELECT dh FROM DetectionHistory dh WHERE dh.user.username = :username ORDER BY dh.detectionTime DESC")
    List<DetectionHistory> findByUsername(@Param("username") String username);

    // Новый метод для получения историй по userId
    @Query("SELECT dh FROM DetectionHistory dh WHERE dh.user.id = :userId ORDER BY dh.detectionTime DESC")
    List<DetectionHistory> findByUserId(@Param("userId") Long userId);
}