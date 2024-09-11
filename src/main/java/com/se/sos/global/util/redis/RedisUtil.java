package com.se.sos.global.util.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public void save(String key, Object value, long expiration) {
        redisTemplate.opsForValue().set(key, value, expiration, TimeUnit.MILLISECONDS);
    }

    public void saveExpire(String key, Long seconds) {
        redisTemplate.expire(key, Duration.ofSeconds(seconds));
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean delete(String userId) {
        if (containsKey(userId)) {
            redisTemplate.delete(userId);
            return true;
        } else {
            return false;
        }
    }

    public void blacklistToken(String key, long remainingTime) {
        redisTemplate.opsForValue().set(key, "blacklisted", remainingTime, TimeUnit.MILLISECONDS);
    }

    public boolean containsKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
    public boolean isTokenBlacklisted(String key) {
        return containsKey(key);
    }

}