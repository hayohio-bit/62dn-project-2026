package com.dnproject.platform.controller;

import com.dnproject.platform.domain.constant.BoardType;
import com.dnproject.platform.dto.request.BoardCreateRequest;
import com.dnproject.platform.dto.request.CommentCreateRequest;
import com.dnproject.platform.dto.response.ApiResponse;
import com.dnproject.platform.dto.response.BoardResponse;
import com.dnproject.platform.dto.response.CommentResponse;
import com.dnproject.platform.dto.response.PageResponse;
import com.dnproject.platform.service.BoardService;
import com.dnproject.platform.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final com.dnproject.platform.repository.UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BoardResponse>>> getBoards(
            @RequestParam(required = false) BoardType type,
            Pageable pageable) {
        Page<BoardResponse> response = boardService.getBoards(type, pageable);
        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(response)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BoardResponse>> getBoard(@PathVariable Long id) {
        BoardResponse response = boardService.getBoard(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BoardResponse>> createBoard(
            @RequestBody BoardCreateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        BoardResponse response = boardService.createBoard(request, userId);
        return ResponseEntity.ok(ApiResponse.success("게시글이 작성되었습니다.", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BoardResponse>> updateBoard(
            @PathVariable Long id,
            @RequestBody BoardCreateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        BoardResponse response = boardService.updateBoard(id, request, userId);
        return ResponseEntity.ok(ApiResponse.success("게시글이 수정되었습니다.", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        boardService.deleteBoard(id, userId);
        return ResponseEntity.ok(ApiResponse.success("게시글이 삭제되었습니다.", null));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(@PathVariable Long id) {
        List<CommentResponse> response = commentService.getComments(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @PathVariable Long id,
            @RequestBody CommentCreateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        CommentResponse response = commentService.addComment(id, request, userId);
        return ResponseEntity.ok(ApiResponse.success("댓글이 작성되었습니다.", response));
    }

    private Long getUserId(UserDetails userDetails) {
        if (userDetails == null) {
            return 1L;
        }
        return userRepository.findByEmail(userDetails.getUsername())
                .map(user -> user.getId())
                .orElse(1L);
    }
}
