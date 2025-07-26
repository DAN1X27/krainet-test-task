package com.example.notifications_service.config;

import com.example.notifications_service.kafka.messages.CreatedUserMessage;
import com.example.notifications_service.kafka.messages.UserMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumersConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserMessage> userMessageContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userMessageConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CreatedUserMessage> createdUserMessageContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CreatedUserMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createdUserMessageConsumerFactory());
        return factory;
    }

    @Bean
    ConsumerFactory<String, CreatedUserMessage> createdUserMessageConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                getProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(CreatedUserMessage.class)
        );
    }

    @Bean
    ConsumerFactory<String, UserMessage> userMessageConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                getProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(UserMessage.class)
        );
    }

    private Map<String, Object> getProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }

}
