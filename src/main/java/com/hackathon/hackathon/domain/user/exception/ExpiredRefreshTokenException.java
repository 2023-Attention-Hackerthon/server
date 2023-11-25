package com.hackathon.hackathon.domain.user.exception;

import com.hackathon.hackathon.global.exception.base.BaseException;

public class ExpiredRefreshTokenException extends BaseException {
    public ExpiredRefreshTokenException() {
        super(RefreshTokenErrorCode.EXPIRED_REFRESH_TOKEN);
    }
}
