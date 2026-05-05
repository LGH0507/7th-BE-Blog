package com.example.leets_project.domain.report.web.dto;

import com.example.leets_project.domain.report.ReportStatus;
import com.example.leets_project.domain.report.entity.Report;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "신고 처리 응답")
public class ReportResolveResponse {

    @Schema(description = "신고 ID", example = "1")
    private Long reportId;
    @Schema(description = "변경 후 신고 처리 상태", example = "RESOLVED")
    private ReportStatus status;

    public static ReportResolveResponse from(Report report) {
        return ReportResolveResponse.builder()
                .reportId(report.getId())
                .status(report.getStatus())
                .build();
    }
}
