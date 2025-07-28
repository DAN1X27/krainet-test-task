package com.example.auth_service.controllers;

import com.example.auth_service.api.UserAPI;
import com.example.auth_service.dto.ShowUserDTO;
import com.example.auth_service.dto.UpdateUserDTO;
import com.example.auth_service.services.UserService;
import com.example.auth_service.util.RequestErrorsHandler;
import com.example.auth_service.util.UserException;
import com.example.auth_service.util.UserValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User")
@RequiredArgsConstructor
public class UserController implements UserAPI {

    private final UserService userService;

    private final UserValidator validator;

    @Override
    @GetMapping
    public ResponseEntity<ShowUserDTO> show() {
        return new ResponseEntity<>(userService.show(), HttpStatus.OK);
    }

    @Override
    @PutMapping
    public ResponseEntity<HttpStatus> update(@RequestBody UpdateUserDTO updateUserDTO, BindingResult bindingResult) {
        validator.validate(updateUserDTO, bindingResult);
        RequestErrorsHandler.handleErrors(bindingResult, UserException.class);
        userService.update(updateUserDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @DeleteMapping
    public ResponseEntity<HttpStatus> delete() {
        userService.delete();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
