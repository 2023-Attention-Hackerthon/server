package com.hackathon.hackathon.domain.card.enums;

public enum HttpStatusCode {
    OK(200);

    private final int value;

    HttpStatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
