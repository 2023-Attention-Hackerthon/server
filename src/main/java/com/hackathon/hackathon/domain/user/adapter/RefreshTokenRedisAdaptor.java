package com.hackathon.hackathon.domain.user.adapter;

import com.hackathon.hackathon.domain.user.entity.RefreshToken;
import com.hackathon.hackathon.domain.user.exception.ExpiredRefreshTokenException;
import com.hackathon.hackathon.domain.user.repositroy.RefreshTokenRedisDao;
import com.hackathon.hackathon.global.annotation.Adaptor;
import com.hackathon.hackathon.global.annotation.Adaptor;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class RefreshTokenRedisAdaptor {
    private final RefreshTokenRedisDao refreshTokenRedisDao;

    public void save(Long id, RefreshToken refreshToken){
        refreshTokenRedisDao.save(id, refreshToken);
    }
    public RefreshToken findById(Long id){
        return refreshTokenRedisDao.findById(id)
                .orElseThrow(() -> new ExpiredRefreshTokenException());
    }
}