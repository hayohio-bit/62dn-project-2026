package com.dnproject.platform.dto.request;

import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.VolunteerRecruitment;
import com.dnproject.platform.domain.constant.RecruitmentStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerRecruitmentCreateRequest {

    @NotNull(message = "보호소 ID는 필수입니다.")
    private Long shelterId;

    @NotBlank(message = "공고 제목을 입력해주세요.")
    @Size(max = 200, message = "제목은 200자 이내여야 합니다.")
    private String title;

    @NotBlank(message = "공고 내용을 입력해주세요.")
    private String content;

    @Min(value = 1, message = "모집 인원은 최소 1명 이상이어야 합니다.")
    private int maxApplicants;

    @NotNull(message = "마감일을 선택해주세요.")
    @FutureOrPresent(message = "마감일은 오늘 이후여야 합니다.")
    private LocalDate deadline;

    //dto -> Entity 변환 메서드
    public VolunteerRecruitment toEntity(Shelter shelter) {
        return VolunteerRecruitment.builder()
                .shelter(shelter)
                .title(title)
                .content(content)
                .maxApplicants(maxApplicants)
                .deadline(deadline)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .build();
    }

}
