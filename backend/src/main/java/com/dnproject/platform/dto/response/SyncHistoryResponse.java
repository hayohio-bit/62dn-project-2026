package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.SyncHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SyncHistoryResponse {
    private Long id;
    private String runAt;
    private String triggerType;
    private Integer addedCount;
    private Integer updatedCount;
    private Integer deletedCount;
    private Integer correctedCount;
    private String errorMessage;
    private Integer daysParam;
    private String speciesFilter;

    public static SyncHistoryResponse from(SyncHistory history) {
        return SyncHistoryResponse.builder()
                .id(history.getId())
                .runAt(history.getCreatedAt().toString())
                .triggerType(history.getTriggerType().name())
                .addedCount(history.getAddedCount())
                .updatedCount(history.getUpdatedCount())
                .deletedCount(history.getDeletedCount())
                .correctedCount(history.getCorrectedCount())
                .errorMessage(history.getErrorMessage())
                .daysParam(history.getDaysParam())
                .speciesFilter(history.getSpeciesFilter())
                .build();
    }
}
