package com.example.auth_service.security;

import com.example.auth_service.models.User;
import com.example.auth_service.repositories.UsersRepository;
import com.example.auth_service.util.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = usersRepository.findByEmailOrUsername(username, username)
                .orElseThrow(() -> new AuthException("User not found"));
        return new UserDetailsImpl(user);
    }
}
