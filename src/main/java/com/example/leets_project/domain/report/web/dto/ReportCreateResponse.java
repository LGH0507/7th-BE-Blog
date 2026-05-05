package com.example.leets_project.domain.report.web.dto;

import com.example.leets_project.domain.report.ReportStatus;
import com.example.leets_project.domain.report.ReportTargetType;
import com.example.leets_project.domain.report.entity.Report;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReportCreateResponse {

    private Long reportId;
    private Long reporterId;
    private ReportTargetType targetType;
    private Long targetId;
    private String reason;
    private ReportStatus status;
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
