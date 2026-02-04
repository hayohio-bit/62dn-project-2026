package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private Long id;
    private String type;
    private String message;
    private boolean isRead;
    private String relatedUrl;
    private LocalDateTime createdAt;

    // Mapping
    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .type(notification.getType())
                .message(notification.getMessage())
                .isRead(notification.isRead())
                .relatedUrl(notification.getRelatedUrl())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
