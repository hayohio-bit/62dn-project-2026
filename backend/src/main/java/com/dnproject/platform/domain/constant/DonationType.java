package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DonationType {
    MONEY("금전 후원"),
    ITEM("물품 후원");

    private final String description;
}