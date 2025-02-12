package com.example.authenticationservice.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    @Value("${application.security.jwt.expiration}")
    private Long tokenExpiration;

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveToRedisForToken(String key, Object value, long timeoutInSeconds) {
        redisTemplate.opsForValue().set(key, value, timeoutInSeconds, TimeUnit.SECONDS);
    }

    public void saveToRedisForToken(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, tokenExpiration, TimeUnit.SECONDS);
    }

    public Object getFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteFromRedis(String key) {
        redisTemplate.delete(key);
    }
}
