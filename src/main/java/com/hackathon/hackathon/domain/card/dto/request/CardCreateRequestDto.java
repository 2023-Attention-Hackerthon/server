package com.hackathon.hackathon.domain.card.dto.request;

import com.hackathon.hackathon.domain.card.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardCreateRequestDto {
    @NotBlank
    private String nickname;
    @NotBlank
    private String contact;
    @NotBlank
    private Gender gender;

    private String instagramId;

    private String blogUrl;

    private String youtubeUrl;

    private String githubId;

    @Builder
    public CardCreateRequestDto(String nickname, String contact, Gender gender) {
        this.nickname = nickname;
        this.contact = contact;
        this.gender = gender;
    }
}
