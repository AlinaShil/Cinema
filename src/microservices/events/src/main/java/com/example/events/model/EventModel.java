package com.example.events.model;

import java.time.Instant;

public record EventModel(String id, String type, Instant timestamp, Object payload) {}