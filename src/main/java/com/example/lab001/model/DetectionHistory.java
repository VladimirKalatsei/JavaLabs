package com.example.lab001.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "detection_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetectionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(nullable = false)
    private String detectedLanguage;

    // Убедитесь, что это поле инициализируется при создании объекта
    @Column(nullable = false)
    private LocalDateTime detectionTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    // Метод для установки значения detectionTime на текущее время
    @PrePersist
    public void onCreate() {
        this.detectionTime = LocalDateTime.now();
    }
}