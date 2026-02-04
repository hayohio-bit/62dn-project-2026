package com.dnproject.platform.dto.response;


import com.dnproject.platform.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BoardResponse {

    private Long id;
    private String type; // Enum을 String으로
    private String title;
    private String writerName; // 작성자 이름 (User 엔티티에서 가져옴)
    private int views;
    private boolean isPinned;
    private LocalDateTime createdAt;

    public static BoardResponse from(Board board) {
        return BoardResponse.builder()
                .id(board.getId())
                .type(board.getType().name())
                .title(board.getTitle())
                .writerName(board.getUser().getName()) // 닉네임이나 이름
                .views(board.getViews())
                .isPinned(board.isPinned())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
