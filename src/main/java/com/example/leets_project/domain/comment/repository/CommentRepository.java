package com.example.leets_project.domain.comment.repository;

import com.example.leets_project.domain.comment.CommentStatus;
import com.example.leets_project.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글의 ACTIVE 댓글 목록
    Page<Comment> findAllByPostIdAndStatus(Long postId, CommentStatus status, Pageable pageable);
}
