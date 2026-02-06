package com.dnproject.platform.repository;

import com.dnproject.platform.domain.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    boolean existsByBusinessRegistrationNumber(String businessRegistrationNumber);

    boolean existsByEmail(String email);

    Optional<Shelter> findByPublicApiShelterId(String publicApiShelterId);

    org.springframework.data.domain.Page<Shelter> findByVerificationStatus(
            com.dnproject.platform.domain.constant.VerificationStatus status,
            org.springframework.data.domain.Pageable pageable);

    Optional<Shelter> findByManagerId(Long managerId);
}
