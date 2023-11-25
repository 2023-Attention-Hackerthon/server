package com.hackathon.hackathon.domain.chatGPT.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class QuestionRequestDTO implements Serializable {
    private String question;
}
