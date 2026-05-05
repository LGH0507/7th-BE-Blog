package com.example.leets_project.domain.user.service;

import com.example.leets_project.common.exception.GeneralException;
import com.example.leets_project.common.response.ErrorCode;
import com.example.leets_project.domain.user.entity.User;
import com.example.leets_project.domain.user.repository.UserRepository;
import com.example.leets_project.domain.user.web.dto.UserCreateRequest;
import com.example.leets_project.domain.user.web.dto.UserCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserCreateResponse createUser(UserCreateRequest request) {
        // 1. 이메일 중복 검증
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new GeneralException(ErrorCode.USER_EMAIL_ALREADY_EXISTS);
        }
        // 2. 닉네임 중복 검증
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new GeneralException(ErrorCode.USER_NICKNAME_ALREADY_EXISTS);
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .nickname(request.getNickname())
                .build();

        User savedUser = userRepository.save(user);

        return UserCreateResponse.from(savedUser);
    }
}