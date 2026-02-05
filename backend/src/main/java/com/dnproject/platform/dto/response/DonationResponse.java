package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.Donation;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class DonationResponse {
    private Long id;
    private String donorName;
    private String shelterName;
    private String donationType;
    private BigDecimal amount;
    private String itemName;
    private Integer quantity;
    private String status;
    private LocalDateTime createdAt;

    // Entity -> dto 변환 메서드
    public static DonationResponse from(Donation donation) {
        return DonationResponse.builder()
                .id(donation.getId())
                .donorName(donation.getDonorName())
                .shelterName(donation.getShelter().getName())
                .donationType(donation.getDonationType().getDescription())
                .amount(donation.getAmount())
                .itemName(donation.getItemName())
                .quantity(donation.getQuantity())
                .status(donation.getStatus().getDescription())
                .createdAt(donation.getCreatedAt())
                .build();
    }
}
