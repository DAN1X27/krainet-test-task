package com.example.auth_service.api;

import com.example.auth_service.dto.AdminShowUserDTO;
import com.example.auth_service.dto.UpdateUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface AdminAPI {

    @Operation(summary = "Возвращает данные всех пользователей на странице")
    ResponseEntity<List<AdminShowUserDTO>> findAll(
            @Parameter(description = "Страница пользователей", example = "0", required = true) int page,
            @Parameter(description = "Количество пользователей на странице", example = "5") int size
    );

    @Operation(summary = "Находит пользователя по имени или почте")
    ResponseEntity<AdminShowUserDTO> find(@Parameter(description = "Имя для поиска") String username,
                                          @Parameter(description = "Почта для поиска") String email);

    @Operation(summary = "Обновляет данные пользователя")
    ResponseEntity<HttpStatus> update(@Parameter(description = "ID пользователя") long id,
                                      UpdateUserDTO updateUserDTO, BindingResult bindingResult);

    @Operation(summary = "Удаляет пользователя")
    ResponseEntity<HttpStatus> delete(@Parameter(description = "ID пользователя") long id);
}
