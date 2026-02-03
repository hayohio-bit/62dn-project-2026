package com.dnproject.platform.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardType {
    FREE("자유게시판"),
    NOTICE("공지사항"),
    Stort("입양후기"),
    FAQ("자주묻는질문");

    private final String description;
}
