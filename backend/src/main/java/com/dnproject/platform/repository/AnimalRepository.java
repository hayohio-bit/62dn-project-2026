package com.dnproject.platform.repository;

import com.dnproject.platform.domain.Animal;
import com.dnproject.platform.domain.constant.AnimalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query("SELECT a FROM Animal a " +
            "WHERE (:status IS NULL OR a.status = :status) " +
            "AND (:species IS NULL OR a.species = :species) " +
            "AND (:breed IS NULL OR a.breed LIKE %:breed%)")
    Page<Animal> findAnimals(@Param("status") AnimalStatus status,
            @Param("species") String species,
            @Param("breed") String breed,
            Pageable pageable);

    Page<Animal> findByShelterId(Long shelterId, Pageable pageable);
}
