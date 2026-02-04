package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Species {
    DOG("개"),
    CAT("고양이"),
    ETC("기타");

    private final String description;
}
