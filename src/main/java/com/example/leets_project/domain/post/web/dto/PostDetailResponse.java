package com.example.leets_project.domain.post.web.dto;

import com.example.leets_project.domain.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "게시글 상세 조회 응답")
public class PostDetailResponse {

    @Schema(description = "상세 조회할 게시글 ID", example = "100")
    private Long postId;
    @Schema(description = "게시글의 작성자 ID", example = "1")
    private Long userId;
    @Schema(description = "게시글 제목", example = "첫 번째 게시글")
    private String title;
    @Schema(description = "게시글 내용", example = "댓글입니다.")
    private String content;
    @Schema(description = "게시글 설명", example = "설명입니다.")
    private String description;
    @Schema(description = "작성자 닉네임", example = "리츠화이팅")
    private String authorNickname;
    @Schema(description = "작성일시", example = "2024-01-01T00:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "수정일시", example = "2024-01-01T00:00:00")
    private LocalDateTime updatedAt;

    public static PostDetailResponse from(Post post) {
        return PostDetailResponse.builder()
                .postId(post.getId())
                .userId(post.getUser().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .description(post.getDescription())
                .authorNickname(post.getUser().getNickname())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
