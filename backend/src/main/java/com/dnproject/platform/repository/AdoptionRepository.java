package com.dnproject.platform.repository;

import com.dnproject.platform.domain.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
}
