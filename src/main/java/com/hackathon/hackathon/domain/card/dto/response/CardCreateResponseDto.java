package com.hackathon.hackathon.domain.card.dto.response;

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
    private String name;

    @Builder
    public CardCreateResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
