package com.example.proxy.controller;

import com.example.proxy.config.FeatureConfig;
import com.example.proxy.client.MovieService;
import com.example.proxy.client.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
public class ProxyController {

    private final FeatureConfig feature;
    private final MovieService movieService;
    private final UserService userService;


    public ProxyController(FeatureConfig feature,
                           MovieService movieService,
                           UserService userService) {
        this.feature = feature;
        this.movieService = movieService;
        this.userService = userService;         // <- присвоение
    }

    @GetMapping("/movies")
    public ResponseEntity<?> getAll() {
        if (feature.isNewMoviesEnabled()) {
            Map<String, Object> movies = movieService.getAllFromNew();
            int pct = feature.getMoviesMigrationPercent();
            int rnd = ThreadLocalRandom.current().nextInt(100);
            if (rnd<pct) {
                return ResponseEntity.ok(movieService.getAllFromNew());
            }
        }
        return ResponseEntity.ok(movieService.getAllFromLegacy());
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        if (feature.isNewMoviesEnabled()) {
            Map<String, Object> movie = movieService.getByIdFromNew(id);
            return ResponseEntity.ok(movie);
        }
        return ResponseEntity.ok(movieService.getByIdFromLegacy(id));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        Object users = feature.isNewUsersEnabled()
                ? userService.getAllFromNew()
                : userService.getAllFromLegacy();
        return ResponseEntity.ok(users);
    }
}