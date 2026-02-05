package com.antigravity.animal.repository;

import com.antigravity.animal.entity.Animal;
import com.antigravity.animal.entity.AnimalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    Page<Animal> findByStatus(AnimalStatus status, Pageable pageable);
}
