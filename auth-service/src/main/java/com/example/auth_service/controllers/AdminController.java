package com.example.auth_service.controllers;

import com.example.auth_service.api.AdminAPI;
import com.example.auth_service.dto.AdminShowUserDTO;
import com.example.auth_service.dto.UpdateUserDTO;
import com.example.auth_service.services.AdminUserService;
import com.example.auth_service.util.RequestErrorsHandler;
import com.example.auth_service.util.UserException;
import com.example.auth_service.util.UserValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin")
@RequiredArgsConstructor
public class AdminController implements AdminAPI {

    private final AdminUserService adminUserService;

    private final UserValidator validator;

    @Override
    @GetMapping
    public ResponseEntity<List<AdminShowUserDTO>> findAll(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(adminUserService.findAll(page, size), HttpStatus.OK);
    }

    @Override
    @GetMapping("/find")
    public ResponseEntity<AdminShowUserDTO> find(@RequestParam(required = false) String username,
                                            @RequestParam(required = false) String email) {
        if (username != null) {
            return new ResponseEntity<>(adminUserService.findByUsername(username), HttpStatus.OK);
        } else if (email != null) {
            return new ResponseEntity<>(adminUserService.findByEmail(email), HttpStatus.OK);
        } else {
            throw new UserException("Username or email is required");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable long id, @RequestBody UpdateUserDTO updateUserDTO,
                                             BindingResult bindingResult) {
        validator.validate(updateUserDTO, bindingResult);
        RequestErrorsHandler.handleErrors(bindingResult, UserException.class);
        adminUserService.update(id, updateUserDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        adminUserService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
