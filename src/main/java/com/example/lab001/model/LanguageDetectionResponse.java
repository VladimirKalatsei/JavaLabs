package com.example.lab001.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LanguageDetectionResponse {
    @JsonProperty("data")
    private Data data;
}