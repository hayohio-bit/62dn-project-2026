package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
    // 금전
    CREDIT_CARD("신용카드"),
    BANK_TRANSFER("계좌이체"),
    VIRTUAL_ACCOUNT("가상계좌"),
    KAKAO_PAY("카카오페이"),

    // 물품
    PARCEL("택배송부"),
    DIRECT_DELIVERY("직접전달"),
    QUICK_SERVICE("퀵서비스");

    private final String description;
}
