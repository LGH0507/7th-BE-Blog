package com.example.leets_project.domain.comment.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "댓글 수정 요청")
public class CommentUpdateRequest {

    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(max = 255, message = "댓글은 255자 이하여야 합니다.")
    @Schema(description = "댓글 내용", example = "댓글입니다.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;
}
