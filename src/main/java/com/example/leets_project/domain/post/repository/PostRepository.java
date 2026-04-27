package com.example.leets_project.domain.post.repository;

import com.example.leets_project.domain.post.PostStatus;
import com.example.leets_project.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByStatus(PostStatus status, Pageable pageable);
}
