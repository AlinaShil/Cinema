package com.example.events.service;

import com.example.events.model.EventModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class EventService {
    private final Logger log = LoggerFactory.getLogger(EventService.class);
    private final KafkaTemplate<String, EventModel> kafka;
    private final String userTopic;
    private final String paymentTopic;
    private final String movieTopic;

    public EventService(KafkaTemplate<String, EventModel> kafka,
                        @Value("${topic.user}") String userTopic,
                        @Value("${topic.payment}") String paymentTopic,
                        @Value("${topic.movie}") String movieTopic) {
        this.kafka = kafka;
        this.userTopic = userTopic;
        this.paymentTopic = paymentTopic;
        this.movieTopic = movieTopic;
    }

    public void produce(String type, Object payload) {
        String topic = switch(type) {
            case "user" -> userTopic;
            case "payment" -> paymentTopic;
            default -> movieTopic;
        };
        EventModel event = new EventModel(UUID.randomUUID().toString(), type, Instant.now(), payload);
        kafka.send(topic, event);
        log.info("Produced event {} to {}", event, topic);
    }
}