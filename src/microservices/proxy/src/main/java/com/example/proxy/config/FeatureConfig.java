package com.example.proxy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "feature")
public class FeatureConfig {
    private boolean newMoviesEnabled;

    public boolean isNewMoviesEnabled() {
        return newMoviesEnabled;
    }

    public void setNewMoviesEnabled(boolean newMoviesEnabled) {
        this.newMoviesEnabled = newMoviesEnabled;
    }
}