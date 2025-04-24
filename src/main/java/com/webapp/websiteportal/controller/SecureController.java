package com.webapp.websiteportal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class SecureController {

    @GetMapping("/data")
    public ResponseEntity<?> getSecureData() {
        return ResponseEntity.ok("This is secure data");
    }
}
