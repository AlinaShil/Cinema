package com.example.events.listener;

import com.example.events.model.EventModel;
import com.example.events.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener {
    private final Logger log = LoggerFactory.getLogger(EventListener.class);

    @KafkaListener(topics = "${topic.user}", groupId = "events-group")
    public void listenUser(EventModel event) {
        log.info("Consumed USER event: {}", event);
    }

    @KafkaListener(topics = "${topic.payment}", groupId = "events-group")
    public void listenPayment(EventModel event) {
        log.info("Consumed PAYMENT event: {}", event);
    }

    @KafkaListener(topics = "${topic.movie}", groupId = "events-group")
    public void listenMovie(EventModel event) {
        log.info("Consumed MOVIE event: {}", event);
    }
}