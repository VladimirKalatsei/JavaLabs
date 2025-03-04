package com.example.lab001.controller;

import com.example.lab001.service.LanguageDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguageDetectionController {

    private final LanguageDetectionService languageDetectionService;

    @Autowired
    public LanguageDetectionController(LanguageDetectionService languageDetectionService) {
        this.languageDetectionService = languageDetectionService;
    }

    @GetMapping("/detect-language")
    public String detectLanguage(@RequestParam String text) {
        return languageDetectionService.detectLanguage(text);
    }
}