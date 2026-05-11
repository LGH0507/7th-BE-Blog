package com.example.leets_project.domain.post.web.dto;

import com.example.leets_project.domain.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "게시글 생성 응답")
public class PostCreateResponse {

    @Schema(description = "게시글 ID", example = "10")
    private Long postId;
    @Schema(description = "게시글 제목", example = "첫 번째 게시글")
    private String title;
    @Schema(description = "작성자 닉네임", example = "리츠화이팅")
    private String authorNickname;
    @Schema(description = "작성일시", example = "2024-01-01T00:00:00")
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
