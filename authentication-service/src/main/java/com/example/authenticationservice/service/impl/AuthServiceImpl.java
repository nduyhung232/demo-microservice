package com.example.authenticationservice.service.impl;

import com.example.authenticationservice.config.exception.CustomAuthenticationException;
import com.example.authenticationservice.config.exception.CustomValidationException;
import com.example.authenticationservice.config.redis.RedisService;
import com.example.authenticationservice.config.security.JwtService;
import com.example.authenticationservice.model.dto.AuthResponse;
import com.example.authenticationservice.model.dto.UserCreateDTO;
import com.example.authenticationservice.model.dto.UserLoginDTO;
import com.example.authenticationservice.model.entity.User;
import com.example.authenticationservice.model.mapper.UserMapper;
import com.example.authenticationservice.repository.UserRepository;
import com.example.authenticationservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
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
        User user = userRepository.findByUsername(userLoginDTO.username())
                .orElseThrow(() -> new CustomAuthenticationException("Invalid username or password"));

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        // save jwt and username to whitelist
        redisService.saveToRedisForJWT(accessToken, user.getUsername());
        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse refreshToken(String authHeader, String refreshHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ") ||
                refreshHeader == null || !refreshHeader.startsWith("Bearer ")) {
            throw new CustomValidationException("Invalid token");
        }
        final String accessToken = authHeader.substring(7);
        final String refreshToken = refreshHeader.substring(7);

        if (!jwtService.verifyToken(accessToken) && !jwtService.verifyToken(refreshToken)) {
            throw new CustomValidationException("Invalid token");
        }

        var username = jwtService.extractUsername(accessToken);
        var user = userRepository.findByUsername(username).orElseThrow();
        var isTokenInWhiteList = redisService.getUsernameFromJwtValue(accessToken) != null;
        if (isTokenInWhiteList) {
            var accessTokenNew = jwtService.generateAccessToken(user);
            var refreshTokenNew = jwtService.generateRefreshToken(user);
            redisService.removeJwt(accessToken);
            redisService.saveToRedisForJWT(accessTokenNew, username);
            return new AuthResponse(accessTokenNew, refreshTokenNew);
        } else {
            throw new CustomValidationException("Invalid token");
        }
    }
}
