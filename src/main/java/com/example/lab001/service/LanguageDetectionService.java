package com.example.lab001.service;

import com.example.lab001.model.LanguageDetectionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LanguageDetectionService {

    @Value("${detectlanguage.api.key}")
    private String apiKey;

    private static final String DETECT_LANGUAGE_URL = "https://ws.detectlanguage.com/0.2/detect";

    public String detectLanguage(String text) {
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = DETECT_LANGUAGE_URL + "?q=" + text + "&key=" + apiKey;

        // Отправляем запрос к API Detect Language
        String response = restTemplate.getForObject(requestUrl, String.class);

        // Парсим JSON-ответ
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LanguageDetectionResponse languageDetectionResponse = objectMapper.readValue(response, LanguageDetectionResponse.class);

            // Получаем первый язык из списка
            if (languageDetectionResponse.getData() != null && !languageDetectionResponse.getData().getDetections().isEmpty()) {
                return languageDetectionResponse.getData().getDetections().get(0).getLanguage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Возвращаем сообщение об ошибке, если не удалось распарсить JSON
        return "Unable to detect language";
    }
}