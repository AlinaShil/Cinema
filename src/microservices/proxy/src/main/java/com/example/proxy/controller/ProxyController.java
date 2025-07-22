package com.example.proxy.controller;

import com.example.proxy.config.FeatureConfig;
import com.example.proxy.client.MoviesClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/movies")
public class ProxyController {

    private final FeatureConfig featureConfig;
    private final MoviesClient moviesClient;
    private final RestTemplate restTemplate;
    private final String legacyUrl;

    public ProxyController(FeatureConfig featureConfig,
                           MoviesClient moviesClient,
                           @Value("${movies.legacy.base-url}") String legacyUrl) {
        this.featureConfig = featureConfig;
        this.moviesClient = moviesClient;
        this.restTemplate = new RestTemplate();
        this.legacyUrl = legacyUrl;
    }

    @GetMapping
    public ResponseEntity<?> getAllMovies() {
        if (featureConfig.isNewMoviesEnabled()) {
            return ResponseEntity.ok(moviesClient.getAllMovies());
        }
        // Proxy to legacy
        Object response = restTemplate.getForObject(legacyUrl, Object.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable String id) {
        if (featureConfig.isNewMoviesEnabled()) {
            return ResponseEntity.ok(moviesClient.getMovieById(id));
        }
        String url = String.format("%s/%s", legacyUrl, id);
        Object response = restTemplate.getForObject(url, Object.class);
        return ResponseEntity.ok(response);
    }
}