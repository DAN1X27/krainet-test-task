package com.example.notifications_service.kafka.messages;

import lombok.Data;

@Data
public class UserMessage {

    private String username;

    private String email;

}
