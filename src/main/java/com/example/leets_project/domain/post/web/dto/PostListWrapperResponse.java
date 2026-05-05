package com.example.leets_project.domain.post.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@Schema(description = "게시글 목록 조회 데이터 응답")
public class PostListWrapperResponse {

    @Schema(description = "게시글 목록 데이터")
    private List<PostListResponse> posts;

    public static PostListWrapperResponse of(Page<PostListResponse> page) {
        return PostListWrapperResponse.builder()
                .posts(page.getContent())
                .build();
    }
}
