package com.hackathon.hackathon.global.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {EnumValidator.class})
public @interface Enum {
    String message() default "Enum에 없는 값입니다."; // 오류 시 출력 메세지

    Class<?>[] groups() default { }; // 상황별 validation 제어

    Class<? extends Payload>[] payload() default { };

    Class<? extends java.lang.Enum<?>> enumClass(); // 제약할 enum class 지정

    boolean ignoreCase() default false;
}
