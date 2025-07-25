package com.example.auth_service.util;

import com.example.auth_service.dto.RequestUserDTO;
import com.example.auth_service.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UsersRepository usersRepository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return RequestUserDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        RequestUserDTO dto = (RequestUserDTO) target;
        usersRepository.findByEmail(dto.getEmail()).ifPresent(user -> errors
                .rejectValue("email", "", "This email is already in use"));
        usersRepository.findByUsername(dto.getUsername()).ifPresent(user -> errors
                .rejectValue("username", "", "This username is already in use"));
    }
}
