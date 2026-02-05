package com.antigravity.adoption.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptionApplyRequestDTO {

    @NotNull(message = "동물 ID는 필수입니다.")
    private Long animalId;

    @NotBlank(message = "신청자 이름은 필수입니다.")
    private String applicantName;

    @NotBlank(message = "연락처는 필수입니다.")
    private String applicantContact;

    private String reason;
}
