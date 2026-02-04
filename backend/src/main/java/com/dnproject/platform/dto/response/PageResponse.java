package com.dnproject.platform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> content;        // 실제 게시글 리스트 (BoardResponse 목록)
    private int pageNumber;           // 현재 페이지 번호
    private int pageSize;             // 한 페이지당 데이터 개수
    private long totalElements;       // 전체 데이터 개수
    private int totalPages;           // 전체 페이지 수
    private boolean last;             // 마지막 페이지 여부
}
