package com.dnproject.platform.domain;

/*
*  공공데이터 수집 일지
*  자동으로 데이터 가져옴, 버튼을 눌러 강제로 가져옴 ->
*  과정 성공? 몇 마리 추가? 왜 실패? 등등 기록해두는 엔티티
* */
import com.dnproject.platform.domain.constant.SyncTriggerType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sync_history", indexes = {
        @Index(name = "idx_sync_history_run_at", columnList = "created_at")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SyncHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SyncTriggerType triggerType;

    private Integer totalCount;
    private Integer successCount;
    private Integer addedCount;
    private Integer updatedCount;
    private Integer deletedCount;
    private Integer correctedCount;

    private String errorMessage;
    private Integer daysParam;
    private String speciesFilter;

    private String status;
}
