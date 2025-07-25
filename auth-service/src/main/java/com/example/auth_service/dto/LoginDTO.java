package com.example.auth_service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDTO {

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Password is required")
    private String password;

}
