package com.example.leets_project.domain.comment.web.dto;

import com.example.leets_project.domain.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "댓글 수정 응답")
public class CommentUpdateResponse {

    @Schema(description = "댓글 ID", example = "10")
    private Long commentId;
    @Schema(description = "댓글 내용", example = "댓글입니다.")
    private String content;
    @Schema(description = "수정일시", example = "2024-01-01T00:00:00")
    private LocalDateTime updatedAt;

    public static CommentUpdateResponse from(Comment comment) {
        return CommentUpdateResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
