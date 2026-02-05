package com.antigravity.animal.entity;

public enum AnimalStatus {
    AVAILABLE, // 입양 가능
    PENDING_ADOPTION, // 입양 진행 중
    ADOPTED, // 입양 완료
    INACTIVE // 비활성 (보호소 관리 외)
}
