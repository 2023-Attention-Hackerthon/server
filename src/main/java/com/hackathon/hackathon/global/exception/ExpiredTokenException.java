package com.hackathon.hackathon.global.exception;

import com.hackathon.hackathon.global.exception.base.BaseException;

public class ExpiredTokenException extends BaseException {
    public ExpiredTokenException(){
        super(GlobalErrorCode.EXPIRED_TOKEN);
    }
}