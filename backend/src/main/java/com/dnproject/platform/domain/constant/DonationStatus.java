package com.dnproject.platform.domain.constant;

public enum DonationStatus {
    // 공통
    PENDING,    // 대기
    PROCESSING, // 처리중

    // 물품 기부 관련
    SHIPPED,    //발송완료
    RECEIVED,   //수령완료

    // 최종 상태
    COMPLETED,  //기부완료
    CANCELLED,  //취소
    REJECTED    //반려
}
