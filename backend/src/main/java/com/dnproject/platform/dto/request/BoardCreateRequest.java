package com.dnproject.platform.dto.request;

import com.dnproject.platform.domain.Board;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.BoardType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardCreateRequest {
    private String title;
    private String content;
    private BoardType type;

    public Board toEntity(User user) {
        return Board.builder()
                .user(user)
                .title(this.title)
                .content(this.content)
                .type(this.type)
                .views(0)
                .isPinned(false)
                .build();
    }
}
