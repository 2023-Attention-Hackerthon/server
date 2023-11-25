package com.hackathon.hackathon.domain.user.usecase;

import com.hackathon.hackathon.domain.user.adapter.UserAdaptor;
import com.hackathon.hackathon.domain.user.dto.response.AccountTokenDto;
import com.hackathon.hackathon.domain.user.entity.LoginType;
import com.hackathon.hackathon.domain.user.entity.User;
import com.hackathon.hackathon.domain.user.service.UserDomainService;
import com.hackathon.hackathon.domain.user.usecase.process.GenerateAccountTokenProcessor;
import com.hackathon.hackathon.domain.user.usecase.process.LoginByIdTokenProcessor;
import com.hackathon.hackathon.global.annotation.UseCase;
import com.hackathon.hackathon.global.jwt.dto.UserInfoFromIdToken;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class LoginUseCase {
    private final LoginByIdTokenProcessor loginByIdTokenProcessor;
    private final UserAdaptor userAdaptor;
    private final UserDomainService userDomainService;
    private final GenerateAccountTokenProcessor generateAccountTokenProcessor;

    public AccountTokenDto execute(String loginType, String idToken){
        UserInfoFromIdToken userInfo = loginByIdTokenProcessor.execute(loginType, idToken);
        if (!userAdaptor.checkEmail(userInfo.getEmail())){
            return AccountTokenDto.notRegistered();
        }

        User user = userDomainService.login(LoginType.fromValue(loginType), userInfo.getEmail());
        return generateAccountTokenProcessor.createToken(user);
    }
}
