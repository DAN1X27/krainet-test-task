package com.example.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ShowUserDTO {

    private long id;

    private String username;

    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("created_at")
    private LocalDate createdAt;

}
