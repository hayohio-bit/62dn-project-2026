package com.dnproject.platform.repository;

import com.dnproject.platform.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findAllByUserId(Long userId);
    List<Donation> findAllByShelterId(Long shelterId);
}
