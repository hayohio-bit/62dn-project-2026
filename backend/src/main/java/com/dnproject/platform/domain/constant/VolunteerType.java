package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VolunteerType {
    INDIVIDUAL("개인 봉사"),
    GROUP("단체 봉사");

    private final String description;
}
