package com.hackathon.hackathon.domain.user.usecase.process;

import com.hackathon.hackathon.global.annotation.Processor;
import com.hackathon.hackathon.global.feign.oauth.kakao.RequestKakaoTokenClient;
import com.hackathon.hackathon.global.feign.oauth.kakao.oidc.PublicKeyDto;
import com.hackathon.hackathon.global.feign.oauth.kakao.oidc.PublicKeysDto;
import com.hackathon.hackathon.global.utils.PublicKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

import java.security.interfaces.RSAPublicKey;

import static com.hackathon.hackathon.global.constant.StaticValue.KAKAO_PUBLIC_KEYS;

@Processor
@RequiredArgsConstructor
public class PublicKeyProcessor {
    private final RequestKakaoTokenClient requestKakaoTokenClient;

    @Cacheable(value = KAKAO_PUBLIC_KEYS, cacheManager = "redisCacheManager")
    public PublicKeysDto getCachedKakaoPublicKeys(){
        return requestKakaoTokenClient.getPublicKeys();
    }

    public RSAPublicKey generatePublicKey(PublicKeyDto key){
        return PublicKeyGenerator.excute(key.getKty(), key.getN(), key.getE());
    }
}