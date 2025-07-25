package com.example.notifications_service.kafka_listeners;

import com.example.notifications_service.dto.UserDTO;
import com.example.notifications_service.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletedUserListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "${kafka-topics.deleted-user}", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "containerFactory")
    public void sendDeletedUserNotification(ConsumerRecord<String, UserDTO> record) {
        UserDTO userDTO = record.value();
        String subject = "Удален пользователь " + userDTO.getUsername();
        String text = String.format(
                "Удален пользователь с именем - %s, паролем - %s и почтой - %s",
                userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail()
        );
        notificationService.sendNotification(record.key(), subject, text);
    }

}
