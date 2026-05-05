package com.example.leets_project.domain.report.web.dto;

import com.example.leets_project.domain.report.ReportStatus;
import com.example.leets_project.domain.report.ReportTargetType;
import com.example.leets_project.domain.report.entity.Report;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "신고 생성 응답")
public class ReportCreateResponse {

    @Schema(description = "신고 ID", example = "1")
    private Long reportId;
    @Schema(description = "신고자 ID", example = "10")
    private Long reporterId;
    @Schema(description = "신고 대상 타입", example = "POST")
    private ReportTargetType targetType;
    @Schema(description = "신고 대상 ID", example = "100")
    private Long targetId;
    @Schema(description = "신고 사유", example = "부적절한 내용이 포함되어 있습니다.")
    private String reason;
    @Schema(description = "신고 처리 상태", example = "PENDING")
    private ReportStatus status;
    @Schema(description = "신고 접수 일시", example = "2024-01-01T12:00:00")
    private LocalDateTime createdAt;

    public static ReportCreateResponse from(Report report) {
        return ReportCreateResponse.builder()
                .reportId(report.getId())
                .reporterId(report.getReporter().getId())
                .targetType(report.getTargetType())
                .targetId(report.getTargetId())
                .reason(report.getReason())
                .status(report.getStatus())
                .createdAt(report.getCreatedAt())
                .build();
    }
}
