package com.example.leets_project.domain.comment.web.dto;

import com.example.leets_project.domain.comment.CommentStatus;
import com.example.leets_project.domain.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "댓글 숨김처리 응답")
public class CommentHideResponse {

    @Schema(description = "숨김처리할 댓글 ID", example = "10")
    private Long commentId;
    @Schema(description = "댓글 상태", example = "ACTIVE")
    private CommentStatus status;

    public static CommentHideResponse from(Comment comment) {
        return CommentHideResponse.builder()
                .commentId(comment.getId())
                .status(comment.getStatus())
                .build();
    }
}
