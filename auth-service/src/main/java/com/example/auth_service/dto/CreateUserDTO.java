package com.example.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateUserDTO implements RequestUserDTO {

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "First name is required")
    @JsonProperty("first_name")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    @JsonProperty("last_name")
    private String lastName;

}
