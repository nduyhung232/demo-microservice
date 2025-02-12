package com.example.authenticationservice.service;

import com.example.authenticationservice.model.dto.AuthResponse;
import com.example.authenticationservice.model.dto.UserCreateDTO;
import com.example.authenticationservice.model.dto.UserLoginDTO;

public interface AuthService {

    AuthResponse register(UserCreateDTO userCreateDTO);
    AuthResponse login(UserLoginDTO userLoginDTO);
}
