package com.dnproject.platform.service;

import com.dnproject.platform.domain.Adoption;
import com.dnproject.platform.domain.Volunteer;
import com.dnproject.platform.domain.constant.AdoptionStatus;
import com.dnproject.platform.domain.constant.VolunteerStatus;
import com.dnproject.platform.dto.response.AdminApplicationResponse;
import com.dnproject.platform.repository.AdoptionRepository;
import com.dnproject.platform.repository.VolunteerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminApplicationService {

    private final AdoptionRepository adoptionRepository;
    private final VolunteerRepository volunteerRepository;

    public Page<AdminApplicationResponse> getAdoptionApplications(Pageable pageable) {
        return adoptionRepository.findAll(pageable)
                .map(adoption -> AdminApplicationResponse.builder()
                        .id(adoption.getId())
                        .type("ADOPTION")
                        .applicantName(adoption.getUser().getName())
                        .targetName(adoption.getAnimal().getName())
                        .status(adoption.getStatus().name())
                        .createdAt(adoption.getCreatedAt())
                        .details(adoption.getReason())
                        .build());
    }

    public Page<AdminApplicationResponse> getVolunteerApplications(Pageable pageable) {
        return volunteerRepository.findAll(pageable)
                .map(volunteer -> AdminApplicationResponse.builder()
                        .id(volunteer.getId())
                        .type("VOLUNTEER")
                        .applicantName(volunteer.getApplicantName())
                        .targetName(volunteer.getShelter().getName())
                        .status(volunteer.getVolunteerStatus().name())
                        .createdAt(volunteer.getCreatedAt())
                        .details(volunteer.getSpecialNotes())
                        .build());
    }

    @Transactional
    public void updateAdoptionStatus(Long applicationId, AdoptionStatus status) {
        Adoption adoption = adoptionRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("입양 신청을 찾을 수 없습니다."));
        adoption.setStatus(status);
        adoption.setProcessedAt(LocalDateTime.now());
    }

    @Transactional
    public void updateVolunteerStatus(Long applicationId, VolunteerStatus status) {
        Volunteer volunteer = volunteerRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("봉사 신청을 찾을 수 없습니다."));
        volunteer.updateStatus(status);
    }

    public Map<String, Object> getApplicationStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalAdoption", adoptionRepository.count());
        stats.put("pendingAdoption", adoptionRepository.countByStatus(AdoptionStatus.PENDING));
        stats.put("totalVolunteer", volunteerRepository.count());
        stats.put("pendingVolunteer", volunteerRepository.countByVolunteerStatus(VolunteerStatus.PENDING));
        return stats;
    }
}
