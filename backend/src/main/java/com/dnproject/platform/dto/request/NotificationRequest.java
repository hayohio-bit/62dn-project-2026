package com.dnproject.platform.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class NotificationRequest {

    @NotNull(message = "수신자 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "알림 타입은 필수입니다.")
    private String type;

    @NotBlank(message = "알림 내용은 필수입니다.")
    private String message;

    private String relatedUrl;

}
