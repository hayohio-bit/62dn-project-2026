package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminUserResponse {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String role;
    private boolean suspended;
    private LocalDateTime createdAt;

    public static AdminUserResponse from(User user) {
        return AdminUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .suspended(user.isSuspended())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
