package com.example.leets_project.common.exception;

import com.example.leets_project.common.response.ErrorCode;
import com.example.leets_project.common.response.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 커스텀 예외
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<GlobalResponse> handleGeneralException(GeneralException e) {
        log.warn("GeneralException: [{}] {}", e.getErrorCode().getCode(), e.getErrorCode().getMessage());
        return GlobalResponse.onFailure(e.getErrorCode());
    }
    // Validation 에러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        String errorDetail = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> "[" + error.getField() + "]: " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("Validation Failed: {}", errorDetail);
        return GlobalResponse.onFailure(ErrorCode.VALIDATION_FAILED, errorDetail);
    }

    // JSON 처리 에러
    @ExceptionHandler({com.fasterxml.jackson.core.JsonParseException.class,
            com.fasterxml.jackson.databind.JsonMappingException.class})
    public ResponseEntity<GlobalResponse> handleJsonException(Exception e) {
        log.error("JSON 처리 오류: {}", e.getMessage());

        return GlobalResponse.onFailure(ErrorCode.VALIDATION_FAILED, "잘못된 JSON 형식입니다.");
    }

    // X-USER-ID 헤더 누락
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<GlobalResponse> handleMissingHeader(MissingRequestHeaderException e) {
        log.warn("필수 헤더 누락 - {}", e.getHeaderName());

        return GlobalResponse.onFailure(ErrorCode.UNAUTHORIZED, "필수 헤더가 누락되었습니다.");
    }

    // 모든 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse> handleGenericException(Exception e) {
        log.error("Unexpected Error", e);

        return GlobalResponse.onFailure(ErrorCode.INTERNAL_ERROR);
    }
}
