package com.example.leets_project.domain.post.web.dto;

import com.example.leets_project.domain.post.PostStatus;
import com.example.leets_project.domain.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "게시글 복구 응답")
public class PostRestoreResponse {

    @Schema(description = "복원할 게시글 ID", example = "100")
    private Long postId;
    @Schema(description = "게시글 상태", example = "ACTIVE")
    private PostStatus status;

    public static PostRestoreResponse from(Post post) {
        return PostRestoreResponse.builder()
                .postId(post.getId())
                .status(post.getStatus())
                .build();
    }
}
