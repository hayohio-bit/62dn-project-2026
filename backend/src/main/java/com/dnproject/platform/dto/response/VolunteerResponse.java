package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.Volunteer;
import com.dnproject.platform.domain.constant.ActivityCycle;
import com.dnproject.platform.domain.constant.VolunteerStatus;
import com.dnproject.platform.domain.constant.VolunteerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerResponse {

    private Long id;
    private Long userId;
    private String userEmail;
    private Long shelterId;
    private String shelterName;

    private String applicantName;
    private String applicantPhone;
    private String applicantEmail;

    private String activityRegion;
    private String activityField;
    private LocalDate volunteerDateStart;
    private LocalDate volunteerDateEnd;
    private ActivityCycle activityCycle;
    private String preferredTimeSlot;
    private VolunteerType volunteerType;
    private Integer participantCount;

    private String experience;
    private String specialNotes;
    private VolunteerStatus volunteerStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Entity -> Response dto 변환 메서드
    public static VolunteerResponse from(Volunteer volunteer) {
        return VolunteerResponse.builder()
                .id(volunteer.getId())
                .userId(volunteer.getUser().getId())
                .shelterId(volunteer.getShelter().getId())
                .shelterName(volunteer.getShelter().getName())
                .applicantName(volunteer.getApplicantName())
                .applicantPhone(volunteer.getApplicantPhone())
                .applicantEmail(volunteer.getApplicantEmail())
                .activityRegion(volunteer.getActivityRegion())
                .activityCycle(volunteer.getActivityCycle())
                .activityField(volunteer.getActivityField())
                .volunteerDateStart(volunteer.getVolunteerDateStart())
                .volunteerDateEnd(volunteer.getVolunteerDateEnd())
                .preferredTimeSlot(volunteer.getPreferredTimeSlot())
                .volunteerType(volunteer.getVolunteerType())
                .participantCount(volunteer.getParticipantCount())
                .experience(volunteer.getExperience())
                .specialNotes(volunteer.getSpecialNotes())
                .volunteerStatus(volunteer.getVolunteerStatus())
                .createdAt(volunteer.getCreatedAt())
                .updatedAt(volunteer.getUpdatedAt())
                .build();
    }

}
