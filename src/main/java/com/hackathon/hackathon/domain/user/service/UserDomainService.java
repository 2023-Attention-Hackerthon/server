package com.hackathon.hackathon.domain.user.service;


import com.hackathon.hackathon.domain.user.adapter.UserAdaptor;
import com.hackathon.hackathon.domain.user.dto.request.SignUpRequestDto;
import com.hackathon.hackathon.domain.user.entity.AuthInfo;
import com.hackathon.hackathon.domain.user.entity.LoginType;
import com.hackathon.hackathon.domain.user.entity.User;
import com.hackathon.hackathon.domain.user.exception.EmailAlreadyRegistered;
import com.hackathon.hackathon.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDomainService {
    private final UserAdaptor userAdaptor;
    private final WalletService walletService;

    public User login(LoginType loginType, String email){
        User user = userAdaptor.findByEmail(email);
        if(user.getAuthInfo().getLoginType().equals(loginType)){
            return user;
        }else throw new EmailAlreadyRegistered();
    }

    @Transactional
    public User signUp(AuthInfo authInfo){
        User user = User.builder()
                .authInfo(authInfo)
                .build();
        System.out.println(user.getId());
        walletService.createInitialWallet(user);
        return userAdaptor.save(user);
    }

    @Transactional
    public User getAccount(SignUpRequestDto requestDto, User user) {
        User newUser = User.builder()
                .authInfo(user.getAuthInfo())
                .id(user.getId())
                .name(requestDto.getName()) // name 정보 설정
                .universityName(requestDto.getUniversityName()) // universityName 정보 설정
                .build();
        return userAdaptor.save(newUser);
    }

}