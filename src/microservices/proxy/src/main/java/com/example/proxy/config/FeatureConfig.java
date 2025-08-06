package com.example.proxy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "feature")
public class FeatureConfig {
    private boolean newMoviesEnabled;
    private boolean newUsersEnabled;
    /** Процент трафика в новый сервис movies */
    private int moviesMigrationPercent;

    public boolean isNewMoviesEnabled() {
        return newMoviesEnabled;
    }

    public void setNewMoviesEnabled(boolean newMoviesEnabled) {
        this.newMoviesEnabled = newMoviesEnabled;
    }

    public int getMoviesMigrationPercent() {
        return moviesMigrationPercent;
    }
    public void setMoviesMigrationPercent(int moviesMigrationPercent) {
        this.moviesMigrationPercent = moviesMigrationPercent;
    }

    public boolean isNewUsersEnabled() {   // <- добавлено
        return newUsersEnabled;
    }
    public void setNewUsersEnabled(boolean newUsersEnabled) {
        this.newUsersEnabled = newUsersEnabled;
    }
}