package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.constant.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ShelterSignupResponse {
    private Long id;
    private String name;
    private VerificationStatus status;
    private LocalDateTime createdAt;

    public static ShelterSignupResponse from(Shelter shelter) {
        return ShelterSignupResponse.builder()
                .id(shelter.getId())
                .name(shelter.getName())
                .status(shelter.getVerificationStatus())
                .createdAt(shelter.getCreatedAt())
                .build();
    }
}
