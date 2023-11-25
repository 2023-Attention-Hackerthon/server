package com.hackathon.hackathon.domain.user.usecase;

import com.hackathon.hackathon.domain.user.dto.response.IdTokenDto;
import com.hackathon.hackathon.domain.user.exception.NotSupportedLoginTypeException;
import com.hackathon.hackathon.global.annotation.UseCase;
import com.hackathon.hackathon.global.feign.oauth.kakao.KakaoProperties;
import com.hackathon.hackathon.global.feign.oauth.kakao.RequestKakaoTokenClient;
import com.hackathon.hackathon.global.feign.oauth.kakao.dto.KakaoTokenInfoDto;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RequestIdTokenUseCase {
    private final RequestKakaoTokenClient requestKakaoTokenClient;
    private final KakaoProperties kakaoProperties;
    public IdTokenDto execute(String loginType, String code){
        switch (loginType) {
            case "kakao":
                KakaoTokenInfoDto kakaoTokenInfoDto = requestKakaoTokenClient.getToken(
                        kakaoProperties.getClientId(),
                        kakaoProperties.getRedirectUrl(),
                        code,
                        kakaoProperties.getClientSecret());
                return IdTokenDto.of(kakaoTokenInfoDto.getIdToken());
            case "google":
                break;
        }
        throw new NotSupportedLoginTypeException();
    }
}