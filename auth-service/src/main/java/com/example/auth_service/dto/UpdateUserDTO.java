package com.example.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateUserDTO implements RequestUserDTO {

    private String username;

    private String email;

    private String password;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

}
