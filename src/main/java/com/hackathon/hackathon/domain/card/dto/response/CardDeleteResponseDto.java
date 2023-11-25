package com.hackathon.hackathon.domain.card.dto.response;

import com.hackathon.hackathon.domain.card.enums.CardStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardDeleteResponseDto {
    @NotNull
    private Long id;

    @NotNull
    private CardStatus cardStatus;

    @Builder
    public CardDeleteResponseDto(Long id, CardStatus cardStatus) {
        this.id = id;
        this.cardStatus = cardStatus;
    }
}
