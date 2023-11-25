package com.hackathon.hackathon.domain.user.exception;

import com.hackathon.hackathon.global.exception.base.BaseErrorCode;
import com.hackathon.hackathon.global.exception.dto.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.hackathon.hackathon.global.constant.StaticValue.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum RefreshTokenErrorCode implements BaseErrorCode {
    EXPIRED_REFRESH_TOKEN(UNAUTHORIZED, "REFRESH_TOKEN_401", "해당 리프레쉬토큰은 만료되었습니다.");


    private final int httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return null;
    }
}