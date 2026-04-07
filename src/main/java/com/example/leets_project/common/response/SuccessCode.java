package com.example.leets_project.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    // POST
    POST_LIST(HttpStatus.OK, "POST_2000", "게시글 목록 조회 성공"),
    POST_DETAIL(HttpStatus.OK, "POST_2001", "게시글 조회 성공"),
    POST_CREATE(HttpStatus.CREATED, "POST_2010", "게시글 생성 성공"),
    POST_UPDATE(HttpStatus.OK, "POST_2002", "게시글 수정 성공"),
    POST_DELETE(HttpStatus.OK, "POST_2003", "게시글 삭제 성공");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
