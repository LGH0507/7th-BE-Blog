package com.example.leets_project.domain.user.web.dto;

import com.example.leets_project.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "사용자 생성 응답")
public class UserCreateResponse {

    @Schema(description = "사용자 ID", example = "1")
    private Long id;
    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;
    @Schema(description = "사용자 이메일", example = "test@example.com")
    private String email;
    @Schema(description = "사용자 닉네임", example = "리츠화이팅")
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
