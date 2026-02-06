package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.Shelter;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminShelterResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String managerName;
    private String businessRegistrationNumber;
    private String verificationStatus;
    private LocalDateTime verifiedAt;

    public static AdminShelterResponse from(Shelter shelter) {
        return AdminShelterResponse.builder()
                .id(shelter.getId())
                .name(shelter.getName())
                .address(shelter.getAddress())
                .phone(shelter.getPhone())
                .managerName(shelter.getManagerName())
                .businessRegistrationNumber(shelter.getBusinessRegistrationNumber())
                .verificationStatus(shelter.getVerificationStatus().name())
                .verifiedAt(shelter.getVerifiedAt())
                .build();
    }
}
