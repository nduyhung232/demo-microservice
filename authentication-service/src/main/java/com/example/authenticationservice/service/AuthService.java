package com.example.authenticationservice.service;

import com.example.authenticationservice.model.dto.AuthResponse;
import com.example.authenticationservice.model.dto.UserCreateDTO;
import com.example.authenticationservice.model.dto.UserLoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {

    void register(UserCreateDTO userCreateDTO);
    AuthResponse login(UserLoginDTO userLoginDTO);

    void refreshToken(HttpServletRequest request, HttpServletResponse response);
}
