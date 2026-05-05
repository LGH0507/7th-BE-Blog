package com.example.leets_project.domain.comment.web.dto;

import com.example.leets_project.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentCreateResponse {

    private Long commentId;
    private Long postId;
    private String authorNickname;
    private String content;
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
