package com.dnproject.platform.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UpdateMeRequest {
    private String name;
    private String phone;
    private String address;

    // 비밀번호 수정까지 포함
    private String currentPassword;
    private String newPassword;
}
