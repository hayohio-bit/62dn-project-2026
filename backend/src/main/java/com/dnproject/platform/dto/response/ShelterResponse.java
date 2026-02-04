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
public class ShelterResponse {
    private long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String managerName;
    private String status;
    private LocalDateTime verifiedAt;

    public static ShelterResponse from(Shelter shelter) {
        return ShelterResponse.builder()
                .id(shelter.getId())
                .name(shelter.getName())
                .address(shelter.getAddress())
                .phone(shelter.getPhone())
                .email(shelter.getEmail())
                .managerName(shelter.getManagerName())
                .status(shelter.getVerificationStatus().getDescription())
                .verifiedAt(shelter.getVerifiedAt())
                .build();
    }
}
