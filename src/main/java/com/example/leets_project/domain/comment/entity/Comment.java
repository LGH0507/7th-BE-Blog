package com.example.leets_project.domain.comment.entity;

import com.example.leets_project.common.entity.BaseEntity;
import com.example.leets_project.common.exception.GeneralException;
import com.example.leets_project.common.response.ErrorCode;
import com.example.leets_project.domain.comment.CommentStatus;
import com.example.leets_project.domain.post.entity.Post;
import com.example.leets_project.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false, length = 255)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommentStatus status =  CommentStatus.ACTIVE;

    @Builder
    public Comment(User user, Post post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
        this.status = CommentStatus.ACTIVE;
    }

    // 댓글 수정
    public void updateContent(String content, Long requesterId) {
        validateOwner(requesterId);
        // 상태 전이 방어: 삭제되거나 숨겨진 댓글은 수정 불가
        if (this.status == CommentStatus.DELETED) {
            throw new GeneralException(ErrorCode.COMMENT_ALREADY_DELETED);
        }
        if (this.status == CommentStatus.HIDDEN) {
            throw new GeneralException(ErrorCode.COMMENT_ALREADY_HIDDEN);
        }
        this.content = content;
    }
    // 댓글 삭제(상태 변경) 로직
    public void delete(Long requesterId) {
        validateOwner(requesterId);
        if (this.status == CommentStatus.DELETED) {
            throw new GeneralException(ErrorCode.COMMENT_ALREADY_DELETED);
        }
        if (this.status == CommentStatus.HIDDEN) {
            throw new GeneralException(ErrorCode.COMMENT_ALREADY_HIDDEN);
        }
        this.status = CommentStatus.DELETED;
        super.delete(); // BaseEntity deletedAt 기록
    }
    // 댓글 숨김(상태 변경) 로직
    public void hide(Long requesterId) {
        // 댓글 숨김 권한은 게시글 작성자
        if (!this.post.getUser().getId().equals(requesterId)) {
            throw new GeneralException(ErrorCode.COMMENT_FORBIDDEN);
        }
        if (this.status == CommentStatus.HIDDEN) {
            throw new GeneralException(ErrorCode.COMMENT_ALREADY_HIDDEN);
        }
        if (this.status == CommentStatus.DELETED) {
            throw new GeneralException(ErrorCode.COMMENT_ALREADY_DELETED);
        }
        this.status = CommentStatus.HIDDEN;
    }
    // 작성자 검증
    public void validateOwner(Long userId){
        if(!this.user.getId().equals(userId)){
            throw new GeneralException(ErrorCode.COMMENT_FORBIDDEN);
        }
    }
}
