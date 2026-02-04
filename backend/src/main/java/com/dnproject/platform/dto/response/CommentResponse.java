package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private String writerName;
    private LocalDateTime createdAt;

    // Mapping
    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .writerName(comment.getUser().getName())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
