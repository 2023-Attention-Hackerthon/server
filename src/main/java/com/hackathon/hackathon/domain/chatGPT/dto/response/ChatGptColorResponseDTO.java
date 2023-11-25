package com.hackathon.hackathon.domain.chatGPT.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatGptColorResponseDTO {

    private String color;

    @Builder
    public ChatGptColorResponseDTO(String color) {
        this.color = color;
    }
}
