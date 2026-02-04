package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DonorType {
    INDIVIDUAL("개인"),
    CORPORATE("기업"),
    ANONYMOUS("익명");

    private final String description;
}