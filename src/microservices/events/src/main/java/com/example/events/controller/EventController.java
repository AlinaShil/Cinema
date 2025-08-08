package com.example.events.controller;

import com.example.events.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService service;
    public EventController(EventService service) { this.service = service; }

    @PostMapping("/user")
    public ResponseEntity<Map<String, String>> postUser(@RequestBody Map<String,Object> body) {
        service.produce("user", body);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("status", "success"));
    }

    @PostMapping("/payment")
    public ResponseEntity<?> postPayment(@RequestBody Map<String, Object> body) {
        service.produce("payment", body);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("status", "success"));
    }

    @PostMapping("/movie")
    public ResponseEntity<?> postMovie(@RequestBody Map<String, Object> body) {
        service.produce("movie", body);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("status", "success"));
    }
}