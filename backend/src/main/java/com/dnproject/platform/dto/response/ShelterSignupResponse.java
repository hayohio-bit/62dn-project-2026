package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ShelterSignupResponse {
    private Long shelterId;
    private String shelterName;
    private String status;
    private Long userId;
    private String email;
    private String role;
    private LocalDateTime createdAt;

    public static ShelterSignupResponse from(Shelter shelter) {
        return ShelterSignupResponse.builder()
                .shelterId(shelter.getId())
                .shelterName(shelter.getName())
                .status(shelter.getVerificationStatus().getDescription())
                .createdAt(shelter.getCreatedAt())
                .build();
    }

    public static ShelterSignupResponse from(Shelter shelter, User user) {
        return ShelterSignupResponse.builder()
                .shelterId(shelter.getId())
                .shelterName(shelter.getName())
                .status(shelter.getVerificationStatus().getDescription())
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .createdAt(shelter.getCreatedAt())
                .build();
    }
}
