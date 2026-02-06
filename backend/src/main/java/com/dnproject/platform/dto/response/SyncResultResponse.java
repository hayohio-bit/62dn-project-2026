package com.dnproject.platform.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SyncResultResponse {
    private int addedCount;
    private int updatedCount;
    private int syncedCount;
    private int statusCorrectedCount;
    private int days;
    private String species;
    private boolean apiKeyConfigured;

    public static SyncResultResponse of(int addedCount, int updatedCount, int syncedCount, int days, String species) {
        return SyncResultResponse.builder()
                .addedCount(addedCount)
                .updatedCount(updatedCount)
                .syncedCount(syncedCount)
                .statusCorrectedCount(0)
                .days(days)
                .species(species)
                .apiKeyConfigured(true)
                .build();
    }
}
