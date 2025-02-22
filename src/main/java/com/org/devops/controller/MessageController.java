package com.org.devops.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MessageController {

    @GetMapping(value = "/test")
    public ResponseEntity<String> getMessage() {
        return ResponseEntity.ok("Hello World");
    }
}
