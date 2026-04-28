package com.example.leets_project.domain.report.entity;

import com.example.leets_project.common.entity.BaseEntity;
import com.example.leets_project.common.exception.GeneralException;
import com.example.leets_project.common.response.ErrorCode;
import com.example.leets_project.domain.report.ReportStatus;
import com.example.leets_project.domain.report.ReportTargetType;
import com.example.leets_project.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reports")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @Enumerated(EnumType.STRING)
    private ReportTargetType targetType; // POST or COMMENT

    private Long targetId; // postId or commentId

    @Column(nullable = false)
    private String reason;

    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.PENDING;

    @Builder
    public Report(User reporter, ReportTargetType targetType, Long targetId, String reason) {
        this.reporter = reporter;
        this.targetType = targetType;
        this.targetId = targetId;
        this.reason = reason;
        this.status = ReportStatus.PENDING; // 생성 시 항상 PENDING
    }

    public void resolve() {
        if (this.status == ReportStatus.RESOLVED) {
            throw new GeneralException(ErrorCode.REPORT_ALREADY_RESOLVED);
        }
        this.status = ReportStatus.RESOLVED;
    }
}
