package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Size {
    SMALL("소형"),
    MEDIUM("중형"),
    LARGE("대형");

    private final String description;
}
