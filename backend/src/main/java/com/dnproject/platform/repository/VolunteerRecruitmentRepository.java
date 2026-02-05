package com.dnproject.platform.repository;

import com.dnproject.platform.domain.VolunteerRecruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;

public interface VolunteerRecruitmentRepository extends JpaRepository<VolunteerRecruitment, Long> {

    // 보호소 별 모집 공고 조회용
    Page<VolunteerRecruitment> findByShelterId(Long shelterId, Pageable pageable);

}
