package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VolunteerStatus {
    PENDING("승인 대기"),
    APPROVED("신청 승인"),
    REJECTED("신청 거절"),
    CANCELLED("신청 취소"),
    COMPLETED("봉사 완료"); // 실제 봉사 활동이 끝난 후 상태

    private final String description;
}
