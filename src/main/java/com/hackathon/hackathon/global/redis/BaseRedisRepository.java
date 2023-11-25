package com.hackathon.hackathon.global.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

public abstract class BaseRedisRepository<T> {
    protected RedisTemplate<String, T> redisTemplate;
    protected String prefix;

    public void save(Long id, T t) {
        redisTemplate.opsForValue().set(generateKeyfromId(id), t);
    }

    public Optional<T> findById(Long id) {
        return Optional.of(redisTemplate.opsForValue().get(generateKeyfromId(id)));
    }
    public Boolean delete(Long id) {
        return redisTemplate.delete(generateKeyfromId(id));
    }

    public String generateKeyfromId(Long id){
        return prefix + id.toString();
    }

}