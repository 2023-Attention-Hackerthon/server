package com.hackathon.hackathon.domain.user.exception;

import com.hackathon.hackathon.global.exception.base.BaseException;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
