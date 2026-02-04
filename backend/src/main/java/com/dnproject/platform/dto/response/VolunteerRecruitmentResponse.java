package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.VolunteerRecruitment;
import com.dnproject.platform.domain.constant.RecruitmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerRecruitmentResponse {

    private Long id;
    private Long shelterId;
    private String shelterName;
    private String title;
    private String content;
    private int maxApplicants;
    private int currenApplicants;
    private LocalDate deadline;
    private String recruitmentStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Entity -> dto 변환 메서드
    public static VolunteerRecruitmentResponse from(VolunteerRecruitment recruitment) {
        return VolunteerRecruitmentResponse.builder()
                .id(recruitment.getId())
                .shelterId(recruitment.getShelter().getId())
                .shelterName(recruitment.getShelter().getName())
                .title(recruitment.getTitle())
                .content(recruitment.getContent())
                .maxApplicants(recruitment.getMaxApplicants())
                .currenApplicants(recruitment.getVolunteers() != null ? recruitment.getVolunteers().size() : 0)
                .deadline(recruitment.getDeadline())
                .recruitmentStatus(recruitment.getRecruitmentStatus().getDescription())
                .createdAt(recruitment.getCreatedAt())
                .updatedAt(recruitment.getUpdatedAt())
                .build();
    }
}
