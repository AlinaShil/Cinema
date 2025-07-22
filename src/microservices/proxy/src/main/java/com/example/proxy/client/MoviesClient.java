package com.example.proxy.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "new-movies", url = "${movies.new.base-url}")
public interface MoviesClient {

    @GetMapping
    Map<String, Object> getAllMovies();
    @GetMapping("/{id}")
    Map<String, Object> getMovieById(@PathVariable("id") String id);
}