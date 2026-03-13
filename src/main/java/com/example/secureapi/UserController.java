package com.example.secureapi;

import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@RequestMapping("/api")
public class UserController {
    private final JdbcTemplate jdbcTemplate;

    // ⚠️ HARDCODED SECRET — intentional vulnerability for assignment
    private static final String DB_PASSWORD = "supersecret123";

    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ⚠️ SQL INJECTION — intentional vulnerability
    @GetMapping("/search")
    public String searchUser(@RequestParam String username) {
        String query = "SELECT * FROM users WHERE username = '" + username + "'";
        return "Executing: " + query;
    }

    @GetMapping("/health")
    public String health() {
        return "API is running";
    }
}
