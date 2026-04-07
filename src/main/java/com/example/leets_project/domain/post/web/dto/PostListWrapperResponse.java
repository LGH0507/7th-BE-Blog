package com.example.leets_project.domain.post.web.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class PostListWrapperResponse {

    private List<PostListResponse> posts;

    public static PostListWrapperResponse of(Page<PostListResponse> page) {
        return PostListWrapperResponse.builder()
                .posts(page.getContent())
                .build();
    }
}
