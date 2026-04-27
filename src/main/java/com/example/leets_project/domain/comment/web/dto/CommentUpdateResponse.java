package com.example.leets_project.domain.comment.web.dto;

import com.example.leets_project.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentUpdateResponse {

    private Long commentId;
    private String content;
    private LocalDateTime updatedAt;

    public static CommentUpdateResponse from(Comment comment) {
        return CommentUpdateResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
