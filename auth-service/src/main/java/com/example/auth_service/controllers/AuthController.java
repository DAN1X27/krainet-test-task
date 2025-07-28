package com.example.auth_service.controllers;

import com.example.auth_service.api.AuthAPI;
import com.example.auth_service.dto.CreateUserDTO;
import com.example.auth_service.dto.LoginDTO;
import com.example.auth_service.dto.TokenDTO;
import com.example.auth_service.security.UserDetailsImpl;
import com.example.auth_service.services.UserService;
import com.example.auth_service.util.AuthException;
import com.example.auth_service.util.RequestErrorsHandler;
import com.example.auth_service.util.JWTUtil;
import com.example.auth_service.util.UserValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth")
@RequiredArgsConstructor
public class AuthController implements AuthAPI {

    private final UserService userService;

    private final AuthenticationProvider authenticationProvider;

    private final JWTUtil jwtUtil;

    private final UserValidator userValidator;

    @Override
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDTO, BindingResult bindingResult) {
        RequestErrorsHandler.handleErrors(bindingResult, AuthException.class);
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationProvider.authenticate(authToken);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.user().getEmail());
            return new ResponseEntity<>(new TokenDTO(token), HttpStatus.CREATED);
        } catch (BadCredentialsException e) {
            throw new AuthException("Incorrect password");
        }
    }

    @Override
    @PostMapping("/registration")
    public ResponseEntity<TokenDTO> registration(@RequestBody @Valid CreateUserDTO createUserDTO,
                                                 BindingResult bindingResult) {
        RequestErrorsHandler.handleErrors(bindingResult, AuthException.class);
        userValidator.validate(createUserDTO, bindingResult);
        RequestErrorsHandler.handleErrors(bindingResult, AuthException.class);
        userService.create(createUserDTO);
        String token = jwtUtil.generateToken(createUserDTO.getEmail());
        return new ResponseEntity<>(new TokenDTO(token), HttpStatus.CREATED);
    }

}
