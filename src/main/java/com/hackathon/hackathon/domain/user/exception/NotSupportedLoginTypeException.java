package com.hackathon.hackathon.domain.user.exception;

import com.hackathon.hackathon.global.exception.base.BaseException;

public class NotSupportedLoginTypeException extends BaseException {
    public NotSupportedLoginTypeException() {
        super(UserErrorCode.NOT_SUPPORTED_LOGIN_TYPE);
    }
}