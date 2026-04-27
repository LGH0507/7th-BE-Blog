package com.example.leets_project.domain.comment.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDeleteResponse {

    private Long commentId;

    public static CommentDeleteResponse of(Long commentId) {
        return CommentDeleteResponse.builder()
                .commentId(commentId)
                .build();
    }
}
