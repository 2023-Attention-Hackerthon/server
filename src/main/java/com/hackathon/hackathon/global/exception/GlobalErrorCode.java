package com.hackathon.hackathon.global.exception;

import com.hackathon.hackathon.global.exception.base.BaseErrorCode;
import com.hackathon.hackathon.global.exception.dto.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.hackathon.hackathon.global.constant.StaticValue.*;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode {
    /** example * */
    EXAMPLE_ERROR(BAD_REQUEST, "GLOBAL_400_0", "에러 예시 입니다."),

    /** Server 오류 * */
    HTTP_MESSAGE_NOT_READABLE(BAD_REQUEST, "GLOBAL_400_1", "잘못된 형식의 값을 입력했습니다."),
    NO_APPLE_CODE(BAD_REQUEST, "GLOBAL_400_2", "애플 코드가 필요합니다."),
    EMPTY_PARAM_VALUE(BAD_REQUEST, "GLOBAL_400_3", "빈 파람 값을 입력했습니다."),

    NOT_AUTHENTIATED_ERROR(UNAUTHORIZED,"SECURITY_401","사용자가 인증되지 않았습니다."),
    INVALID_TOKEN(UNAUTHORIZED,"TOKEN_401_1","토큰이 유효하지않습니다."),
    INVALID_SIGNATURE_TOKEN(UNAUTHORIZED,"TOKEN_401_2","토큰의 Signature가 일치하지 않습니다."),
    INCORRECT_ISSUER_TOKEN(UNAUTHORIZED,"TOKEN_401_3","토큰 발급처가 일치하지 않습니다."),
    EXPIRED_TOKEN(UNAUTHORIZED,"TOKEN_401_4","토큰이 만료되었습니다."),
    NOT_MATCHED_TOKEN_TYPE(UNAUTHORIZED,"TOKEN_401_5","토큰의 타입이 일치하지 않아 디코딩할 수 없습니다."),


    PUBKEY_GENERATION_FAILED(BAD_REQUEST,"PUBLIC_KEY_400_1","공개키를 생성하는데 실패했습니다.");

    private int status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status, code, reason);
    }
}
