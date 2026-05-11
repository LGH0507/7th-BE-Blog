package com.example.leets_project.domain.post.web.dto;

import com.example.leets_project.domain.post.PostStatus;
import com.example.leets_project.domain.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "게시글 숨김처리 응답")
public class PostHideResponse {
    @Schema(description = "숨김처리 할 게시글 ID", example = "10")
    private Long postId;
    @Schema(description = "게시글 상태", example = "ACTIVE")
    private PostStatus status;

    public static PostHideResponse from(Post post) {
        return PostHideResponse.builder()
                .postId(post.getId())
                .status(post.getStatus())
                .build();
    }
}
