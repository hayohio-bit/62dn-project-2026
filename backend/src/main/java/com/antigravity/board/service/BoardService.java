package com.antigravity.board.service;

import com.antigravity.board.dto.BoardRequestDTO;
import com.antigravity.board.dto.BoardResponseDTO;
import com.antigravity.board.dto.CommentRequestDTO;
import com.antigravity.board.dto.CommentResponseDTO;
import com.antigravity.board.entity.Board;
import com.antigravity.board.entity.BoardType;
import com.antigravity.board.entity.Comment;
import com.antigravity.board.repository.BoardRepository;
import com.antigravity.board.repository.CommentRepository;
import com.antigravity.global.exception.BusinessException;
import com.antigravity.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public BoardResponseDTO createBoard(BoardRequestDTO requestDTO) {
        Board board = requestDTO.toEntity();
        Board savedBoard = boardRepository.save(board);
        return BoardResponseDTO.fromEntity(savedBoard);
    }

    public BoardResponseDTO getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        return BoardResponseDTO.fromEntity(board);
    }

    @Transactional
    public BoardResponseDTO getBoardWithViewCount(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        board.increaseViewCount();
        return BoardResponseDTO.fromEntity(board);
    }

    public Page<BoardResponseDTO> getBoards(BoardType type, String keyword, Pageable pageable) {
        Page<Board> boards;
        if (keyword != null && !keyword.isBlank()) {
            boards = boardRepository.searchByType(type, keyword, pageable);
        } else {
            boards = boardRepository.findByType(type, pageable);
        }
        return boards.map(BoardResponseDTO::fromEntity);
    }

    @Transactional
    public BoardResponseDTO updateBoard(Long id, BoardRequestDTO requestDTO) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        board.update(requestDTO.getTitle(), requestDTO.getContent());
        return BoardResponseDTO.fromEntity(board);
    }

    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        boardRepository.delete(board);
    }

    // Comment management
    @Transactional
    public CommentResponseDTO addComment(Long boardId, CommentRequestDTO requestDTO) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        Comment comment = Comment.builder()
                .board(board)
                .content(requestDTO.getContent())
                .author(requestDTO.getAuthor())
                .build();

        Comment savedComment = commentRepository.save(comment);
        return CommentResponseDTO.fromEntity(savedComment);
    }

    public List<CommentResponseDTO> getComments(Long boardId) {
        return commentRepository.findByBoardId(boardId).stream()
                .map(CommentResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        commentRepository.delete(comment);
    }
}
