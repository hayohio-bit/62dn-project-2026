package com.dnproject.platform.controller;

import com.dnproject.platform.dto.response.ApiResponse;
import com.dnproject.platform.dto.response.NotificationResponse;
import com.dnproject.platform.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final com.dnproject.platform.repository.UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getNotifications(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        List<NotificationResponse> responses = notificationService.getNotifications(userId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getUnreadCount(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        long count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("count", count)));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success("알림을 읽음 처리했습니다.", null));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markAsReadPatch(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success("알림을 읽음 처리했습니다.", null));
    }

    @PutMapping("/read-all")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok(ApiResponse.success("모든 알림을 읽음 처리했습니다.", null));
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
