package com.dnproject.platform.service;

import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.constant.VerificationStatus;
import com.dnproject.platform.dto.response.AdminShelterResponse;
import com.dnproject.platform.repository.ShelterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminShelterService {

    private final ShelterRepository shelterRepository;

    public Page<AdminShelterResponse> getVerificationRequests(Pageable pageable) {
        return shelterRepository.findByVerificationStatus(VerificationStatus.PENDING, pageable)
                .map(AdminShelterResponse::from);
    }

    public Page<AdminShelterResponse> getAllShelters(Pageable pageable) {
        return shelterRepository.findAll(pageable)
                .map(AdminShelterResponse::from);
    }

    @Transactional
    public void verifyShelter(Long shelterId, VerificationStatus status) {
        Shelter shelter = shelterRepository.findById(shelterId)
                .orElseThrow(() -> new EntityNotFoundException("보호소를 찾을 수 없습니다."));
        shelter.updateVerificationStatus(status);
    }
}
