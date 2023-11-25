package com.hackathon.hackathon.domain.card.enums;

public enum CardSuccessMessage {
    CARD_CREATE_SUCCESS_MESSAGE("카드 생성에 성공했습니다."),
    CARD_GET_SUCCESS_MESSAGE("카드 조회에 성공했습니다."),
    CARD_DELETE_SUCCESS_MESSAGE("카드 삭제에 성공했습니다.");

    private final String message;
    private static final String SUCCESS_MESSAGE_PREFIX = "[SUCCESS] ";

    CardSuccessMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return SUCCESS_MESSAGE_PREFIX + this.message;
    }
}
