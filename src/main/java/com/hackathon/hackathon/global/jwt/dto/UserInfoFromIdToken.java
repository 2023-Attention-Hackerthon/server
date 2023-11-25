package com.hackathon.hackathon.global.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserInfoFromIdToken {
    private String nickname;
    private String email;
    private String profileImage;
}