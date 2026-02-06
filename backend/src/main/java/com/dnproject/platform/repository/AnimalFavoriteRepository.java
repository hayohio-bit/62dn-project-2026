package com.dnproject.platform.repository;

import com.dnproject.platform.domain.AnimalFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AnimalFavoriteRepository extends JpaRepository<AnimalFavorite, Long> {
    List<AnimalFavorite> findByUserId(Long userId);

    Optional<AnimalFavorite> findByUserIdAndAnimalId(Long userId, Long animalId);

    boolean existsByUserIdAndAnimalId(Long userId, Long animalId);
}
