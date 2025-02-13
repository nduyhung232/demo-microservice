package com.example.authenticationservice.model.dto;

public record Token(
        String token,
        TokenType tokenType,
        String userId) {

    public Token(String token, String userId) {
        this(token, TokenType.BEARER, userId);
    }
}
