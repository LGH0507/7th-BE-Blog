package com.example.leets_project.domain.post.web.dto;

import com.example.leets_project.domain.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "게시글 수정 응답")
public class PostUpdateResponse {

    @Schema(description = "수정할 게시글 ID", example = "10")
    private Long postId;

    @Schema(description = "수정할 게시글 제목", example = "수정된 제목입니다.")
    private String title;
    @Schema(description = "수정할 게시글 내용", example = "수정된 내용입니다.")
    private String content;
    @Schema(description = "수정할 게시글 설명", example = "수정된 설명입니다.")
    private String description;

    @Schema(description = "작성일시", example = "2024-01-01T00:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "수정일시", example = "2024-01-01T00:00:00")
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
