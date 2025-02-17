package com.example.authenticationservice.service;

import com.example.authenticationservice.model.dto.AuthResponse;
import com.example.authenticationservice.model.dto.UserCreateDTO;
import com.example.authenticationservice.model.dto.UserLoginDTO;

public interface AuthService {

    void register(UserCreateDTO userCreateDTO);
    AuthResponse login(UserLoginDTO userLoginDTO);

    AuthResponse refreshToken(String authorization, String refreshToken);
}
