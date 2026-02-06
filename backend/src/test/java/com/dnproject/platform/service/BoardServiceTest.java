package com.dnproject.platform.service;

import com.dnproject.platform.domain.Board;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.BoardType;
import com.dnproject.platform.dto.request.BoardCreateRequest;
import com.dnproject.platform.dto.response.BoardResponse;
import com.dnproject.platform.repository.BoardRepository;
import com.dnproject.platform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class BoardServiceTest {

        @Autowired
        private BoardService boardService;

        @Autowired
        private BoardRepository boardRepository;

        @Autowired
        private UserRepository userRepository;

        private User savedUser;

        @BeforeEach
        void setUp() {
                savedUser = userRepository.save(User.builder()
                                .email("writer@test.com")
                                .name("작성자")
                                .password("1234")
                                .build());
        }

        @Test
        @DisplayName("게시글 작성 테스트")
        void createBoardTest() {
                // given
                BoardCreateRequest request = BoardCreateRequest.builder()
                                .title("안녕하세요")
                                .content("가입인사 드립니다.")
                                .type(BoardType.FREE)
                                .build();

                // when
                BoardResponse response = boardService.createBoard(request, savedUser.getId());

                // then
                assertThat(response.getTitle()).isEqualTo("안녕하세요");
                assertThat(boardRepository.existsById(response.getId())).isTrue();
        }

        @Test
        @DisplayName("게시글 조회 및 조회수 증가 테스트")
        void getBoardTest() {
                // given
                Board board = boardRepository.save(Board.builder()
                                .user(savedUser)
                                .title("제목")
                                .content("내용")
                                .type(BoardType.FREE)
                                .build());

                // when
                BoardResponse response = boardService.getBoard(board.getId());

                // then
                assertThat(response.getViews()).isEqualTo(1);
        }

        @Test
        @DisplayName("게시글 수정 테스트")
        void updateBoardTest() {
                // given
                Board board = boardRepository.save(Board.builder()
                                .user(savedUser)
                                .title("수정 전")
                                .content("내용")
                                .type(BoardType.FREE)
                                .build());

                BoardCreateRequest updateRequest = BoardCreateRequest.builder()
                                .title("수정 후")
                                .content("내용 수정")
                                .build();

                // when
                BoardResponse response = boardService.updateBoard(board.getId(), updateRequest, savedUser.getId());

                // then
                assertThat(response.getTitle()).isEqualTo("수정 후");
        }

        @Test
        @DisplayName("게시글 삭제 실패 - 권한 없음")
        void deleteBoardFailNoPermission() {
                // given
                Board board = boardRepository.save(Board.builder()
                                .user(savedUser)
                                .title("제목")
                                .content("내용")
                                .type(BoardType.FREE)
                                .build());

                User otherUser = userRepository
                                .save(User.builder().email("other@test.com").name("다른사람").password("1234").build());

                // when & then
                assertThatThrownBy(() -> boardService.deleteBoard(board.getId(), otherUser.getId()))
                                .isInstanceOf(IllegalArgumentException.class)
                                .hasMessage("자신의 게시글만 삭제할 수 있습니다.");
        }
}
