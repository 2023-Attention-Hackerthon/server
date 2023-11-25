package com.hackathon.hackathon.domain.card.dto.response;

import com.hackathon.hackathon.domain.card.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardCreateResponseDto {

    @NotNull
    private Long id;

    @NotNull
    private String nickname;

    private String contact;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String instagramId;

    private String blogUrl;

    private String youtubeUrl;

    private String githubId;

    @Builder
    public CardCreateResponseDto(Long id, String nickname, String contact, Gender gender, String instagramId,
                                 String blogUrl, String youtubeUrl, String githubId) {
        this.id = id;
        this.nickname = nickname;
        this.contact = contact;
        this.gender = gender;
        this.instagramId = instagramId;
        this.blogUrl = blogUrl;
        this.youtubeUrl = youtubeUrl;
        this.githubId = githubId;
    }
}
