package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SyncTriggerType {
    MANUAL("수동실행"),
    SCHEDULED("스케줄러 실행");

        private final String description;
}
