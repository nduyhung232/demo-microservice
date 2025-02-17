package com.example.authenticationservice.service.impl;

import com.example.authenticationservice.config.exception.CustomValidationException;
import com.example.authenticationservice.config.redis.RedisService;
import com.example.authenticationservice.model.dto.AuthResponse;
import com.example.authenticationservice.model.dto.UserCreateDTO;
import com.example.authenticationservice.model.dto.UserLoginDTO;
import com.example.authenticationservice.config.security.JwtService;
import com.example.authenticationservice.model.entity.User;
import com.example.authenticationservice.model.mapper.UserMapper;
import com.example.authenticationservice.repository.UserRepository;
import com.example.authenticationservice.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
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

        User user = (User) userDetailsService.loadUserByUsername(userLoginDTO.username());

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        // save jwt and username to whitelist
        redisService.saveToRedisForJWT(accessToken, user.getUsername());
        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshHeader = request.getHeader("Refresh-Token");
        if (authHeader == null || !authHeader.startsWith("Bearer ") ||
                refreshHeader == null || !refreshHeader.startsWith("Bearer ")) {
            return;
        }
        final String accessToken = authHeader.substring(7);
        final String refreshToken = refreshHeader.substring(7);

        var username = jwtService.extractUsername(accessToken);
        var user = userRepository.findByUsername(username).orElseThrow();
        var isTokenInWhiteList = redisService.getUsernameFromJwtValue(accessToken) != null;
        if (jwtService.isTokenValid(accessToken, user) && isTokenInWhiteList) {
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessTokenNew = jwtService.generateAccessToken(user);
                var refreshTokenNew = jwtService.generateRefreshToken(user);
                redisService.removeJwt(accessToken);
                redisService.saveToRedisForJWT(accessTokenNew, username);
                var authResponse = new AuthResponse(accessTokenNew, refreshTokenNew);
                try {
                    new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }
    }
}
