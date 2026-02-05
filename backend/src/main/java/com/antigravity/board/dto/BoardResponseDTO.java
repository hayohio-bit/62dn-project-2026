package com.antigravity.board.dto;

import com.antigravity.board.entity.Board;
import com.antigravity.board.entity.BoardType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    private BoardType type;
    private Integer viewCount;
    private Integer commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BoardResponseDTO fromEntity(Board board) {
        return BoardResponseDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .type(board.getType())
                .viewCount(board.getViewCount())
                .commentCount(board.getComments() != null ? board.getComments().size() : 0)
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
    }
}
