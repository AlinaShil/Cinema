package com.example.proxy.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public Map<String, Object> getAllFromNew() {
        return rest.getForObject(newUrl, Map.class);
    }

    public Map<String, Object> getByIdFromNew(String id) {
        return rest.getForObject(newUrl + "/" + id, Map.class);
    }

    public Object getAllFromLegacy() {
        return rest.getForObject(legacyUrl, Object.class);
    }

    public Object getByIdFromLegacy(String id) {
        return rest.getForObject(legacyUrl + "/" + id, Object.class);
    }
}