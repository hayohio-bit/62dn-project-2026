package com.dnproject.platform.controller;

import com.dnproject.platform.dto.response.ApiResponse;
import com.dnproject.platform.dto.response.PageResponse;
import com.dnproject.platform.dto.response.SyncHistoryResponse;
import com.dnproject.platform.dto.response.SyncResultResponse;
import com.dnproject.platform.service.AnimalSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/animals")
@RequiredArgsConstructor
public class AdminAnimalController {

    private final AnimalSyncService animalSyncService;

    @PostMapping("/sync-from-public-api")
    public ResponseEntity<ApiResponse<SyncResultResponse>> syncAnimals(
            @RequestParam(name = "days", required = false, defaultValue = "7") Integer days,
            @RequestParam(name = "maxPages", required = false, defaultValue = "1") Integer maxPages,
            @RequestParam(name = "species", required = false) String species) {
        SyncResultResponse result = animalSyncService.syncFromPublicApi(days, maxPages, species);
        return ResponseEntity.ok(ApiResponse.success("동물 데이터 동기화가 완료되었습니다.", result));
    }

    @GetMapping("/sync-history")
    public ResponseEntity<ApiResponse<PageResponse<SyncHistoryResponse>>> getSyncHistory(Pageable pageable) {
        Page<SyncHistoryResponse> response = animalSyncService.getSyncHistory(pageable);
        return ResponseEntity.ok(ApiResponse.success(PageResponse.from(response)));
    }
}
