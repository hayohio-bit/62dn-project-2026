package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VerificationStatus {
    UNVERIFIED("미인증"),
    PENDING("검토 중"),
    VERIFIED("인증 완료"),
    REJECTED("인증 거절");

    private final String description;
}
