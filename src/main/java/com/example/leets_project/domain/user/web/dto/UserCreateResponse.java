package com.example.leets_project.domain.user.web.dto;

import com.example.leets_project.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCreateResponse {

    private Long id;
    private String name;
    private String email;
    private String nickname;

    public static UserCreateResponse from(User user) {
        return UserCreateResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}
