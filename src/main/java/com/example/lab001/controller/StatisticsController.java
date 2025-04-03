package com.example.lab001.controller;

import com.example.lab001.service.RequestCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StatisticsController {
    private final RequestCounter requestCounter;

    @GetMapping("/api/statistics/requests")
    public ResponseEntity<Integer> getRequestCount()
    {
        int count = requestCounter.getCount();
        return ResponseEntity.ok(count);
    }
}