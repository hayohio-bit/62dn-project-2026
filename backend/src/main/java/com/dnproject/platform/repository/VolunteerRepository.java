package com.dnproject.platform.repository;

import com.dnproject.platform.domain.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    Page<Volunteer> findByShelterId(Long shelterId, Pageable pageable);

    Page<Volunteer> findByUserId(Long userId, Pageable pageable);

    long countByVolunteerStatus(com.dnproject.platform.domain.constant.VolunteerStatus status);
}
