package com.hackathon.hackathon.domain.card.exception.constants;

public enum CardErrorMessage {
    ACCESS_DENIED_OTHER_CARD("본인의 카드가 아니면 접근할 수 없습니다.");

    private final String message;
    private static final String ERROR_MESSAGE_PREFIX = "[ERROR] ";

    CardErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_MESSAGE_PREFIX + this.message;
    }
    }
