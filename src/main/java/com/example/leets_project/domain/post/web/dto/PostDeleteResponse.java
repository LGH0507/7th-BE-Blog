package com.example.leets_project.domain.post.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "게시글 삭제 응답")
public class PostDeleteResponse {

    @Schema(description = "삭제할 게시글 ID", example = "10")
    private Long postId;

    public static PostDeleteResponse of(Long postId) {
        return PostDeleteResponse.builder()
                .postId(postId)
                .build();
    }
}
