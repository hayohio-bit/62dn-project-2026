package com.dnproject.platform.repository;

import com.dnproject.platform.domain.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
