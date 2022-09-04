package com.tinyurl.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HashController {

    @GetMapping("/hello")
    public String index() {
        int a = 1;
        int b = 2;
        int c = a + b;
        return "Greetings from Spring Boot!";
    }

}
