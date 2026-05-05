package com.example.leets_project.domain.comment.web.dto;

import com.example.leets_project.domain.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "댓글 생성 응답")
public class CommentCreateResponse {

    @Schema(description = "댓글 ID", example = "100")
    private Long commentId;
    @Schema(description = "게시글 ID", example = "10")
    private Long postId;
    @Schema(description = "작성자 닉네임", example = "리츠화이팅")
    private String authorNickname;
    @Schema(description = "댓글 내용", example = "댓글입니다.")
    private String content;
    @Schema(description = "작성일시", example = "2024-01-01T00:00:00")
    private LocalDateTime createdAt;

    public static CommentCreateResponse from(Comment comment) {
        return CommentCreateResponse.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .authorNickname(comment.getUser().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

}
