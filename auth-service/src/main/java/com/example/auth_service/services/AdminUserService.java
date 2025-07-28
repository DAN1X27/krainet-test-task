package com.example.auth_service.services;

import com.example.auth_service.dto.AdminShowUserDTO;
import com.example.auth_service.dto.UpdateUserDTO;

import java.util.List;

public interface AdminUserService {

    List<AdminShowUserDTO> findAll(int page, int size);

    AdminShowUserDTO findByUsername(String username);

    AdminShowUserDTO findByEmail(String email);

    void update(long id, UpdateUserDTO updateUserDTO);

    void delete(long id);

}
