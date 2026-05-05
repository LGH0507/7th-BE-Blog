package com.example.leets_project.domain.comment.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "댓글 삭제 응답")
public class CommentDeleteResponse {

    @Schema(description = "삭제할 댓글 ID", example = "10")
    private Long commentId;

    public static CommentDeleteResponse of(Long commentId) {
        return CommentDeleteResponse.builder()
                .commentId(commentId)
                .build();
    }
}
