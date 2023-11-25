package com.hackathon.hackathon.domain.user.repositroy;


import com.hackathon.hackathon.domain.user.entity.RefreshToken;
import com.hackathon.hackathon.global.RedisRepository;
import com.hackathon.hackathon.global.redis.BaseRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import static com.hackathon.hackathon.global.constant.StaticValue.REFRESH_TOKEN_KEY;

@RedisRepository
public class RefreshTokenRedisDao extends BaseRedisRepository<RefreshToken> {
    @Autowired
    public RefreshTokenRedisDao(RedisTemplate redisTemplate) {
        this.prefix = REFRESH_TOKEN_KEY + ":";
        this.redisTemplate = redisTemplate;
    }
}