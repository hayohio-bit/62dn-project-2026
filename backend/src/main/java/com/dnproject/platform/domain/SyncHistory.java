package com.dnproject.platform.domain;

import com.dnproject.platform.domain.constant.SyncTriggerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyncHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SyncTriggerType triggerType;

    private LocalDateTime syncStartTime;
    private LocalDateTime syncEndTime;

    private Integer totalCount;
    private Integer successCount;

    private String status;

}
