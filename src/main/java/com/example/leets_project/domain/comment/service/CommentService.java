package com.example.leets_project.domain.comment.service;

import com.example.leets_project.common.exception.GeneralException;
import com.example.leets_project.common.response.ErrorCode;
import com.example.leets_project.domain.comment.CommentStatus;
import com.example.leets_project.domain.comment.entity.Comment;
import com.example.leets_project.domain.comment.repository.CommentRepository;
import com.example.leets_project.domain.comment.web.dto.*;
import com.example.leets_project.domain.post.entity.Post;
import com.example.leets_project.domain.post.repository.PostRepository;
import com.example.leets_project.domain.user.User;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    @Transactional
    public CommentCreateResponse createComment(Long postId, Long currentUserId, CommentCreateRequest request){

        User user = findUserOrThrow(currentUserId);
        Post post = findPostOrThrow(postId);

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(request.getContent())
                .build();

        return CommentCreateResponse.from(commentRepository.save(comment));
    }

    // 댓글 수정
    @Transactional
    public CommentUpdateResponse updateComment(Long postId,Long commentId, Long currentUserId, CommentUpdateRequest request){

        findUserOrThrow(currentUserId);
        findPostOrThrow(postId);
        Comment comment = findCommentOrThrow(commentId);
        validateCommentBelongsToPost(comment, postId);

        comment.updateContent(request.getContent(), currentUserId);

        return CommentUpdateResponse.from(commentRepository.save(comment));
    }
    // 댓글 삭제
    @Transactional
    public CommentDeleteResponse deleteComment(Long postId, Long commentId, Long currentUserId){

        findUserOrThrow(currentUserId);
        findPostOrThrow(postId);
        Comment comment = findCommentOrThrow(commentId);
        validateCommentBelongsToPost(comment, postId);

        // 상태 변경(ACTIVE -> DELETED)
        comment.changeStatusToDeleted();

        return CommentDeleteResponse.of(commentId);
    }
    // 댓글 목록 조회(특정 게시글)
    public Page<CommentListResponse> getComments(Long postId, int page, int size) {

        findPostOrThrow(postId);

        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));

        return commentRepository.findAllByPostIdAndStatus(postId, CommentStatus.ACTIVE, pageable)
                .map(CommentListResponse::from);
    }

    // 공통 검증 로직
    // 사용자 검증
    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
    }
    // 게시글 검증
    private Post findPostOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorCode.POST_NOT_FOUND));
    }
    // 댓글 검증(존재/DELETED/HIDDEN)
    private Comment findCommentOrThrow(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(ErrorCode.COMMENT_NOT_FOUND));

        if (comment.getStatus() == CommentStatus.DELETED) {
            throw new GeneralException(ErrorCode.COMMENT_ALREADY_DELETED);
        }
        if (comment.getStatus() == CommentStatus.HIDDEN) {
            throw new GeneralException(ErrorCode.COMMENT_ALREADY_HIDDEN);
        }
        return comment;
    }
    // 댓글이 해당 게시글 소속인지 검증
    private void validateCommentBelongsToPost(Comment comment, Long postId) {
        if (!comment.getPost().getId().equals(postId)) {
            throw new GeneralException(ErrorCode.COMMENT_NOT_FOUND);
        }
    }
}
