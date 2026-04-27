package com.example.leets_project.domain.post.service;

import com.example.leets_project.common.exception.GeneralException;
import com.example.leets_project.common.response.ErrorCode;
import com.example.leets_project.domain.post.PostStatus;
import com.example.leets_project.domain.post.entity.Post;
import com.example.leets_project.domain.post.repository.PostRepository;
import com.example.leets_project.domain.post.web.dto.*;
import com.example.leets_project.domain.user.entity.User;
import com.example.leets_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private static final int MIN_PAGE = 0;
    private static final int MAX_PAGE = 10;

    // 1. 게시글 생성
    @Transactional
    public PostCreateResponse createPost(Long currentUserId, PostCreateRequest request) {
        // 사용자 존재 확인
        User user = findUserOrThrow(currentUserId);

        Post post = Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .description(request.getDescription())
                .build();

        Post savedPost = postRepository.save(post);

        return PostCreateResponse.from(savedPost);
    }

    // 2. 게시글 목록 조회 - 상태별 필터링, 기본값 ACTIVE
    // GET /api/posts?status=ACTIVE   → ACTIVE 게시글만
    public Page<PostListResponse> getPosts(PostStatus status, int page, int size) {

        validatePageRange(size);
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return postRepository.findAllByStatus(status, pageable)
                .map(PostListResponse::from);
    }

    // 3. 게시글 상세 조회 - 상태 무관하게 조회 가능
    public PostDetailResponse getPostDetail(Long postId) {

        Post post = findPostOrThrow(postId);

        return PostDetailResponse.from(post);
    }

    // 4. 게시글 수정
    @Transactional
    public PostUpdateResponse updatePost(Long postId, Long currentUserId, PostUpdateRequest request) {

        Post post = findPostOrThrow(postId);

        post.updatePost(
                request.getTitle(),
                request.getContent(),
                request.getDescription(),
                currentUserId
        );

        return PostUpdateResponse.from(post);
    }
    // 5. 게시글 숨김(ACTIVE → HIDDEN)
    @Transactional
    public PostHideResponse hidePost(Long postId, Long currentUserId) {
        Post post = findPostOrThrow(postId);
        post.hide(currentUserId);
        return PostHideResponse.from(post);
    }
    // 6. 게시글 삭제(soft delete)
    @Transactional
    public PostDeleteResponse deletePost(Long postId, Long currentUserId) {
        Post post = findPostOrThrow(postId);
        post.delete(currentUserId);
        return PostDeleteResponse.of(postId);
    }

    // 공통 검증 로직
    // 게시글 조회
    private Post findPostOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorCode.POST_NOT_FOUND));
    }

    // 사용자 조회
    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
    }

    private void validatePageRange(int page) {
        if (page < MIN_PAGE || page > MAX_PAGE) {
            throw new GeneralException(ErrorCode.POST_INVALID);
        }
    }
}