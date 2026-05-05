package com.example.leets_project.domain.report.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "신고 생성 요청")
public class ReportCreateRequest {

    @NotBlank(message = "신고 사유는 필수입니다.")
    @Size(max = 255, message = "신고 사유는 255자 이하여야 합니다.")
    @Schema(description = "신고 사유", example = "부적절한 내용이 포함되어 있습니다.")
    private String reason;
}
