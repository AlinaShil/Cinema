package com.example.events.config;

import com.example.events.model.EventModel;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {


    @Bean
    public ProducerFactory<String, EventModel> producerFactory(KafkaProperties props) {
        DefaultKafkaProducerFactory<String, EventModel> factory =
                new DefaultKafkaProducerFactory<>(
                        props.buildProducerProperties(),
                        new StringSerializer(),
                        new JsonSerializer<>()
                );
        return factory;
    }

    @Bean
    public KafkaTemplate<String, EventModel> kafkaTemplate(ProducerFactory<String, EventModel> pf) {
        return new KafkaTemplate<>(pf);
    }


    @Bean
    public ConsumerFactory<String, EventModel> consumerFactory(KafkaProperties props) {
        JsonDeserializer<EventModel> valueDeserializer =
                new JsonDeserializer<>(EventModel.class);
        // Разрешаем пакеты для десериализации
        valueDeserializer.addTrustedPackages("com.example.events.model");

        return new DefaultKafkaConsumerFactory<>(
                props.buildConsumerProperties(),
                new StringDeserializer(),
                valueDeserializer
        );
    }

    /**
     * Контейнерная фабрика, используемая по умолчанию @KafkaListener.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EventModel>
    kafkaListenerContainerFactory(ConsumerFactory<String, EventModel> cf) {
        ConcurrentKafkaListenerContainerFactory<String, EventModel> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);
        return factory;
    }
}