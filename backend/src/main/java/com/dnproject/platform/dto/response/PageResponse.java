package com.dnproject.platform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> content; // 실제 데이터 리스트
    private int page; // 현재 페이지 번호 (프론트엔드 호환을 위해 명칭 변경)
    private int size; // 한 페이지당 데이터 개수 (프론트엔드 호환을 위해 명칭 변경)
    private long totalElements; // 전체 데이터 개수
    private int totalPages; // 전체 페이지 수
    private boolean last; // 마지막 페이지 여부

    public static <T> PageResponse<T> from(org.springframework.data.domain.Page<T> page) {
        return PageResponse.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
