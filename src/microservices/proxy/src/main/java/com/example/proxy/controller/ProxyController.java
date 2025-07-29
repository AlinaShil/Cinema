package com.example.proxy.controller;

import com.example.proxy.config.FeatureConfig;
import com.example.proxy.client.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/movies")
public class ProxyController {

    private final FeatureConfig feature;
    private final MovieService service;

    public ProxyController(FeatureConfig feature, MovieService service) {
        this.feature = feature;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        if (feature.isNewMoviesEnabled()) {
            Map<String, Object> movies = service.getAllFromNew();
            return ResponseEntity.ok(movies);
        }
        return ResponseEntity.ok(service.getAllFromLegacy());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        if (feature.isNewMoviesEnabled()) {
            Map<String, Object> movie = service.getByIdFromNew(id);
            return ResponseEntity.ok(movie);
        }
        return ResponseEntity.ok(service.getByIdFromLegacy(id));
    }

    @GetMapping("/api/users")
    public ResponseEntity<?> getAllUsers() {
        Object users = feature.isNewUsersEnabled()
                ? userService.getAllFromNew()
                : userService.getAllFromLegacy();
        return ResponseEntity.ok(users);
    }
}