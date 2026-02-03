package com.dnproject.platform.dto.request;

import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String passwordConfirm;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    private String phone;
    private String address;

    public User toEntity(String encodedPassword, Role role) {
        return User.builder()
                .email(this.email)
                .password(encodedPassword)
                .name(this.name)
                .phone(this.phone)
                .address(this.address)
                .role(role)
                .build();
    }

}
