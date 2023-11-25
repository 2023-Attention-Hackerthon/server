package com.hackathon.hackathon.global.exception;


import com.hackathon.hackathon.global.exception.base.BaseException;

public class NotMatchedTokenTypeException extends BaseException {

    public NotMatchedTokenTypeException() {
        super(GlobalErrorCode.NOT_MATCHED_TOKEN_TYPE);
    }
}