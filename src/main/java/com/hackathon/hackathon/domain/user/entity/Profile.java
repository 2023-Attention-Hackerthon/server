package com.hackathon.hackathon.domain.user.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Profile {

    private String name;
    // 010-0000-0000 형태로
    // private String phoneNumber;
    private String profileImage;

    public static Profile profileForSignUp(String name, String profileImage){
        return Profile.builder()
                .name(name)
                .profileImage(profileImage)
                .build();
    }
}