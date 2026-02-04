package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.DonationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class DonationRequestResponse {
    private Long id;
    private String shelterName;
    private String title;
    private String content;
    private String itemCategory;
    private Integer targetQuantity;
    private Integer currentQuantity;
    private LocalDate deadline;
    private String status;

    // Entity -> dto 변환 메서드
    public static DonationRequestResponse from(DonationRequest request) {
        return DonationRequestResponse.builder()
                .id(request.getId())
                .shelterName(request.getShelter().getName())
                .title(request.getTitle())
                .content(request.getContent())
                .itemCategory(request.getItemCategory())
                .targetQuantity(request.getTargetQuantity())
                .currentQuantity(request.getCurrentQuantity())
                .deadline(request.getDeadline())
                .status(request.getStatus().getDescription())
                .build();
    }
}
