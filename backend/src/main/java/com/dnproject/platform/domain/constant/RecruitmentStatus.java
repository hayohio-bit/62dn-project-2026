package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecruitmentStatus {
    RECRUITING("모집 중"),
    CLOSED("모집 완료"), // 목표 인원이 다 찼을 때
    EXPIRED("기간 만료"), // 모집 기간이 지났을 때
    PENDING("승인 대기"), // 모집글 등록 후 관리자 승인 대기 시
    CANCELED("모집 취소"); // 작성자가 모집을 철회했을 때

    private final String description;
}