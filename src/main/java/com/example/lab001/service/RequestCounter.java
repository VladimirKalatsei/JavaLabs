package com.example.lab001.service;

import org.springframework.stereotype.Component;

@Component
public class RequestCounter
{
    private int count = 0;

    public synchronized void increment()
    {
        count++;
    }

    public synchronized int getCount()
    {
        return count;
    }
}