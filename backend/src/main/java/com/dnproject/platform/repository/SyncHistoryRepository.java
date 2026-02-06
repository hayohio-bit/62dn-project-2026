package com.dnproject.platform.repository;

import com.dnproject.platform.domain.SyncHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncHistoryRepository extends JpaRepository<SyncHistory, Long> {
}
