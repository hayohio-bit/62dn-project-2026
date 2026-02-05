package com.dnproject.platform.repository;

import com.dnproject.platform.domain.Adoption;
import com.dnproject.platform.domain.constant.AdoptionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {

    Page<Adoption> findByUserId(Long userId, Pageable pageable);

    Page<Adoption> findByAnimalId(Long animalId, Pageable pageable);

    @Query("SELECT a FROM Adoption a JOIN a.animal an WHERE an.shelter.id = :shelterId AND a.status = :status")
    Page<Adoption> findByShelterIdAndStatus(@Param("shelterId") Long shelterId,
            @Param("status") AdoptionStatus status,
            Pageable pageable);
}
