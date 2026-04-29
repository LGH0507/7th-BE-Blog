package com.example.leets_project.domain.comment.web.controller;

import com.example.leets_project.common.response.GlobalResponse;
import com.example.leets_project.common.response.SuccessCode;
import com.example.leets_project.domain.comment.service.CommentService;
import com.example.leets_project.domain.comment.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "COMMENT API", description = "댓글 생성, 삭제, 수정, 목록 조회 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @Operation(summary = "댓글 생성", description = "특정 게시물에 댓글을 작성합니다.")
    @PostMapping
    public ResponseEntity<GlobalResponse> createComment(@PathVariable Long postId,
                                                        @RequestHeader("X-USER-ID") Long currentUserId,
                                                        @RequestBody @Valid CommentCreateRequest request) {

        CommentCreateResponse response = commentService.createComment(postId, currentUserId, request);

        return GlobalResponse.onSuccess(SuccessCode.COMMENT_CREATE, response);
    }
    // 댓글 수정
    @Operation(summary = "댓글 수정", description = "특정 댓글을 수정합니다.")
    @PutMapping("/{commentId}")
    public ResponseEntity<GlobalResponse> udateComment(@PathVariable Long postId,
                                                       @PathVariable Long commentId,
                                                       @RequestHeader("X-USER-ID") Long currentUserId,
                                                       @RequestBody @Valid CommentUpdateRequest request){

        CommentUpdateResponse response = commentService.updateComment(postId, commentId, currentUserId, request);

        return GlobalResponse.onSuccess(SuccessCode.COMMENT_UPDATE, response);
    }
    // 댓글 삭제
    @Operation(summary = "댓글 삭제", description = "특정 댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<GlobalResponse> deleteComment(@PathVariable Long postId,
                                                        @PathVariable Long commentId,
                                                        @RequestHeader("X-USER-ID") Long currentUserId){

        CommentDeleteResponse response = commentService.deleteComment(postId, commentId, currentUserId);

        return GlobalResponse.onSuccess(SuccessCode.COMMENT_DELETE, response);
    }
    // 댓글 숨김
    @Operation(summary = "댓글 숨김", description = "특정 댓글을 숨김 처리합니다.")
    @PatchMapping("/{commentId}/hide")
    public ResponseEntity<GlobalResponse> hideComment(@PathVariable Long postId,
                                                      @PathVariable Long commentId,
                                                      @RequestHeader("X-USER-ID") Long currentUserId) {

        CommentHideResponse response = commentService.hideComment(postId, commentId, currentUserId);

        return GlobalResponse.onSuccess(SuccessCode.COMMENT_HIDE, response);
    }
    // 댓글 복구
    @Operation(summary = "댓글 복구", description = "HIDDEN 상태의 댓글을 ACTIVE로 복구합니다.")
    @PatchMapping("/{commentId}/restore")
    public ResponseEntity<GlobalResponse> restoreComment(@PathVariable Long postId,
                                                         @PathVariable Long commentId,
                                                         @RequestHeader("X-USER-ID") Long currentUserId) {

        CommentRestoreResponse response = commentService.restoreComment(postId, commentId, currentUserId);

        return GlobalResponse.onSuccess(SuccessCode.COMMENT_RESTORE, response);
    }
    // 댓글 목록 조회(특정 게시물)
    @Operation(summary = "댓글 목록 조회", description = "특정 게시물의 댓글 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<GlobalResponse> getComments(@PathVariable Long postId,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {

        Page<CommentListResponse> response = commentService.getComments(postId, page, size);

        return GlobalResponse.onSuccess(SuccessCode.COMMENT_LIST, response);
    }
}
