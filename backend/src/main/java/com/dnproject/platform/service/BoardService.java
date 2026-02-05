package com.dnproject.platform.service;

import com.dnproject.platform.domain.Board;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.BoardType;
import com.dnproject.platform.dto.request.BoardCreateRequest;
import com.dnproject.platform.dto.response.BoardResponse;
import com.dnproject.platform.repository.BoardRepository;
import com.dnproject.platform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Page<BoardResponse> getBoards(BoardType type, Pageable pageable) {
        if (type != null) {
            return boardRepository.findByType(type, pageable).map(BoardResponse::from);
        }
        return boardRepository.findAll(pageable).map(BoardResponse::from);
    }

    @Transactional
    public BoardResponse getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + id));
        board.incrementViews();
        return BoardResponse.from(board);
    }

    @Transactional
    public BoardResponse createBoard(BoardCreateRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        Board board = request.toEntity(user);
        Board saved = boardRepository.save(board);
        return BoardResponse.from(saved);
    }

    @Transactional
    public BoardResponse updateBoard(Long id, BoardCreateRequest request, Long userId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + id));

        if (!board.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("자신의 게시글만 수정할 수 있습니다.");
        }

        board.update(request.getTitle(), request.getContent(), false); // isPinned는 일단 false로 고정 또는 request에 추가
        return BoardResponse.from(board);
    }

    @Transactional
    public void deleteBoard(Long id, Long userId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + id));

        if (!board.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("자신의 게시글만 삭제할 수 있습니다.");
        }

        boardRepository.delete(board);
    }
}
