package com.hackathon.hackathon.global.exception.handler;

import com.hackathon.hackathon.global.exception.dto.ErrorReason;
import com.hackathon.hackathon.global.response.ErrorResponse;
import io.micrometer.common.lang.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatus statusCode,
            WebRequest request) {
        log.error("HandleInternalException", ex);
        final HttpStatus status = (HttpStatus) statusCode;
        final ErrorReason errorReason =
                ErrorReason.of(status.value(), status.name(), ex.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.from(errorReason);
        return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
    }
}
