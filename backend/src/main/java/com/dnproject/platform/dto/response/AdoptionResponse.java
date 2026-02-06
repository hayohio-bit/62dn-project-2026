package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.Adoption;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdoptionResponse {
    private Long id;
    private String userName;
    private String animalName;
    private String typeDescription;
    private String status;
    private String statusDescription;
    private String reason;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;

    public static AdoptionResponse from(Adoption adoption) {
        return AdoptionResponse.builder()
                .id(adoption.getId())
                .userName(adoption.getUser().getName())
                .animalName(adoption.getAnimal().getName())
                .typeDescription(adoption.getType().getDescription())
                .status(adoption.getStatus().name())
                .statusDescription(adoption.getStatus().getDescription())
                .createdAt(adoption.getCreatedAt())
                .build();
    }
}
