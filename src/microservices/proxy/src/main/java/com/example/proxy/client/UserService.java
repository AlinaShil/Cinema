package com.example.proxy.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class UserService {
    private final RestTemplate rest;
    private final String newUrl;
    private final String legacyUrl;
    private final boolean newEnabled;

    public UserService(RestTemplate rest,
                       @Value("${users.new.base-url:}") String newUrl,
                       @Value("${users.legacy.base-url}") String legacyUrl,
                       @Value("${feature.newUsersEnabled:false}") boolean newEnabled) {
        this.rest       = rest;
        this.newUrl     = newUrl;
        this.legacyUrl  = legacyUrl;
        this.newEnabled = newEnabled;
    }

    public Object getAllFromNew() {
        return rest.getForObject(newUrl, Object.class);
    }

    public Object getAllFromLegacy() {
        return rest.getForObject(legacyUrl, Object.class);
    }
}