package com.hackathon.hackathon.domain.user.exception;

import com.hackathon.hackathon.global.exception.GlobalErrorCode;
import com.hackathon.hackathon.global.exception.base.BaseException;

public class PublicKeyGenerationException extends BaseException {
    public PublicKeyGenerationException() {
        super(GlobalErrorCode.PUBKEY_GENERATION_FAILED);
    }
}