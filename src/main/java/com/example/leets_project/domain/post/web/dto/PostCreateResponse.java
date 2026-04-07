package com.example.leets_project.domain.post.web.dto;

import com.example.leets_project.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostCreateResponse {

    private Long postId;
    private String title;
    private String authorNickname;
    private LocalDateTime createdAt;

    public static PostCreateResponse from(Post post) {
        return PostCreateResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .authorNickname(post.getUser().getNickname())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
