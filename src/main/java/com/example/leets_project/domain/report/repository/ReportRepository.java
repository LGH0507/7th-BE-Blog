package com.example.leets_project.domain.report.repository;

import com.example.leets_project.domain.report.ReportTargetType;
import com.example.leets_project.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

    // 중복 신고 방지 - (신고자 + 대상유형 + 대상ID) 조합 확인
    boolean existsByReporterIdAndTargetTypeAndTargetId(
            Long reporterId, ReportTargetType targetType, Long targetId
    );
}
