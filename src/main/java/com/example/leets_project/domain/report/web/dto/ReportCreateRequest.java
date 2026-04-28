package com.example.leets_project.domain.report.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportCreateRequest {

    @NotBlank(message = "신고 사유는 필수입니다.")
    @Size(max = 255, message = "신고 사유는 255자 이하여야 합니다.")
    private String reason;
}
