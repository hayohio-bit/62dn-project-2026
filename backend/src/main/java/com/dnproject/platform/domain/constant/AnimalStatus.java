package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AnimalStatus {
    PROTECTED("보호 중"),
    FOSTERING("임시보호 중"),
    ADOPTED("입양 완료"),
    EUTHANIZED("안락사"),
    NATURAL_DEATH("자연사"),
    RETURNED("원주인 반환");

    private final String description;
}
