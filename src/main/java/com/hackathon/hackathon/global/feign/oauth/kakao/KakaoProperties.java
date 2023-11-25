package com.hackathon.hackathon.global.feign.oauth.kakao;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "kakao")
public class KakaoProperties {
    private final String iss;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;
    private final String appKey;

    public KakaoProperties(String iss, String clientId, String clientSecret, String redirectUrl, String appKey) {
        this.iss = iss;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.appKey = appKey;
    }
}