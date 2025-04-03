package com.example.lab001.dto;

import com.example.lab001.model.DetectionHistory;

import java.time.LocalDateTime;

public class DetectionHistoryDTO {
    private Long id;
    private String text;
    private String detectedLanguage;
    private LocalDateTime detectionTime;
    private String username;
    private String email;

    public DetectionHistoryDTO(DetectionHistory history) {
        this.id = history.getId();
        this.text = history.getText();
        this.detectedLanguage = history.getDetectedLanguage();
        this.detectionTime = history.getDetectionTime();
        this.username = history.getUser().getUsername();
        this.email = history.getUser().getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDetectedLanguage() {
        return detectedLanguage;
    }

    public void setDetectedLanguage(String detectedLanguage) {
        this.detectedLanguage = detectedLanguage;
    }

    public LocalDateTime getDetectionTime() {
        return detectionTime;
    }

    public void setDetectionTime(LocalDateTime detectionTime) {
        this.detectionTime = detectionTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}