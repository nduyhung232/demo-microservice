package com.example.authenticationservice.controller;

import com.example.authenticationservice.model.dto.AuthResponse;
import com.example.authenticationservice.model.dto.MessageResponse;
import com.example.authenticationservice.model.dto.UserCreateDTO;
import com.example.authenticationservice.model.dto.UserLoginDTO;
import com.example.authenticationservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        MessageResponse messageResponse = new MessageResponse("User created successfully");
        authService.register(userCreateDTO);
        return ResponseEntity.ok(messageResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(authService.login(userLoginDTO));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("Authorization") String authorization,
                                                     @RequestHeader("Refresh-Token") String refreshToken
    ) {
        return ResponseEntity.ok(authService.refreshToken(authorization, refreshToken));
    }
}
