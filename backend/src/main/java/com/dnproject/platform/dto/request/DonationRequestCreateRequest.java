package com.dnproject.platform.dto.request;

import com.dnproject.platform.domain.DonationRequest;
import com.dnproject.platform.domain.Shelter;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationRequestCreateRequest {
    @NotNull(message = "보호소 ID는 필수입니다.")
    private Long shelterId;

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자 이내로 작성해주세요.")
    private String title;

    @NotBlank(message = "상세 내용은 필수입니다.")
    private String content;

    @NotBlank(message = "물품 카테고리는 필수입니다.")
    private String itemCategory;

    @NotNull(message = "목표 수량은 필수입니다.")
    @Min(value = 1, message = "목표 수량은 1개 이상이어야 합니다.")
    private Integer targetQuantity;

    @NotNull(message = "마감일은 필수입니다.")
    @Future(message = "마감일은 오늘 이후의 날짜여야 합니다.")
    private LocalDate deadline;

    // dto -> Entity 변환 메서드
    public DonationRequest toEntity(Shelter shelter) {
        return DonationRequest.builder()
                .shelter(shelter)
                .title(title)
                .content(content)
                .itemCategory(itemCategory)
                .targetQuantity(targetQuantity)
                .deadline(deadline)
                .build();
    }
}
