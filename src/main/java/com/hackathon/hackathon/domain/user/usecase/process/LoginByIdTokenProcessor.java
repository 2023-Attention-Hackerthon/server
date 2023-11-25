package com.hackathon.hackathon.domain.user.usecase.process;


import com.hackathon.hackathon.domain.user.exception.NotSupportedLoginTypeException;
import com.hackathon.hackathon.global.annotation.Processor;
import com.hackathon.hackathon.global.exception.IncorrectIssuerTokenException;
import com.hackathon.hackathon.global.feign.oauth.kakao.KakaoProperties;
import com.hackathon.hackathon.global.feign.oauth.kakao.oidc.PublicKeyDto;
import com.hackathon.hackathon.global.feign.oauth.kakao.oidc.PublicKeysDto;
import com.hackathon.hackathon.global.jwt.JwtIdTokenProvider;
import com.hackathon.hackathon.global.jwt.dto.UserInfoFromIdToken;
import lombok.RequiredArgsConstructor;

@Processor
@RequiredArgsConstructor
public class LoginByIdTokenProcessor {
    private final PublicKeyProcessor publicKeyProcessor;
    private final JwtIdTokenProvider jwtIdTokenProvider;

    private final KakaoProperties kakaoProperties;

    public UserInfoFromIdToken execute(String loginType, String idToken){
        String kid = jwtIdTokenProvider.getKid(idToken);
        PublicKeysDto publicKeys = new PublicKeysDto();
        String iss = new String();
        String aud = new String();
        switch (loginType) {
            case "kakao":
                PublicKeysDto keys = publicKeyProcessor.getCachedKakaoPublicKeys();
                publicKeys = keys;
                iss = kakaoProperties.getIss();
                aud = kakaoProperties.getAppKey();
                System.out.println(iss);
                System.out.println(aud);
                break;
            case "google":

                break;
            default:
                throw new NotSupportedLoginTypeException();
        }
        PublicKeyDto key = publicKeys.getKeys().stream()
                .filter(k -> k.getKid().equals(kid))
                .findFirst()
                .orElseThrow(() -> new IncorrectIssuerTokenException());
        System.out.println(iss);
        System.out.println(aud);
        return jwtIdTokenProvider.getUserInfo(idToken, publicKeyProcessor.generatePublicKey(key), iss, aud);
    }

}