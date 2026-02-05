package com.dnproject.platform.controller;

import com.dnproject.platform.domain.constant.BoardType;
import com.dnproject.platform.dto.request.BoardCreateRequest;
import com.dnproject.platform.dto.request.CommentCreateRequest;
import com.dnproject.platform.dto.response.BoardResponse;
import com.dnproject.platform.dto.response.CommentResponse;
import com.dnproject.platform.service.BoardService;
import com.dnproject.platform.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getBoards(
            @RequestParam(required = false) BoardType type,
            Pageable pageable) {
        Page<BoardResponse> boards = boardService.getBoards(type, pageable);
        return ResponseEntity.ok(Map.of(
                "data", Map.of(
                        "content", boards.getContent(),
                        "totalPages", boards.getTotalPages(),
                        "totalElements", boards.getTotalElements())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getBoard(@PathVariable Long id) {
        BoardResponse board = boardService.getBoard(id);
        return ResponseEntity.ok(Map.of("data", board));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createBoard(@RequestBody BoardCreateRequest request) {
        Long userId = 1L; // Mock userId
        BoardResponse response = boardService.createBoard(request, userId);
        return ResponseEntity.ok(Map.of("data", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateBoard(@PathVariable Long id,
            @RequestBody BoardCreateRequest request) {
        Long userId = 1L; // Mock userId
        BoardResponse response = boardService.updateBoard(id, request, userId);
        return ResponseEntity.ok(Map.of("data", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        Long userId = 1L; // Mock userId
        boardService.deleteBoard(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<Map<String, Object>> addComment(
            @PathVariable Long boardId,
            @RequestBody CommentCreateRequest request) {
        Long userId = 1L; // Mock userId
        CommentResponse response = commentService.addComment(boardId, request, userId);
        return ResponseEntity.ok(Map.of("data", response));
    }

    @GetMapping("/{boardId}/comments")
    public ResponseEntity<Map<String, Object>> getComments(@PathVariable Long boardId) {
        List<CommentResponse> comments = commentService.getComments(boardId);
        return ResponseEntity.ok(Map.of("data", comments));
    }
}
