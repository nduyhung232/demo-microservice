package com.example.authenticationservice.controller;

import com.example.authenticationservice.model.dto.ApiResponse;
import com.example.authenticationservice.model.dto.AuthResponse;
import com.example.authenticationservice.model.dto.UserCreateDTO;
import com.example.authenticationservice.model.dto.UserLoginDTO;
import com.example.authenticationservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        ApiResponse apiResponse = new ApiResponse("User created successfully");
        authService.register(userCreateDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(authService.login(userLoginDTO));
    }
//
//    @PostMapping("/refresh")
//    public ResponseEntity<> refresh() {
//
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<ApiResponse> logout() {
//        ApiResponse apiResponse = new ApiResponse("Logged out successfully", null);
//        return ResponseEntity.ok(apiResponse);
//    }
}
