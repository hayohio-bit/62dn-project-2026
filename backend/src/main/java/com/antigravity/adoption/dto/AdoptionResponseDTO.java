package com.antigravity.adoption.dto;

import com.antigravity.adoption.entity.AdoptionRequest;
import com.antigravity.adoption.entity.AdoptionStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptionResponseDTO {
    private Long id;
    private Long animalId;
    private String animalName;
    private String applicantName;
    private String applicantContact;
    private String reason;
    private AdoptionStatus status;
    private LocalDateTime processedAt;
    private LocalDateTime createdAt;

    public static AdoptionResponseDTO fromEntity(AdoptionRequest request) {
        return AdoptionResponseDTO.builder()
                .id(request.getId())
                .animalId(request.getAnimal().getId())
                .animalName(request.getAnimal().getName())
                .applicantName(request.getApplicantName())
                .applicantContact(request.getApplicantContact())
                .reason(request.getReason())
                .status(request.getStatus())
                .processedAt(request.getProcessedAt())
                .createdAt(request.getCreatedAt())
                .build();
    }
}
