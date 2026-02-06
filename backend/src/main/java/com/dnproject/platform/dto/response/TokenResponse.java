package com.dnproject.platform.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private UserResponse user;

    public static TokenResponse of(
            String accessToken,
            String refreshToken,
            Long expiresIn,
            UserResponse user) {
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(expiresIn)
                .user(user)
                .build();
    }
}
