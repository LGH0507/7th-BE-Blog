package com.example.leets_project.domain.post.entity;

import com.example.leets_project.common.entity.BaseEntity;
import com.example.leets_project.common.exception.GeneralException;
import com.example.leets_project.common.response.ErrorCode;
import com.example.leets_project.domain.comment.CommentStatus;
import com.example.leets_project.domain.comment.entity.Comment;
import com.example.leets_project.domain.post.PostStatus;
import com.example.leets_project.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private String description;

    // 양방향: Post → Comment
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status = PostStatus.ACTIVE;

    @Builder
    public Post(User user, String title, String content, String description) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.description = description;
        this.status = PostStatus.ACTIVE;
    }
    public void updatePost(String title, String content, String description, Long requesterId) {
        validateOwner(requesterId);
        // 상태 전이 방어: 삭제되거나 숨겨진 게시글(신고)은 수정 불가
        if (this.status == PostStatus.DELETED) {
            throw new GeneralException(ErrorCode.POST_ALREADY_DELETED);
        }
        if (this.status == PostStatus.HIDDEN) {
            throw new GeneralException(ErrorCode.POST_ALREADY_HIDDEN);
        }
        this.title = title;
        this.content = content;
        this.description = description;
    }
    // 게시글 숨김(ACTIVE → HIDDEN)
    public void hide(Long requesterId) {
        validateOwner(requesterId);
        if (this.status == PostStatus.HIDDEN) {
            throw new GeneralException(ErrorCode.POST_ALREADY_HIDDEN);
        }
        if (this.status == PostStatus.DELETED) {
            throw new GeneralException(ErrorCode.POST_ALREADY_DELETED);
        }
        this.status = PostStatus.HIDDEN;
    }
    // 게시글 삭제(ACTIVE -> DELETED, soft delete)
    public void delete(Long requesterId) {
        validateOwner(requesterId);
        if (this.status == PostStatus.DELETED) {
            throw new GeneralException(ErrorCode.POST_ALREADY_DELETED);
        }
        if (this.status == PostStatus.HIDDEN) {
            throw new GeneralException(ErrorCode.POST_ALREADY_HIDDEN);
        }
        this.status = PostStatus.DELETED;
        super.delete(); // BaseEntity deletedAt 기록
    }
    // 게시글 복구(Hidden -> ACTIVE)
    public void restore(Long requesterId) {
        validateOwner(requesterId);
        if (this.status == PostStatus.ACTIVE) {
            throw new GeneralException(ErrorCode.POST_ALREADY_ACTIVE);
        }
        if (this.status == PostStatus.DELETED) {
            throw new GeneralException(ErrorCode.POST_ALREADY_DELETED);
        }
        this.status = PostStatus.ACTIVE;
    }

    public void validateOwner(Long userId) {
        if (!this.user.getId().equals(userId)) {
            throw new GeneralException(ErrorCode.POST_FORBIDDEN);
        }
    }
    // HIDDEN/DELETED 게시글 상태 검증
    public void validateVisible() {
        if (this.status == PostStatus.HIDDEN) {
            throw new GeneralException(ErrorCode.POST_ALREADY_HIDDEN);
        }
        if (this.status == PostStatus.DELETED) {
            throw new GeneralException(ErrorCode.POST_ALREADY_DELETED);
        }
    }
}
