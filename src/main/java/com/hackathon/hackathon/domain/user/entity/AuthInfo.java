package com.hackathon.hackathon.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import static com.hackathon.hackathon.domain.user.entity.AccountStatus.MEMBER;
import static com.hackathon.hackathon.domain.user.entity.Role.GUEST;


@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class AuthInfo {

    @Enumerated(EnumType.STRING)
    private LoginType loginType;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static AuthInfo authInfoForSignUp(LoginType loginType, String email) {
        return AuthInfo.builder()
                .loginType(loginType)
                .email(email)
                .accountStatus(MEMBER)
                .role(GUEST)
                .build();
    }
}