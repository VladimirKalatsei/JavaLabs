package com.example.lab001.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LanguageDetectionResponse {

    @JsonProperty("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}