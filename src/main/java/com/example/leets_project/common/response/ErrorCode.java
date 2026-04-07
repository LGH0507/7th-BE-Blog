package com.example.leets_project.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // COMMON
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "COMMON_4000", "유효하지 않은 값입니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "COMMON_4001", "잘못된 입력입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_4002", "허용되지 않은 요청입니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_5000", "서버 오류입니다."),

    // AUTH
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_4000", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH_4001", "접근 권한이 없습니다."),

    // USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_4040", "사용자를 찾을 수 없습니다."),

    // POST
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_4040", "게시글을 찾을 수 없습니다."),
    POST_INVALID(HttpStatus.BAD_REQUEST, "POST_4001", "게시글 입력값이 올바르지 않습니다."),
    POST_FORBIDDEN(HttpStatus.FORBIDDEN, "POST_4003", "작성자만 수정/삭제할 수 있습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
