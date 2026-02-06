package com.dnproject.platform.service;

import com.dnproject.platform.domain.Notification;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.dto.response.NotificationResponse;
import com.dnproject.platform.repository.NotificationRepository;
import com.dnproject.platform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        savedUser = userRepository.save(User.builder()
                .email("notify@test.com")
                .name("알림대상")
                .password("1234")
                .build());
    }

    @Test
    @DisplayName("알림 목록 조회 테스트")
    void getNotificationsTest() {
        // given
        notificationRepository.save(Notification.builder().user(savedUser).type("INFO").message("알림1").build());
        notificationRepository.save(Notification.builder().user(savedUser).type("INFO").message("알림2").build());

        // when
        List<NotificationResponse> responses = notificationService.getNotifications(savedUser.getId());

        // then
        assertThat(responses).hasSize(2);
    }

    @Test
    @DisplayName("알림 읽음 처리 테스트")
    void markAsReadTest() {
        // given
        Notification notification = notificationRepository
                .save(Notification.builder().user(savedUser).type("INFO").message("새 알림").build());

        // when
        notificationService.markAsRead(notification.getId());

        // then
        Notification updated = notificationRepository.findById(notification.getId()).orElseThrow();
        assertThat(updated.isRead()).isTrue();
    }
}
