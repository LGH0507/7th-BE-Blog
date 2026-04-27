package com.example.leets_project.domain.post.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDeleteResponse {

    private Long postId;

    public static PostDeleteResponse of(Long postId) {
        return PostDeleteResponse.builder()
                .postId(postId)
                .build();
    }
}
