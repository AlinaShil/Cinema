package com.example.proxy.controller;

import com.example.proxy.config.FeatureConfig;
import com.example.proxy.client.MovieService;
import com.example.proxy.client.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        this.userService = userService;
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Map<String, Object>>> getAllMovies() {
        boolean useNew = feature.isNewMoviesEnabled()
                && ThreadLocalRandom.current().nextInt(100) < feature.getMoviesMigrationPercent();

        List<Map<String, Object>> result = useNew
                ? movieService.getAllFromNew()
                : movieService.getAllFromLegacy();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Map<String, Object>> getMovieById(@PathVariable String id) {
        Map<String, Object> result = feature.isNewMoviesEnabled()
                ? movieService.getByIdFromNew(id)
                : movieService.getByIdFromLegacy(id);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        boolean useNew = feature.isNewUsersEnabled();

        Object result = useNew
                ? userService.getAllFromNew()
                : userService.getAllFromLegacy();

        return ResponseEntity.ok(result);
    }
}