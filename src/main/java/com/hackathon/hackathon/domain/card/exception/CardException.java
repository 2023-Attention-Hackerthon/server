package com.hackathon.hackathon.domain.card.exception;

import com.hackathon.hackathon.domain.card.exception.constants.CardErrorMessage;

public class CardException extends IllegalArgumentException {
    private CardException(final CardErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }

    public static CardException of(final CardErrorMessage message) {
        return new CardException(message);
    }
}
