package com.hackathon.hackathon.global.exception;


import com.hackathon.hackathon.global.exception.base.BaseException;

public class IncorrectIssuerTokenException extends BaseException {
    public IncorrectIssuerTokenException() {
        super(GlobalErrorCode.INCORRECT_ISSUER_TOKEN);
    }
}