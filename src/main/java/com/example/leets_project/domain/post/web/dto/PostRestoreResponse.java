package com.example.leets_project.domain.post.web.dto;

import com.example.leets_project.domain.post.PostStatus;
import com.example.leets_project.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostRestoreResponse {

    private Long postId;
    private PostStatus status;

    public static PostRestoreResponse from(Post post) {
        return PostRestoreResponse.builder()
                .postId(post.getId())
                .status(post.getStatus())
                .build();
    }
}
