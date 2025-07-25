package com.example.auth_service.controllers;

import com.example.auth_service.dto.ShowUserDTO;
import com.example.auth_service.dto.UpdateUserDTO;
import com.example.auth_service.services.AdminUserService;
import com.example.auth_service.util.RequestErrorsHandler;
import com.example.auth_service.util.UserException;
import com.example.auth_service.util.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminUserService adminUserService;

    private final UserValidator validator;

    @GetMapping
    public ResponseEntity<List<ShowUserDTO>> findAll(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(adminUserService.findAll(page, size), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<ShowUserDTO> find(@RequestParam(required = false) String username,
                                            @RequestParam(required = false) String email) {
        if (username != null) {
            return new ResponseEntity<>(adminUserService.findByUsername(username), HttpStatus.OK);
        } else if (email != null) {
            return new ResponseEntity<>(adminUserService.findByEmail(email), HttpStatus.OK);
        } else {
            throw new UserException("Username or email is required");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable long id, @RequestBody UpdateUserDTO updateUserDTO,
                                             BindingResult bindingResult) {
        validator.validate(updateUserDTO, bindingResult);
        RequestErrorsHandler.handleErrors(bindingResult, UserException.class);
        adminUserService.update(id, updateUserDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id) {
        adminUserService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
