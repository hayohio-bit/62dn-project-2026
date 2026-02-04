package com.dnproject.platform.repository;

import com.dnproject.platform.domain.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    boolean existsByBusinessRegistrationNumber(String businessRegistrationNumber);

    boolean existsByEmail(String email);
}
