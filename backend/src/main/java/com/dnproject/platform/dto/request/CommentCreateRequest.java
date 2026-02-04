package com.dnproject.platform.dto.request;


import com.dnproject.platform.domain.Board;
import com.dnproject.platform.domain.Comment;
import com.dnproject.platform.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreateRequest {
    private Long boardId;
    private String content;

    // Mapping
    public Comment toEntity(Board board, User user) {
        return Comment.builder()
                .content(this.content)
                .board(board)
                .user(user)
                .build();
    }
}
