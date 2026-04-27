package com.example.leets_project.domain.post.web.controller;

import com.example.leets_project.common.response.GlobalResponse;
import com.example.leets_project.common.response.SuccessCode;
import com.example.leets_project.domain.post.PostStatus;
import com.example.leets_project.domain.post.service.PostService;
import com.example.leets_project.domain.post.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name ="POST API", description = "게시글 생성,조회,수정,삭제 관련 API ")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // 게시물 생성
    @Operation(summary = "게시글 생성", description = "새로운 게시글을 등록합니다.")
    @PostMapping
    public ResponseEntity<GlobalResponse> createPost(@RequestHeader("X-USER-ID") Long currentUserId,
                                                     @RequestBody @Valid PostCreateRequest request){

    PostCreateResponse response = postService.createPost(currentUserId, request);

    return GlobalResponse.onSuccess(SuccessCode.POST_CREATE, response);
    }

    // 2. 게시글 목록 조회
    @Operation(summary = "게시글 목록 조회", description = "상태별 게시글 목록을 페이징으로 조회합니다.")
    @GetMapping
    public ResponseEntity<GlobalResponse> getPosts(@RequestParam(defaultValue = "ACTIVE") PostStatus status,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {

        Page<PostListResponse> postPage = postService.getPosts(status, page, size);
        PostListWrapperResponse response = PostListWrapperResponse.of(postPage);

        return GlobalResponse.onSuccess(SuccessCode.POST_LIST, response);
    }

    // 3. 게시글 상세 조회
    @Operation(summary = "게시글 상세 조회", description = "특정 게시글의 상세 내용을 조회합니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<GlobalResponse> getPostDetail(@PathVariable Long postId) {

        PostDetailResponse response = postService.getPostDetail(postId);

        return GlobalResponse.onSuccess(SuccessCode.POST_DETAIL, response);
    }

    // 4. 게시글 수정
    @Operation(summary = "게시글 수정", description = "ACTIVE 상태의 게시글을 수정합니다.")
    @PutMapping("/{postId}")
    public ResponseEntity<GlobalResponse> updatePost(@PathVariable Long postId,
                                                     @RequestHeader("X-USER-ID") Long currentUserId,
                                                     @RequestBody @Valid PostUpdateRequest request) {

        PostUpdateResponse response = postService.updatePost(postId, currentUserId, request);

        return GlobalResponse.onSuccess(SuccessCode.POST_UPDATE, response);
    }
    // 5. 게시글 숨김
    @Operation(summary = "게시글 숨김", description = "게시글을 HIDDEN 상태로 변경합니다.")
    @PatchMapping("/{postId}/hide")
    public ResponseEntity<GlobalResponse> hidePost(@PathVariable Long postId,
                                                   @RequestHeader("X-USER-ID") Long currentUserId) {

        PostHideResponse response = postService.hidePost(postId, currentUserId);

        return GlobalResponse.onSuccess(SuccessCode.POST_HIDE, response);
    }
    // 6. 게시글 삭제
    @Operation(summary = "게시글 삭제", description = "게시글을 DELETE 상태로 변경합니다.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<GlobalResponse> deletePost(@PathVariable Long postId,
                                                     @RequestHeader("X-USER-ID") Long currentUserId) {

        PostDeleteResponse response = postService.deletePost(postId, currentUserId);

        return GlobalResponse.onSuccess(SuccessCode.POST_DELETE, response);
    }
}
