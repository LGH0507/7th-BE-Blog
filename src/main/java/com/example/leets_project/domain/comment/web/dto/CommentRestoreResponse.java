package com.example.leets_project.domain.comment.web.dto;

import com.example.leets_project.domain.comment.CommentStatus;
import com.example.leets_project.domain.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "댓글 복원 응답")
public class CommentRestoreResponse {

    @Schema(description = "복원할 댓글 ID", example = "10")
    private Long commentId;
    @Schema(description = "댓글 상태", example = "ACTIVE")
    private CommentStatus status;

    public static CommentRestoreResponse from(Comment comment) {
        return CommentRestoreResponse.builder()
                .commentId(comment.getId())
                .status(comment.getStatus())
                .build();
    }
}
