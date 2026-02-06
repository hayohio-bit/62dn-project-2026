package com.dnproject.platform.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminApplicationResponse {
    private Long id;
    private String type; // ADOPTION, VOLUNTEER
    private String applicantName;
    private String targetName; // Animal Name or Shelter Name
    private String status;
    private LocalDateTime createdAt;
    private String details;
}
