package com.example.leets_project.domain.user.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 생성 요청")
public class UserCreateRequest {

    @Schema(description = "사용자 이름", example = "홍길동")
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @Schema(description = "사용자 이메일", example = "test@example.com")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Schema(description = "사용자 닉네임", example = "리츠화이팅")
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;
}
