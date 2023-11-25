package com.hackathon.hackathon.domain.user.usecase;

import com.hackathon.hackathon.domain.user.adapter.UserAdaptor;
import com.hackathon.hackathon.domain.user.dto.response.AccountTokenDto;
import com.hackathon.hackathon.domain.user.entity.User;
import com.hackathon.hackathon.domain.user.service.RefreshTokenRedisService;
import com.hackathon.hackathon.domain.user.usecase.process.GenerateAccountTokenProcessor;
import com.hackathon.hackathon.global.annotation.UseCase;
import com.hackathon.hackathon.global.exception.InvalidTokenException;
import com.hackathon.hackathon.global.jwt.JwtProvider;
import com.hackathon.hackathon.global.jwt.dto.DecodedJwtToken;
import lombok.RequiredArgsConstructor;

import static com.hackathon.hackathon.global.constant.StaticValue.REFRESH_TOKEN;

@UseCase
@RequiredArgsConstructor
public class RefreshTokenUseCase {
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final GenerateAccountTokenProcessor generateAccountTokenProcessor;
    private final JwtProvider jwtProvider;
    private final UserAdaptor userAdaptor;
    public AccountTokenDto execute(String refreshToken){
        DecodedJwtToken decodedJwtToken = jwtProvider.decodeToken(refreshToken,REFRESH_TOKEN);
        User user = userAdaptor.findById(decodedJwtToken.getUserId());

        if(refreshTokenRedisService.checkToken(user.getId(), refreshToken)){
            return generateAccountTokenProcessor.refreshToken(user,refreshToken);
        }else throw new InvalidTokenException();
    }
}