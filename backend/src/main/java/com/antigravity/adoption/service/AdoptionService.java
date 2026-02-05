package com.antigravity.adoption.service;

import com.antigravity.adoption.dto.AdoptionApplyRequestDTO;
import com.antigravity.adoption.dto.AdoptionResponseDTO;
import com.antigravity.adoption.entity.AdoptionRequest;
import com.antigravity.adoption.entity.AdoptionStatus;
import com.antigravity.adoption.repository.AdoptionRepository;
import com.antigravity.animal.entity.Animal;
import com.antigravity.animal.entity.AnimalStatus;
import com.antigravity.animal.repository.AnimalRepository;
import com.antigravity.global.exception.BusinessException;
import com.antigravity.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;
    private final AnimalRepository animalRepository;

    @Transactional
    public AdoptionResponseDTO apply(AdoptionApplyRequestDTO requestDTO) {
        Animal animal = animalRepository.findById(requestDTO.getAnimalId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ANIMAL_NOT_FOUND));

        if (animal.getStatus() != AnimalStatus.AVAILABLE) {
            throw new BusinessException(ErrorCode.ADOPTION_REQUEST_INVALID_STATUS);
        }

        // 신청 시 상태를 PENDING_ADOPTION으로 변경하여 다른 신청을 방지할 수 있음 (정책에 따라 다름)
        // 여기서는 승인 시점에만 ADOPTED로 변경하는 요구사항을 따르되,
        // 신청이 들어오면 PENDING으로 관리하는 것이 일반적임.
        animal.updateStatus(AnimalStatus.PENDING_ADOPTION);

        AdoptionRequest request = AdoptionRequest.builder()
                .animal(animal)
                .applicantName(requestDTO.getApplicantName())
                .applicantContact(requestDTO.getApplicantContact())
                .reason(requestDTO.getReason())
                .status(AdoptionStatus.REQUESTED)
                .build();

        AdoptionRequest savedRequest = adoptionRepository.save(request);
        return AdoptionResponseDTO.fromEntity(savedRequest);
    }

    public Page<AdoptionResponseDTO> getRequests(AdoptionStatus status, Pageable pageable) {
        return adoptionRepository.findByStatus(status, pageable)
                .map(AdoptionResponseDTO::fromEntity);
    }

    @Transactional
    public AdoptionResponseDTO approve(Long requestId) {
        AdoptionRequest request = adoptionRepository.findById(requestId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ADOPTION_REQUEST_NOT_FOUND));

        if (request.getStatus() != AdoptionStatus.REQUESTED) {
            throw new BusinessException("이미 처리된 요청입니다.", ErrorCode.ADOPTION_REQUEST_INVALID_STATUS);
        }

        request.approve();
        request.getAnimal().updateStatus(AnimalStatus.ADOPTED);

        return AdoptionResponseDTO.fromEntity(request);
    }

    @Transactional
    public AdoptionResponseDTO reject(Long requestId) {
        AdoptionRequest request = adoptionRepository.findById(requestId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ADOPTION_REQUEST_NOT_FOUND));

        request.reject();
        // 거절 시 다시 입양 가능 상태로 변경
        request.getAnimal().updateStatus(AnimalStatus.AVAILABLE);

        return AdoptionResponseDTO.fromEntity(request);
    }

    @Transactional
    public void cancel(Long requestId) {
        AdoptionRequest request = adoptionRepository.findById(requestId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ADOPTION_REQUEST_NOT_FOUND));

        request.cancel();
        request.getAnimal().updateStatus(AnimalStatus.AVAILABLE);
    }
}
