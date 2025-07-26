package com.example.auth_service.kafka.messages;

import lombok.Data;

@Data
public class CreatedUserMessage {

    private String username;

    private String email;

    private String password;

}
