package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdoptionStatus {
    PENDING("심사 대기"),
    APPROVED("입양 승인"),
    REJECTED("입양 거절"),
    CANCELLED("신청 취소");

    private final String description;
}
