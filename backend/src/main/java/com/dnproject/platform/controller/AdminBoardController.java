package com.dnproject.platform.controller;

import com.dnproject.platform.dto.response.ApiResponse;
import com.dnproject.platform.dto.response.BoardResponse;
import com.dnproject.platform.dto.response.CommentResponse;
import com.dnproject.platform.dto.response.PageResponse;
import com.dnproject.platform.service.AdminBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/boards")
@RequiredArgsConstructor
public class AdminBoardController {

    private final AdminBoardService adminBoardService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BoardResponse>>> getAllBoards(Pageable pageable) {
        Page<BoardResponse> response = adminBoardService.getBoards(pageable);
        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(response)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(@PathVariable Long id) {
        adminBoardService.deleteBoard(id);
        return ResponseEntity.ok(ApiResponse.success("게시글이 삭제되었습니다.", null));
    }

    @GetMapping("/comments")
    public ResponseEntity<ApiResponse<PageResponse<CommentResponse>>> getAllComments(Pageable pageable) {
        Page<CommentResponse> response = adminBoardService.getComments(pageable);
        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(response)));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long id) {
        adminBoardService.deleteComment(id);
        return ResponseEntity.ok(ApiResponse.success("댓글이 삭제되었습니다.", null));
    }
}
