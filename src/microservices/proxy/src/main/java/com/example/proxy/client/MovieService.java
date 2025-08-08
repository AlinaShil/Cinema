package com.example.proxy.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Collections;
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

    // Для JSON-массива можно взять массив Map'ов и обернуть в List
    public List<Map<String, Object>> getAllFromNew() {
        Map<String,Object>[] arr = rest.getForObject(newUrl, Map[].class);
        return arr == null
                ? Collections.emptyList()
                : Arrays.asList(arr);
    }

    public Map<String, Object> getByIdFromNew(String id) {
        return rest.getForObject(newUrl + "/" + id, Map.class);
    }

    public List<Map<String, Object>> getAllFromLegacy() {
        Map<String,Object>[] arr = rest.getForObject(legacyUrl, Map[].class);
        return arr == null
                ? Collections.emptyList()
                : Arrays.asList(arr);
    }

    public Map<String, Object> getByIdFromLegacy(String id) {
        return rest.getForObject(legacyUrl + "/" + id, Map.class);
    }
}