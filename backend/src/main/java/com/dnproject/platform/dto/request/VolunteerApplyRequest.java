package com.dnproject.platform.dto.request;

import com.dnproject.platform.domain.*;
import com.dnproject.platform.domain.constant.ActivityCycle;
import com.dnproject.platform.domain.constant.VolunteerStatus;
import com.dnproject.platform.domain.constant.VolunteerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerApplyRequest {

    @NotNull(message = "보호소 ID는 필수입니다.")
    private Long shelterId;

    private Long recruitmentId;

    private Long boardId;

    @NotBlank(message = "신청자 이름은 필수입니다.")
    private String applicantName;

    @NotBlank(message = "연락처는 필수입니다.")
    private String applicantPhone;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String applicantEmail;

    @NotBlank(message = "활동 희망 지역을 입력해주세요.")
    private String activityRegion;

    @NotBlank(message = "봉사 분야를 선택해주세요.")
    private String activityField;

    @NotNull(message = "시작일은 필수입니다.")
    private LocalDate volunteerDateStart;

    private LocalDate volunteerDateEnd;

    @NotNull(message = "활동 주기를 선택해주세요.")
    private ActivityCycle activityCycle;

    private String preferredTimeSlot;

    @NotNull(message = "신청 유형을 선택해주세요.")
    private VolunteerType volunteerType;

    private String experience;

    private String specialNotes;

    @Min(value = 1, message = "참여 인원은 최소 1명 이상이어야 합니다.")
    private Integer participantCount;

    // dto -> Entity 변환 메서드
    public Volunteer toEntity(User user, Shelter shelter,
                              VolunteerRecruitment recruitment,
                              Board board) {
        return Volunteer.builder()
                .user(user)
                .shelter(shelter)
                .volunteerRecruitment(recruitment)
                .board(board)
                .applicantName(applicantName)
                .applicantPhone(applicantPhone)
                .applicantEmail(applicantEmail)
                .activityRegion(activityRegion)
                .activityField(activityField)
                .activityCycle(activityCycle)
                .volunteerDateStart(volunteerDateStart)
                .volunteerDateEnd(volunteerDateEnd)
                .preferredTimeSlot(preferredTimeSlot)
                .volunteerType(volunteerType)
                .experience(experience)
                .specialNotes(specialNotes)
                .participantCount(participantCount)
                .volunteerStatus(VolunteerStatus.PENDING)
                .build();
    }
}
