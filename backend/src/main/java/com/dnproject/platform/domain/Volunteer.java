package com.dnproject.platform.domain;

import com.dnproject.platform.domain.constant.ActivityCycle;
import com.dnproject.platform.domain.constant.VolunteerStatus;
import com.dnproject.platform.domain.constant.VolunteerType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "volunteers")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Volunteer extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  //신청자 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id", nullable = false)
    private Shelter shelter;    //보호소 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private VolunteerRecruitment volunteerRecruitment;  //VolunteerRecruitment 엔티티 작업할 때 확인

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;                                // Board 엔티티 작업할때 확인

    @Column(name = "applicant_name", nullable = false, length = 50)
    private String applicantName;

    @Column(name = "applicant_phone", nullable = false, length = 20)
    private String applicantPhone;

    @Column(name = "applicant_email", nullable = false, length = 100)
    private String applicantEmail;

    @Column(name = "activity_region", nullable = false, length = 100)
    private String activityRegion;

    @Column(name = "activity_field", nullable = false, length = 50)
    private String activityField;

    @Column(nullable = false)
    private LocalDate volunteerDateStart;

    private LocalDate volunteerDateEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_cycle", nullable = false, length = 20)
    private ActivityCycle activityCycle;    // 활동 주기

    @Column(name = "preferred_time_slot", length = 50)
    private String preferredTimeSlot;   // 희망 시간대

    @Enumerated(EnumType.STRING)
    @Column(name = "volunteer_type", nullable = false, length = 20)
    @Builder.Default
    private VolunteerType volunteerType = VolunteerType.INDIVIDUAL; // 신청 유형

    @Column(columnDefinition = "TEXT")
    private String experience;  // 봉사 경험

    @Column(name = "special_notes", columnDefinition = "TEXT")
    private String specialNotes;    // 특이 사항

    @Column(name = "participant_count")
    private Integer participantCount;   // 단체 인원 수

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private VolunteerStatus volunteerStatus = VolunteerStatus.PENDING;  // 신청 상태


}
