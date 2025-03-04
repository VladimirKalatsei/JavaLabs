package com.example.lab001.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Detection
{
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