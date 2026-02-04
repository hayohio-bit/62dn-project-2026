package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityCycle {
    ONCE("일회성"),
    DAILY("매일"),
    WEEKLY("매주"),
    MONTHLY("매월"),
    QUARTERLY("분기별"),
    AS_NEEDED("수시(필요 시)");

    private final String description;
}
