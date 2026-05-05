package com.example.leets_project.domain.report.web.dto;

import com.example.leets_project.domain.report.ReportStatus;
import com.example.leets_project.domain.report.entity.Report;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportResolveResponse {

    private Long reportId;
    private ReportStatus status;

    public static ReportResolveResponse from(Report report) {
        return ReportResolveResponse.builder()
                .reportId(report.getId())
                .status(report.getStatus())
                .build();
    }
}
