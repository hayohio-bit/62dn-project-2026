package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdoptionType {
    ADOPTION("정식 입양"),
    FOSTER("임시 보호");

    private final String description;
}