package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.Notification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationResponse {
    private Long id;
    private String type;
    private String message;
    private boolean isRead;
    private String relatedUrl;
    private LocalDateTime createdAt;

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
