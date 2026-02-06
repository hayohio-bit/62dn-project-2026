package com.dnproject.platform.controller;

import com.dnproject.platform.dto.request.CommentCreateRequest;
import com.dnproject.platform.dto.response.ApiResponse;
import com.dnproject.platform.dto.response.CommentResponse;
import com.dnproject.platform.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final com.dnproject.platform.repository.UserRepository userRepository;

    @PostMapping("/board/{boardId}")
    public ResponseEntity<ApiResponse<CommentResponse>> addComment(
            @PathVariable Long boardId,
            @RequestBody CommentCreateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        CommentResponse response = commentService.addComment(boardId, request, userId);
        return ResponseEntity.ok(ApiResponse.success("댓글이 추가되었습니다.", response));
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(@PathVariable Long boardId) {
        List<CommentResponse> responses = commentService.getComments(boardId);
        return ResponseEntity.ok(ApiResponse.success(responses));
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
