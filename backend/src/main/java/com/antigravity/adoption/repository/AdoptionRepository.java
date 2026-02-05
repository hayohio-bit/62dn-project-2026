package com.antigravity.adoption.repository;

import com.antigravity.adoption.entity.AdoptionRequest;
import com.antigravity.adoption.entity.AdoptionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionRepository extends JpaRepository<AdoptionRequest, Long> {
    Page<AdoptionRequest> findByStatus(AdoptionStatus status, Pageable pageable);

    List<AdoptionRequest> findByApplicantName(String applicantName);
}
