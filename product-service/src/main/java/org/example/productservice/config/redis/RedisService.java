package org.example.productservice.config.redis;

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

    public Object getFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void saveToRedis(String key, Object value, long timeoutInSeconds) {
        redisTemplate.opsForValue().set(key, value, timeoutInSeconds, TimeUnit.MILLISECONDS);
    }

    public void deleteFromRedis(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Save jwt: username
     */
    public void saveToRedisForJWT(String jwt, String username) {
        saveToRedis(jwt, username, tokenExpiration);
    }

    /**
     * Remove jwt
     */
    public void removeJwt(String jwt) {
        deleteFromRedis(jwt);
    }

    /**
     * Remove username value from JWT value
     */
    public String getUsernameFromJwtValue(String jwt) {
        return (String) getFromRedis(jwt);
    }

}
