package com.example.auth_service.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(String error, LocalDateTime timestamp) {
}
