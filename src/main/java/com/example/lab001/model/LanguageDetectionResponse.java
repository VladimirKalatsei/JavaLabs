package com.example.lab001.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LanguageDetectionResponse {

    @JsonProperty("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @JsonProperty("detections")
        private List<Detection> detections;

        public List<Detection> getDetections() {
            return detections;
        }

        public void setDetections(List<Detection> detections) {
            this.detections = detections;
        }
    }

    public static class Detection {
        @JsonProperty("language")
        private String language;

        @JsonProperty("isReliable")
        private boolean isReliable;

        @JsonProperty("confidence")
        private double confidence;

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public boolean isReliable() {
            return isReliable;
        }

        public void setReliable(boolean reliable) {
            isReliable = reliable;
        }

        public double getConfidence() {
            return confidence;
        }

        public void setConfidence(double confidence) {
            this.confidence = confidence;
        }
    }
}