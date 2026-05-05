package com.example.leets_project.domain.report.web.controller;

import com.example.leets_project.common.response.GlobalResponse;
import com.example.leets_project.common.response.SuccessCode;
import com.example.leets_project.domain.report.service.ReportService;
import com.example.leets_project.domain.report.web.dto.ReportCreateRequest;
import com.example.leets_project.domain.report.web.dto.ReportCreateResponse;
import com.example.leets_project.domain.report.web.dto.ReportResolveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "REPORT API", description = "게시글/댓글 신고 및 신고 처리 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;
    // 게시글 신고
    @Operation(summary = "게시글 신고", description = "게시글을 신고합니다. 중복 신고 및 자기 자신 신고는 불가합니다.")
    @PostMapping("/posts/{postId}")
    public ResponseEntity<GlobalResponse> reportPost(@RequestHeader("X-USER-ID") Long reporterId,
                                                     @PathVariable Long postId,
                                                     @RequestBody @Valid ReportCreateRequest request) {

        ReportCreateResponse response = reportService.reportPost(reporterId, postId, request);

        return GlobalResponse.onSuccess(SuccessCode.REPORT_POST, response);
    }

    // 댓글 신고
    @Operation(summary = "댓글 신고", description = "댓글을 신고합니다. 중복 신고 및 자기 자신 신고는 불가합니다.")
    @PostMapping("/comments/{commentId}")
    public ResponseEntity<GlobalResponse> reportComment(@RequestHeader("X-USER-ID") Long reporterId,
                                                        @PathVariable Long commentId,
                                                        @RequestBody @Valid ReportCreateRequest request) {

        ReportCreateResponse response = reportService.reportComment(reporterId, commentId, request);

        return GlobalResponse.onSuccess(SuccessCode.REPORT_COMMENT, response);
    }

    // 신고 처리(PENDING → RESOLVED)
    @Operation(summary = "신고 처리", description = "신고를 처리 완료 상태로 변경합니다. (PENDING → RESOLVED)")
    @PatchMapping("/{reportId}/resolve")
    public ResponseEntity<GlobalResponse> resolveReport(@PathVariable Long reportId) {

        ReportResolveResponse response = reportService.resolveReport(reportId);

        return GlobalResponse.onSuccess(SuccessCode.REPORT_RESOLVE, response);
    }
}
