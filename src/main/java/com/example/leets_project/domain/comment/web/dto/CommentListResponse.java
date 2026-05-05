package com.example.leets_project.domain.comment.web.dto;

import com.example.leets_project.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentListResponse {

    private Long commentId;
    private Long userId;
    private String authorNickname;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentListResponse from(Comment comment) {
        return CommentListResponse.builder()
                .commentId(comment.getId())
                .userId(comment.getUser().getId())
                .authorNickname(comment.getUser().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
