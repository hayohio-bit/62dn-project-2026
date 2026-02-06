package com.dnproject.platform.service;

import com.dnproject.platform.dto.response.BoardResponse;
import com.dnproject.platform.dto.response.CommentResponse;
import com.dnproject.platform.repository.BoardRepository;
import com.dnproject.platform.repository.CommentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminBoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public Page<BoardResponse> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable)
                .map(BoardResponse::from);
    }

    @Transactional
    public void deleteBoard(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + id);
        }
        boardRepository.deleteById(id);
    }

    public Page<CommentResponse> getComments(Pageable pageable) {
        return commentRepository.findAll(pageable)
                .map(CommentResponse::from);
    }

    @Transactional
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new EntityNotFoundException("댓글을 찾을 수 없습니다. ID: " + id);
        }
        commentRepository.deleteById(id);
    }
}
