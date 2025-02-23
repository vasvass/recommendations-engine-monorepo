package com.vasvass.user_profile_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProfileController {

    /**
     * Health check endpoint or "Hello World" test.
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello from User Profile Service!";
    }
}