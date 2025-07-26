package com.example.auth_service.config;

import com.example.auth_service.kafka.messages.CreatedUserMessage;
import com.example.auth_service.kafka.messages.UserMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducersConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaTemplate<String, UserMessage> userMessageKafkaTemplate() {
        return new KafkaTemplate<>(userMessageProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, CreatedUserMessage> createdUserMessageKafkaTemplate() {
        return new KafkaTemplate<>(createdUserMessageProducerFactory());
    }

    @Bean
    ProducerFactory<String, CreatedUserMessage> createdUserMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(
                getProps(),
                new StringSerializer(),
                new JsonSerializer<>()
        );
    }

    @Bean
    ProducerFactory<String, UserMessage> userMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(
                getProps(),
                new StringSerializer(),
                new JsonSerializer<>()
        );
    }

    private Map<String, Object> getProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

}
