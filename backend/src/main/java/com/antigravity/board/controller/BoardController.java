package com.antigravity.board.controller;

import com.antigravity.board.dto.BoardRequestDTO;
import com.antigravity.board.dto.BoardResponseDTO;
import com.antigravity.board.dto.CommentRequestDTO;
import com.antigravity.board.dto.CommentResponseDTO;
import com.antigravity.board.entity.BoardType;
import com.antigravity.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Board", description = "Board Management API")
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시글 작성")
    @PostMapping
    public ResponseEntity<BoardResponseDTO> create(@Valid @RequestBody BoardRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createBoard(requestDTO));
    }

    @Operation(summary = "게시글 상세 조회 (조회수 증가)")
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.getBoardWithViewCount(id));
    }

    @Operation(summary = "게시글 목록 조회 (검색 및 페이징)")
    @GetMapping
    public ResponseEntity<Page<BoardResponseDTO>> list(
            @RequestParam BoardType type,
            @RequestParam(required = false) String keyword,
            Pageable pageable) {
        return ResponseEntity.ok(boardService.getBoards(type, keyword, pageable));
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody BoardRequestDTO requestDTO) {
        return ResponseEntity.ok(boardService.updateBoard(id, requestDTO));
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }

    // Comments
    @Operation(summary = "댓글 작성")
    @PostMapping("/{boardId}/comments")
    public ResponseEntity<CommentResponseDTO> addComment(@PathVariable Long boardId,
            @Valid @RequestBody CommentRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.addComment(boardId, requestDTO));
    }

    @Operation(summary = "댓글 목록 조회")
    @GetMapping("/{boardId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getComments(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.getComments(boardId));
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        boardService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
