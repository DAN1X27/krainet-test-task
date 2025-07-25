package com.example.auth_service.services;

import com.example.auth_service.dto.KafkaUserDTO;
import com.example.auth_service.mapper.UserMapper;
import com.example.auth_service.models.User;
import com.example.auth_service.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, KafkaUserDTO> kafkaTemplate;

    private final UsersRepository usersRepository;

    private final UserMapper mapper;

    @Value("${kafka-topics.created-user}")
    private String createdUserTopic;

    @Value("${kafka-topics.updated-user}")
    private String updatedUserTopic;

    @Value("${kafka-topics.deleted-user}")
    private String deletedUserTopic;

    public void sendCreatedUserMessage(User user) {
        send(createdUserTopic, user);
    }

    public void sendUpdatedUserMessage(User user) {
        send(updatedUserTopic, user);
    }

    public void sendDeletedUserMessage(User user) {
        send(deletedUserTopic, user);
    }

    private void send(String topic, User user) {
        usersRepository.findAllByRole(User.Role.ROLE_ADMIN).forEach(admin -> kafkaTemplate
                .send(topic, admin.getEmail(), mapper.toKafkaUserDTO(user)));
    }

}
