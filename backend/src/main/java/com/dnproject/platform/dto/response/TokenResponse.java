package com.dnproject.platform.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;

    private String email;
    private String role;

    public static TokenResponse of(
            String grantType, String accessToken,
            String refreshToken, String email, String role) {
        return TokenResponse.builder()
                .grantType(grantType)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(email)
                .role(role)
                .build();
    }
}
