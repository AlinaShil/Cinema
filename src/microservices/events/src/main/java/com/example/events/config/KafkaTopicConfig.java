package com.example.events.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean(name = "userTopic")
    public NewTopic userTopic(@Value("${topic.user}") String name) {
        return new NewTopic(name, 1, (short)1);
    }
    @Bean(name = "paymentTopic")
    public NewTopic paymentTopic(@Value("${topic.payment}") String name) {
        return new NewTopic(name, 1, (short)1);
    }
    @Bean(name = "movieTopic")
    public NewTopic movieTopic(@Value("${topic.movie}") String name) {
        return new NewTopic(name, 1, (short)1);
    }
}