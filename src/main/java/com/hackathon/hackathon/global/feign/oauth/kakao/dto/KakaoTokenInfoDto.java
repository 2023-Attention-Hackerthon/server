package com.hackathon.hackathon.global.feign.oauth.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoTokenInfoDto {
    private String accessToken;
    private String refreshToken;
    private String idToken;
}