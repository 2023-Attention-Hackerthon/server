package com.hackathon.hackathon.domain.user.usecase;

import com.hackathon.hackathon.domain.user.adapter.UserAdaptor;
import com.hackathon.hackathon.domain.user.dto.response.AccountTokenDto;
import com.hackathon.hackathon.domain.user.entity.AuthInfo;
import com.hackathon.hackathon.domain.user.entity.LoginType;
import com.hackathon.hackathon.domain.user.entity.User;
import com.hackathon.hackathon.domain.user.exception.EmailAlreadyRegistered;
import com.hackathon.hackathon.domain.user.service.UserDomainService;
import com.hackathon.hackathon.domain.user.usecase.process.GenerateAccountTokenProcessor;
import com.hackathon.hackathon.domain.user.usecase.process.LoginByIdTokenProcessor;
import com.hackathon.hackathon.global.annotation.UseCase;
import com.hackathon.hackathon.global.jwt.dto.UserInfoFromIdToken;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SignUpUseCase {
    private final UserDomainService userDomainService;
    private final UserAdaptor userAdaptor;
    private final LoginByIdTokenProcessor loginByIdTokenProcessor;
    private final GenerateAccountTokenProcessor generateAccountTokenProcessor;

    public AccountTokenDto execute(String loginType,
                                   String idToken){
        UserInfoFromIdToken userInfo = loginByIdTokenProcessor.execute(loginType, idToken);
        System.out.println(userInfo.getEmail());
        System.out.println(userInfo.getNickname());
        if (!userAdaptor.checkEmail(userInfo.getEmail())){
//            Profile profile = Profile.profileForSignUp(userInfo.getNickname(),
//                    userInfo.getProfileImage());
//            System.out.println(profile);

            AuthInfo authInfo = AuthInfo.authInfoForSignUp((LoginType.fromValue(loginType)), userInfo.getEmail());
            System.out.println(authInfo);
            User user = userDomainService.signUp(authInfo);
            return generateAccountTokenProcessor.createToken(user);
        }else throw new EmailAlreadyRegistered();
    }
}