package com.example.lab001.controller;

import com.example.lab001.model.DetectionHistory;
import com.example.lab001.model.User;
import com.example.lab001.service.LanguageDetectionService;
import com.example.lab001.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LanguageDetectionController {
    private final LanguageDetectionService languageDetectionService;
    private final UserService userService;

    @GetMapping("/detect-language")
    public String detectLanguage(
            @RequestParam String text,
            @RequestParam Long userId) {

        String detectedLanguage = languageDetectionService.detectLanguage(text);

        User user = userService.findById(userId);
        if (user != null) {
            DetectionHistory history = DetectionHistory.builder()
                    .text(text)
                    .detectedLanguage(detectedLanguage)
                    .user(user)
                    .build();

            languageDetectionService.saveDetectionHistory(history);
        }

        return detectedLanguage;
    }
}