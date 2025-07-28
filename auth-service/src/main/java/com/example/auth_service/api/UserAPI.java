package com.example.auth_service.api;

import com.example.auth_service.dto.ShowUserDTO;
import com.example.auth_service.dto.UpdateUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface UserAPI {

    @Operation(summary = "Возвращает данные текущего пользователя")
    ResponseEntity<ShowUserDTO> show();

    @Operation(summary = "Обновляет данные текущего пользователя")
    ResponseEntity<HttpStatus> update(UpdateUserDTO updateUserDTO, BindingResult bindingResult);

    @Operation(summary = "Удаляет текущего пользователя")
    ResponseEntity<HttpStatus> delete();

}
