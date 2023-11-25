package com.hackathon.hackathon.domain.user.service;

import com.hackathon.hackathon.domain.user.adapter.RefreshTokenRedisAdaptor;
import com.hackathon.hackathon.domain.user.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenRedisService {
    private final RefreshTokenRedisAdaptor refreshTokenRedisAdaptor;


    public void save(Long id, String token){
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(id)
                .token(token)
                .build();
        refreshTokenRedisAdaptor.save(id, refreshToken);
    }
    public Boolean checkToken(Long id, String token){
        RefreshToken refreshToken = refreshTokenRedisAdaptor.findById(id);
        if(token.equals(refreshToken.getToken())) return true;
        else return false;
    }
}
