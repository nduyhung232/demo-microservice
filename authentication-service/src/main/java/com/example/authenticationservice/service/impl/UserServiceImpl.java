package com.example.authenticationservice.service.impl;

import com.example.authenticationservice.config.security.JwtService;
import com.example.authenticationservice.model.mapper.UserMapper;
import com.example.authenticationservice.repository.UserRepository;
import com.example.authenticationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


}
