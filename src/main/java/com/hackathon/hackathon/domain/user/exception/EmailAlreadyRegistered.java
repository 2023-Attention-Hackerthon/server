package com.hackathon.hackathon.domain.user.exception;

import com.hackathon.hackathon.global.exception.base.BaseException;

public class EmailAlreadyRegistered extends BaseException {
    public EmailAlreadyRegistered() {
        super(UserErrorCode.EMAIL_ALREADY_REGISTERED);
    }
}