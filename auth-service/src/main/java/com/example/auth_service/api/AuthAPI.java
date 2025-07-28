package com.example.auth_service.api;

import com.example.auth_service.dto.CreateUserDTO;
import com.example.auth_service.dto.LoginDTO;
import com.example.auth_service.dto.TokenDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface AuthAPI {

    @Operation(summary = "Создает и возвращает JWT токен для пользователя")
    ResponseEntity<TokenDTO> login(LoginDTO loginDTO, BindingResult bindingResult);

    @Operation(summary = "Регистрирует пользователя и возвращает JWT токен")
    ResponseEntity<TokenDTO> registration(CreateUserDTO createUserDTO, BindingResult bindingResult);

}
