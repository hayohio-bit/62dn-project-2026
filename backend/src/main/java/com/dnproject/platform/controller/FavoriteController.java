package com.dnproject.platform.controller;

import com.dnproject.platform.dto.response.ApiResponse;
import com.dnproject.platform.dto.response.FavoriteResponse;
import com.dnproject.platform.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final com.dnproject.platform.repository.UserRepository userRepository;

    @PostMapping("/{animalId}")
    public ResponseEntity<ApiResponse<FavoriteResponse>> addFavorite(
            @PathVariable Long animalId,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        FavoriteResponse response = favoriteService.addFavorite(userId, animalId);
        return ResponseEntity.ok(ApiResponse.success("찜 목록에 추가되었습니다.", response));
    }

    @DeleteMapping("/{animalId}")
    public ResponseEntity<ApiResponse<Void>> removeFavorite(
            @PathVariable Long animalId,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        favoriteService.removeFavorite(userId, animalId);
        return ResponseEntity.ok(ApiResponse.success("찜 목록에서 삭제되었습니다.", null));
    }

    @PostMapping("/animal/{animalId}")
    public ResponseEntity<ApiResponse<FavoriteResponse>> toggleFavorite(
            @PathVariable Long animalId,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        FavoriteResponse response = favoriteService.toggleFavorite(userId, animalId);
        String message = response == null ? "찜 목록에서 삭제되었습니다." : "찜 목록에 추가되었습니다.";
        return ResponseEntity.ok(ApiResponse.success(message, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FavoriteResponse>>> getMyFavorites(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        List<FavoriteResponse> responses = favoriteService.getMyFavorites(userId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/ids")
    public ResponseEntity<ApiResponse<List<Long>>> getMyFavoriteIds(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserId(userDetails);
        List<Long> ids = favoriteService.getMyFavoriteAnimalIds(userId);
        return ResponseEntity.ok(ApiResponse.success(ids));
    }

    private Long getUserId(UserDetails userDetails) {
        if (userDetails == null) {
            return 1L;
        }
        return userRepository.findByEmail(userDetails.getUsername())
                .map(user -> user.getId())
                .orElse(1L);
    }
}
