package com.hackathon.hackathon.domain.card.dto.request;

import com.hackathon.hackathon.domain.card.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CardCreateRequestDto {
    @NotBlank
    private String nickname;
    private String cardName;
    @NotBlank
    private String contact;
    @NotBlank
    private Gender gender;
    private int age;
    private String mbti;
    private List<String> adjective;
    private String introduce;

    private String instagramId;

    private String blogUrl;

    private String youtubeUrl;

    private String githubId;

    @Builder
    public CardCreateRequestDto(String nickname, String contact, Gender gender, String instagramId,
                                String blogUrl, String youtubeUrl, String githubId, String cardName, String introduce, String mbti, List<String> adjective, int age) {
        this.nickname = nickname;
        this.contact = contact;
        this.gender = gender;
        this.instagramId = instagramId;
        this.blogUrl = blogUrl;
        this.youtubeUrl = youtubeUrl;
        this.githubId = githubId;
        this.cardName = cardName;
        this.introduce = introduce;
        this.mbti = mbti;
        this.age = age;
        this.adjective = adjective;
    }
}
