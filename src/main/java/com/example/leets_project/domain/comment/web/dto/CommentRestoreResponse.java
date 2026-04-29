package com.example.leets_project.domain.comment.web.dto;

import com.example.leets_project.domain.comment.CommentStatus;
import com.example.leets_project.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentRestoreResponse {

    private Long commentId;
    private CommentStatus status;

    public static CommentRestoreResponse from(Comment comment) {
        return CommentRestoreResponse.builder()
                .commentId(comment.getId())
                .status(comment.getStatus())
                .build();
    }
}
