package com.dnproject.platform.service;

import com.dnproject.platform.domain.Adoption;
import com.dnproject.platform.domain.Animal;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.domain.constant.AdoptionStatus;
import com.dnproject.platform.dto.request.AdoptionRequest;
import com.dnproject.platform.dto.response.AdoptionResponse;
import com.dnproject.platform.repository.AdoptionRepository;
import com.dnproject.platform.repository.AnimalRepository;
import com.dnproject.platform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    @Transactional
    public AdoptionResponse apply(AdoptionRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        Animal animal = animalRepository.findById(request.getAnimalId())
                .orElseThrow(() -> new EntityNotFoundException("동물을 찾을 수 없습니다."));

        Adoption adoption = request.toEntity(user, animal);
        Adoption saved = adoptionRepository.save(adoption);

        return AdoptionResponse.from(saved);
    }

    public Page<AdoptionResponse> getMyList(Long userId, Pageable pageable) {
        return adoptionRepository.findByUserId(userId, pageable)
                .map(AdoptionResponse::from);
    }

    @Transactional
    public AdoptionResponse cancel(Long id, Long userId) {
        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("신청 내역을 찾을 수 없습니다."));

        if (!adoption.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인의 신청 내역만 취소할 수 있습니다.");
        }

        if (adoption.getStatus() != AdoptionStatus.PENDING) {
            throw new IllegalArgumentException("대기 중인 신청만 취소할 수 있습니다.");
        }

        adoption.setStatus(AdoptionStatus.CANCELLED);
        return AdoptionResponse.from(adoption);
    }

    public Page<AdoptionResponse> getByAnimalId(Long animalId, Pageable pageable) {
        return adoptionRepository.findByAnimalId(animalId, pageable)
                .map(AdoptionResponse::from);
    }

    public Page<AdoptionResponse> getPendingByShelter(Long shelterId, Pageable pageable) {
        return adoptionRepository.findByShelterIdAndStatus(shelterId, AdoptionStatus.PENDING, pageable)
                .map(AdoptionResponse::from);
    }

    @Transactional
    public AdoptionResponse approve(Long id) {
        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("신청 내역을 찾을 수 없습니다."));

        adoption.setStatus(AdoptionStatus.APPROVED);
        adoption.setProcessedAt(LocalDateTime.now());

        // 동물의 상태도 변경 (필요 시)
        // adoption.getAnimal().setStatus(Animal.AnimalStatus.ADOPTED);

        return AdoptionResponse.from(adoption);
    }

    @Transactional
    public AdoptionResponse reject(Long id, String rejectReason) {
        Adoption adoption = adoptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("신청 내역을 찾을 수 없습니다."));

        adoption.setStatus(AdoptionStatus.REJECTED);
        adoption.setProcessedAt(LocalDateTime.now());
        // rejectReason 필드가 엔티티에 있다면 설정 가능 (현재는 reason만 있음)

        return AdoptionResponse.from(adoption);
    }
}
