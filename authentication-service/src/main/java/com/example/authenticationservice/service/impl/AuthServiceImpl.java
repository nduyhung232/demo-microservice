package com.example.authenticationservice.service.impl;

import com.example.authenticationservice.config.exception.CustomValidationException;
import com.example.authenticationservice.config.redis.RedisService;
import com.example.authenticationservice.model.dto.AuthResponse;
import com.example.authenticationservice.model.dto.TokenType;
import com.example.authenticationservice.model.dto.UserCreateDTO;
import com.example.authenticationservice.model.dto.UserLoginDTO;
import com.example.authenticationservice.config.security.JwtService;
import com.example.authenticationservice.model.dto.Token;
import com.example.authenticationservice.model.entity.User;
import com.example.authenticationservice.model.mapper.UserMapper;
import com.example.authenticationservice.repository.UserRepository;
import com.example.authenticationservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(UserCreateDTO userCreateDTO) {
        // valid
        Optional<User> userOptional = userRepository.findByUsername(userCreateDTO.username());
        if (userOptional.isPresent()) {
            throw new CustomValidationException("username exist");
        }

        // mapping
        User user = userMapper.toEntity(userCreateDTO);
        user.setPassword(passwordEncoder.encode(userCreateDTO.password()));

        // save
        userRepository.save(user);
    }

    @Override
    public AuthResponse login(UserLoginDTO userLoginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.username(), userLoginDTO.password()));

        var user = userDetailsService.loadUserByUsername(userLoginDTO.username());

        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(user, accessToken);
        return new AuthResponse(accessToken, refreshToken);
    }

    private void saveUserToken(UserDetails user, String accessToken) {
        var token = new Token(accessToken, user.getUsername());
        redisService.saveToRedisForToken(accessToken, token);
    }
}
