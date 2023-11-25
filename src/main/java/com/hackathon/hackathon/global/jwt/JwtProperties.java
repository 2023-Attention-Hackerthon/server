package com.hackathon.hackathon.global.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private final String secretKey;
    private final Long accessTokenExp;
    private final Long refreshTokenExp;

    public JwtProperties(String secretKey, Long accessTokenExp, Long refreshTokenExp) {
        this.secretKey = secretKey;
        this.accessTokenExp = accessTokenExp;
        this.refreshTokenExp = refreshTokenExp;
    }
}