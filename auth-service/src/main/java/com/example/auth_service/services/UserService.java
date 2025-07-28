package com.example.auth_service.services;

import com.example.auth_service.dto.CreateUserDTO;
import com.example.auth_service.dto.ShowUserDTO;
import com.example.auth_service.dto.UpdateUserDTO;

public interface UserService {

    ShowUserDTO show();

    void create(CreateUserDTO createUserDTO);

    void update(UpdateUserDTO updateUserDTO);

    void delete();

}
