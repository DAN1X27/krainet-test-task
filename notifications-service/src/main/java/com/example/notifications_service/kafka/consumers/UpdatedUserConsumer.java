package com.example.notifications_service.kafka.consumers;

import com.example.notifications_service.kafka.messages.UserMessage;
import com.example.notifications_service.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatedUserConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "${kafka-topics.updated-user}", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "userMessageContainerFactory")
    public void sendUpdatedUserNotification(ConsumerRecord<String, UserMessage> record) {
        UserMessage message = record.value();
        String subject = "Изменен пользователь " + message.getUsername();
        String text = String.format(
                "Изменен пользователь с именем - %s и почтой - %s",
                message.getUsername(), message.getEmail()
        );
        notificationService.sendNotification(record.key(), subject, text);
    }

}
