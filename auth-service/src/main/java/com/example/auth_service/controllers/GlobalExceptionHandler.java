package com.example.auth_service.controllers;

import com.example.auth_service.dto.ErrorResponseDTO;
import com.example.auth_service.util.AuthException;
import com.example.auth_service.util.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserException.class, AuthException.class})
    public ResponseEntity<ErrorResponseDTO> handleExceptions(Exception e) {
        return new ResponseEntity<>(new ErrorResponseDTO(e.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

}
