package com.dnproject.platform.dto.response;

/*
* 엔티티에 모든 정보를 던져주면 안됨. 보안이슈
* DB용 데이터, 민감한 데이터는 숨기고 필요한 정보만 보내주기.
* */

import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    // 보안 철칙 제 1장 패스워드는 응답하지 않는다. 왜 why? 로그가 남아버림
//
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String address;
    private Role role;

    // 엔티티 -> DTO 변환
    public static UserResponse from(User user){
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .role(user.getRole())
                .build();
    }
}
