package com.dnproject.platform.service;

import com.dnproject.platform.domain.Board;
import com.dnproject.platform.domain.Comment;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.BoardType;
import com.dnproject.platform.dto.request.CommentCreateRequest;
import com.dnproject.platform.dto.response.CommentResponse;
import com.dnproject.platform.repository.BoardRepository;
import com.dnproject.platform.repository.CommentRepository;
import com.dnproject.platform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    private User savedUser;
    private Board savedBoard;

    @BeforeEach
    void setUp() {
        savedUser = userRepository.save(User.builder()
                .email("commenter@test.com")
                .name("댓글러")
                .password("1234")
                .build());

        savedBoard = boardRepository.save(Board.builder()
                .user(savedUser)
                .title("게시글")
                .content("내용")
                .type(BoardType.FREE)
                .build());
    }

    @Test
    @DisplayName("댓글 추가 테스트")
    void addCommentTest() {
        // given
        CommentCreateRequest request = CommentCreateRequest.builder()
                .content("좋은 글이네요!")
                .build();

        // when
        CommentResponse response = commentService.addComment(savedBoard.getId(), request, savedUser.getId());

        // then
        assertThat(response.getContent()).isEqualTo("좋은 글이네요!");
        assertThat(commentRepository.existsById(response.getId())).isTrue();
    }

    @Test
    @DisplayName("댓글 목록 조회 테스트")
    void getCommentsTest() {
        // given
        commentRepository.save(Comment.builder().board(savedBoard).user(savedUser).content("댓글1").build());
        commentRepository.save(Comment.builder().board(savedBoard).user(savedUser).content("댓글2").build());

        // when
        List<CommentResponse> responses = commentService.getComments(savedBoard.getId());

        // then
        assertThat(responses).hasSize(2);
    }
}
