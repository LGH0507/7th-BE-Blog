package com.example.leets_project.domain.post.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "게시글 수정 요청")
public class PostUpdateRequest {

    @NotBlank(message = "제목은 필수입니다.")
    @Schema(description = "게시글 제목", example = "첫 번째 게시글", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    @Schema(description = "게시글 내용", example = "내용입니다.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "게시글 설명", example = "설명입니다.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
}
