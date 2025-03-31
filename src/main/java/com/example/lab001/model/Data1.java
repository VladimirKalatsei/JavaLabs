package com.example.lab001.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class Data1
{
    @JsonProperty("detections")
    private List<Detection> detections;
}