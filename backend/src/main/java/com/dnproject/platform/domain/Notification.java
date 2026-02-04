package com.dnproject.platform.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(nullable = false, length = 255)
    private String message;

    private boolean isRead = false;

    @Column(length = 255)
    private String relatedUrl;

    @Builder
    public Notification(User user, String type, String message, String relatedUrl) {
        this.user = user;
        this.type = type;
        this.message = message;
        this.relatedUrl = relatedUrl;
    }

    public void markAsRead() {
        this.isRead = true;
    }
}
