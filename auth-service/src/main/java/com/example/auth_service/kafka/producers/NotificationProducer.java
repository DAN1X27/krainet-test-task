package com.example.auth_service.kafka.producers;

import com.example.auth_service.dto.CreateUserDTO;
import com.example.auth_service.kafka.messages.CreatedUserMessage;
import com.example.auth_service.kafka.messages.UserMessage;
import com.example.auth_service.mapper.UserMapper;
import com.example.auth_service.models.User;
import com.example.auth_service.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationProducer {

    private final KafkaTemplate<String, UserMessage> userMessageKafkaTemplate;

    private final KafkaTemplate<String, CreatedUserMessage> createdUserMessageKafkaTemplate;

    private final UsersRepository usersRepository;

    private final UserMapper mapper;

    @Value("${kafka-topics.created-user}")
    private String createdUserTopic;

    @Value("${kafka-topics.updated-user}")
    private String updatedUserTopic;

    @Value("${kafka-topics.deleted-user}")
    private String deletedUserTopic;

    public void sendCreatedUserNotification(CreateUserDTO user) {
        usersRepository.findAllByRole(User.Role.ROLE_ADMIN).forEach(admin -> createdUserMessageKafkaTemplate
                .send(createdUserTopic, admin.getEmail(), mapper.toCreatedUserNotificationDTOFromCreateDTO(user)));
    }

    public void sendUpdatedUserNotification(User user) {
        sendUserNotification(updatedUserTopic, user);
    }

    public void sendDeletedUserNotification(User user) {
        sendUserNotification(deletedUserTopic, user);
    }

    private void sendUserNotification(String topic, User user) {
        usersRepository.findAllByRole(User.Role.ROLE_ADMIN).forEach(admin -> userMessageKafkaTemplate
                .send(topic, admin.getEmail(), mapper.toUserNotificationDTO(user)));
    }

}
