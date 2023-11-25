package com.hackathon.hackathon.global.exception;


import com.hackathon.hackathon.global.exception.base.BaseException;

public class InvalidSignatureTokenException extends BaseException {
    public InvalidSignatureTokenException(){
        super(GlobalErrorCode.INVALID_SIGNATURE_TOKEN);
    }
}
