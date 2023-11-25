package com.hackathon.hackathon.domain.user.exception;

import com.hackathon.hackathon.global.exception.base.BaseErrorCode;
import com.hackathon.hackathon.global.exception.dto.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.hackathon.hackathon.global.constant.StaticValue.BAD_REQUEST;
import static com.hackathon.hackathon.global.constant.StaticValue.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    NOT_SUPPORTED_LOGIN_TYPE(BAD_REQUEST, "LOGIN_TYPE_400", "해당 방식은 지원하지않는 로그인 방식입니다."),
    USER_NOT_FOUND(NOT_FOUND, "USER_404", "유저를 찾을 수 없습니다."),
    EMAIL_ALREADY_REGISTERED(NOT_FOUND, "USER_409", "같은 이메일로 회원가입된 계정이 있습니다.");


    private final int httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReason getErrorReason() {
        return null;
    }
}