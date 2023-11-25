package com.hackathon.hackathon.domain.card.dto.response;

import com.hackathon.hackathon.domain.card.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CardCreateResponseDto {

    @NotNull
    private Long id;

    @NotNull
    private String nickname;
    private String cardName;

    private String contact;

    @Enumerated(EnumType.STRING)
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
    public CardCreateResponseDto(Long id, String nickname, String contact, Gender gender, String instagramId,
                                 String blogUrl, String youtubeUrl, String githubId, String cardName, String introduce, String mbti, List<String> adjective, int age) {
        this.id = id;
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
