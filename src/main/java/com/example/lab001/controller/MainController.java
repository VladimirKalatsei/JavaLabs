package com.example.lab001.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = {"/", "/users", "/detection", "/history", "/stats"})
    public String index() {
        return "index";
    }
}
