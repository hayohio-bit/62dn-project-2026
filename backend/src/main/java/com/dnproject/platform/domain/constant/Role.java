package com.dnproject.platform.domain.constant;

public enum Role {
    USER, // 일반 사용자
    MANAGER, // 기존 매니저 (하위 호환용)
    SHELTER_ADMIN, // 보호소 관리자
    SUPER_ADMIN, // 시스템 관리자
    ADMIN // 기존 관리자 (하위 호환용)
}
