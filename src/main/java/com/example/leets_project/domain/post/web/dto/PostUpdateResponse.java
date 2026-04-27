package com.example.leets_project.domain.post.web.dto;

import com.example.leets_project.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostUpdateResponse {

    private Long postId;

    private String title;
    private String content;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostUpdateResponse from(Post post) {
        return PostUpdateResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .description(post.getDescription())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
