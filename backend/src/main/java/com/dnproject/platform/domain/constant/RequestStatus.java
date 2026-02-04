package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestStatus {
    OPEN("모집 중"),
    CLOSED("마감");

    private final String description;
}
