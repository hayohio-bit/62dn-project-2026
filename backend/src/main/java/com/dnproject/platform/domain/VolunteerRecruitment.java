package com.dnproject.platform.domain;

import com.dnproject.platform.domain.constant.RecruitmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "volunteer_recruitments")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class VolunteerRecruitment extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id", nullable = false)
    private Shelter shelter;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "max_applicants", nullable = false)
    private int maxApplicants;  // 모집 정원

    @Column(nullable = false)
    private LocalDate deadline; // 마감일

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private RecruitmentStatus recruitmentStatus = RecruitmentStatus.RECRUITING;

    @OneToMany(mappedBy = "volunteerRecruitment")
    private List<Volunteer> volunteers = new ArrayList<>();
}
