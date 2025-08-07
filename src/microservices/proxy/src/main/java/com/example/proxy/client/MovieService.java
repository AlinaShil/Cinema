package com.example.proxy.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResponseEntity;

import java.util.List;
import java.util.Map;

@Service
public class MovieService {
    private final RestTemplate rest;
    private final String newUrl;
    private final String legacyUrl;

    public MovieService(RestTemplate rest,
                        @Value("${movies.new.base-url}") String newUrl,
                        @Value("${movies.legacy.base-url}") String legacyUrl) {
        this.rest = rest;
        this.newUrl = newUrl;
        this.legacyUrl = legacyUrl;
    }

    // Возвращаем список фильмов
    public List<Map<String, Object>> getAllFromNew() {
        ResponseEntity<List<Map<String,Object>>> resp = rest.exchange(
                newUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return resp.getBody();
    }

    public Map<String, Object> getByIdFromNew(String id) {
        return rest.getForObject(newUrl + "/" + id, Map.class);
    }

    // То же для legacy
    public List<Map<String, Object>> getAllFromLegacy() {
        ResponseEntity<List<Map<String,Object>>> resp = rest.exchange(
                legacyUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return resp.getBody();
    }

    public Map<String, Object> getByIdFromLegacy(String id) {
        return rest.getForObject(legacyUrl + "/" + id, Map.class);
    }
}