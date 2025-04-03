package com.example.lab001.service;

import com.example.lab001.model.DetectionHistory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LanguageDetectionService {
    private final DetectionHistoryService detectionHistoryService;

    @Value("${detectlanguage.api.key}")
    private String apiKey;

    private static final String DETECT_LANGUAGE_URL = "https://ws.detectlanguage.com/0.2/detect";

    public String detectLanguage(String text) {
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = DETECT_LANGUAGE_URL + "?q=" + text + "&key=" + apiKey;

        try {
            String response = restTemplate.getForObject(requestUrl, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            Map<String, Object> data = (Map<String, Object>) responseMap.get("data");
            if (data != null && data.containsKey("detections")) {
                java.util.List<Map<String, Object>> detections =
                        (java.util.List<Map<String, Object>>) data.get("detections");

                if (!detections.isEmpty()) {
                    return (String) detections.get(0).get("language");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Unable to detect language";
    }

    public DetectionHistory saveDetectionHistory(DetectionHistory detectionHistory) {
        return detectionHistoryService.create(detectionHistory);
    }
}