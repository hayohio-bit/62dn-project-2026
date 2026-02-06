package com.dnproject.platform.repository;

import com.dnproject.platform.domain.Animal;
import com.dnproject.platform.domain.constant.AnimalStatus;
import com.dnproject.platform.domain.constant.Size;
import com.dnproject.platform.domain.constant.Species;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

        @Query("SELECT a FROM Animal a " +
                        "WHERE (:status IS NULL OR a.status = :status) " +
                        "AND (:species IS NULL OR a.species = :species) " +
                        "AND (:breed IS NULL OR a.breed LIKE %:breed%)")
        Page<Animal> findAnimals(@Param("status") AnimalStatus status,
                        @Param("species") Species species,
                        @Param("breed") String breed,
                        Pageable pageable);

        Page<Animal> findByShelterId(Long shelterId, Pageable pageable);

        Optional<Animal> findByPublicApiAnimalId(String publicApiAnimalId);

        @Query("SELECT a FROM Animal a " +
                        "WHERE a.status = 'PROTECTED' " +
                        "AND (:species IS NULL OR a.species = :species) " +
                        "AND (:size IS NULL OR a.size = :size) " +
                        "AND (:minAge IS NULL OR a.age >= :minAge) " +
                        "AND (:maxAge IS NULL OR a.age <= :maxAge) " +
                        "ORDER BY a.createdAt DESC")
        Page<Animal> findRecommendedAnimals(
                        @Param("species") Species species,
                        @Param("size") Size size,
                        @Param("minAge") Integer minAge,
                        @Param("maxAge") Integer maxAge,
                        Pageable pageable);
}
