package com.hackathon.hackathon.global.exception;

import com.hackathon.hackathon.global.exception.base.BaseException;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException(){
        super(GlobalErrorCode.INVALID_TOKEN);
    }
}
