package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DonationStatus {
    // 공통
    PENDING("대기"),
    PROCESSING("처리 중"),

    // 물품 기부 관련 (배송 프로세스)
    SHIPPED("발송 완료"),
    RECEIVED("수령 완료"),

    // 최종 상태
    COMPLETED("기부 완료"),
    CANCELLED("취소"),
    REJECTED("반려");

    private final String description;
}