package com.example.authenticationservice.config.security;

import com.example.authenticationservice.config.redis.RedisService;
import com.example.authenticationservice.model.dto.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class LogoutService implements LogoutHandler {

    private final RedisService redisService;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = (Token) redisService.getFromRedis(jwt);

        if (storedToken != null) {
            redisService.deleteFromRedis(jwt);
            SecurityContextHolder.clearContext();
        } else {
            log.warn("Token not exist");
        }
    }
}
