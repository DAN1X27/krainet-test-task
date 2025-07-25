package com.example.notifications_service.kafka_listeners;

import com.example.notifications_service.dto.UserDTO;
import com.example.notifications_service.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatedUserListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "${kafka-topics.created-user}", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "containerFactory")
    public void sendCreatedUserNotification(ConsumerRecord<String, UserDTO> record) {
        UserDTO userDTO = record.value();
        String subject = "Создан пользователь " + userDTO.getUsername();
        String text = String.format(
                "Создан пользователь с именем - %s, паролем - %s и почтой - %s",
                userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail()
        );
        notificationService.sendNotification(record.key(), subject, text);
    }

}
