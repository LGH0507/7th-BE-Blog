package com.example.leets_project.domain.report.service;

import com.example.leets_project.common.exception.GeneralException;
import com.example.leets_project.common.response.ErrorCode;
import com.example.leets_project.domain.comment.CommentStatus;
import com.example.leets_project.domain.comment.entity.Comment;
import com.example.leets_project.domain.comment.repository.CommentRepository;
import com.example.leets_project.domain.post.PostStatus;
import com.example.leets_project.domain.post.entity.Post;
import com.example.leets_project.domain.post.repository.PostRepository;
import com.example.leets_project.domain.report.ReportTargetType;
import com.example.leets_project.domain.report.entity.Report;
import com.example.leets_project.domain.report.repository.ReportRepository;
import com.example.leets_project.domain.report.web.dto.ReportCreateRequest;
import com.example.leets_project.domain.report.web.dto.ReportCreateResponse;
import com.example.leets_project.domain.report.web.dto.ReportResolveResponse;
import com.example.leets_project.domain.user.entity.User;
import com.example.leets_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 게시글 신고
    @Transactional
    public ReportCreateResponse reportPost(Long reporterId, Long postId,
                                           ReportCreateRequest request) {
        User reporter = findUserOrThrow(reporterId);
        Post post = findPostOrThrow(postId);

        // DELETED 게시글 신고 방지 (HIDDEN은 신고 가능)
        if (post.getStatus() == PostStatus.DELETED) {
            throw new GeneralException(ErrorCode.POST_ALREADY_DELETED);
        }

        // 본인 신고 방지
        if (post.getUser().getId().equals(reporterId)) {
            throw new GeneralException(ErrorCode.REPORT_CANNOT_SELF);
        }

        // 중복 신고 방지
        if (reportRepository.existsByReporterIdAndTargetTypeAndTargetId(
                reporterId, ReportTargetType.POST, postId)) {
            throw new GeneralException(ErrorCode.REPORT_ALREADY_EXISTS);
        }

        Report report = Report.builder()
                .reporter(reporter)
                .targetType(ReportTargetType.POST)
                .targetId(postId)
                .reason(request.getReason())
                .build();

        // 중복 신고 방지 - 2단계 (동시 요청 방어)
        try {
            return ReportCreateResponse.from(reportRepository.save(report));
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException(ErrorCode.REPORT_ALREADY_EXISTS);
        }
    }

    // 댓글 신고
    @Transactional
    public ReportCreateResponse reportComment(Long reporterId, Long commentId,
                                              ReportCreateRequest request) {
        User reporter = findUserOrThrow(reporterId);
        Comment comment = findCommentOrThrow(commentId);

        // DELETED 댓글 신고 방지 (HIDDEN은 신고 가능)
        if (comment.getStatus() == CommentStatus.DELETED) {
            throw new GeneralException(ErrorCode.COMMENT_ALREADY_DELETED);
        }

        // 자기 자신 신고 방지
        if (comment.getUser().getId().equals(reporterId)) {
            throw new GeneralException(ErrorCode.REPORT_CANNOT_SELF);
        }

        // 중복 신고 방지
        if (reportRepository.existsByReporterIdAndTargetTypeAndTargetId(
                reporterId, ReportTargetType.COMMENT, commentId)) {
            throw new GeneralException(ErrorCode.REPORT_ALREADY_EXISTS);
        }


        Report report = Report.builder()
                .reporter(reporter)
                .targetType(ReportTargetType.COMMENT)
                .targetId(commentId)
                .reason(request.getReason())
                .build();

        // 중복 신고 방지 - 2단계 (동시 요청 방어)
        try {
            return ReportCreateResponse.from(reportRepository.save(report));
        } catch (DataIntegrityViolationException e) {
            throw new GeneralException(ErrorCode.REPORT_ALREADY_EXISTS);
        }

    }

    // 신고 처리(PENDING → RESOLVED)
    @Transactional
    public ReportResolveResponse resolveReport(Long reportId) {
        Report report = findReportOrThrow(reportId);
        report.resolve();

        return ReportResolveResponse.from(report);
    }

    // --- 공통 검증 ---
    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
    }

    private Post findPostOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorCode.POST_NOT_FOUND));
    }

    private Comment findCommentOrThrow(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(ErrorCode.COMMENT_NOT_FOUND));
    }

    private Report findReportOrThrow(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new GeneralException(ErrorCode.REPORT_NOT_FOUND));
    }
}
