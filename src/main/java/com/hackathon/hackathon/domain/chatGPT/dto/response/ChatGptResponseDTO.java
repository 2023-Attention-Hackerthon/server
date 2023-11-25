package com.hackathon.hackathon.domain.chatGPT.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackathon.hackathon.domain.chatGPT.entity.ChatGptMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class ChatGptResponseDTO {
    private String id;
    private String object;
    private long created;
    private String model;
    @JsonProperty("system_fingerprint")
    private String systemFingerprint;
    private List<Choice> choices;
    private Usage usage;

    @Getter
    @Setter
    public static class Usage {
        @JsonProperty("prompt_tokens")
        private int promptTokens;
        @JsonProperty("completion_tokens")
        private int completionTokens;
        @JsonProperty("total_tokens")
        private int totalTokens;
    }

    @Getter
    @Setter
    public static class Choice {
        private int index;
        private ChatGptMessage message;
        @JsonProperty("finish_reason")
        private String finishReason;
    }
}
