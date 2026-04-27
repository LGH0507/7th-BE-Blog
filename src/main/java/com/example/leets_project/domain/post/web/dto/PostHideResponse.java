package com.example.leets_project.domain.post.web.dto;

import com.example.leets_project.domain.post.PostStatus;
import com.example.leets_project.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostHideResponse {

    private Long postId;
    private PostStatus status;

    public static PostHideResponse from(Post post) {
        return PostHideResponse.builder()
                .postId(post.getId())
                .status(post.getStatus())
                .build();
    }
}
