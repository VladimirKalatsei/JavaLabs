package com.example.lab001.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Detection {
    @JsonProperty("language")
    private String language;

    @JsonProperty("isReliable")
    private boolean isReliable;

    @JsonProperty("confidence")
    private double confidence;
}