package com.example.leets_project.domain.comment.entity;

import com.example.leets_project.common.entity.BaseEntity;
import com.example.leets_project.common.exception.GeneralException;
import com.example.leets_project.common.response.ErrorCode;
import com.example.leets_project.domain.comment.CommentStatus;
import com.example.leets_project.domain.post.entity.Post;
import com.example.leets_project.domain.user.User;
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
    public Comment(User user, Post post, String content, CommentStatus status) {
        this.user = user;
        this.post = post;
        this.content = content;
        this.status = CommentStatus.ACTIVE;
    }

    // 댓글 수정
    public void updateContent(String content, Long requesterId) {
        validateOwner(requesterId);
        this.content = content;
    }
    // 댓글 삭제(상태 변경) 로직
    public void changeStatusToDeleted() {
        this.status = CommentStatus.DELETED;
        this.delete(); // BaseEntity에 있는 deletedAt 기록 메서드 호출
    }
    // 작성자 검증
    public void validateOwner(Long userId){
        if(!this.user.getId().equals(userId)){
            throw new GeneralException(ErrorCode.COMMENT_FORBIDDEN);
        }
    }
}
