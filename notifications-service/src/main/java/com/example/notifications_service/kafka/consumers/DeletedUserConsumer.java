package com.example.notifications_service.kafka.consumers;

import com.example.notifications_service.kafka.messages.UserMessage;
import com.example.notifications_service.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletedUserConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "${kafka-topics.deleted-user}", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "userMessageContainerFactory")
    public void sendDeletedUserNotification(ConsumerRecord<String, UserMessage> record) {
        UserMessage message = record.value();
        String subject = "Удален пользователь " + message.getUsername();
        String text = String.format(
                "Удален пользователь с именем - %s и почтой - %s",
                message.getUsername(), message.getEmail()
        );
        notificationService.sendNotification(record.key(), subject, text);
    }

}
