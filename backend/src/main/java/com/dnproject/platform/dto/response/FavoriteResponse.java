package com.dnproject.platform.dto.response;


import com.dnproject.platform.domain.Favorite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class FavoriteResponse{
    private Long id;
    private Long userId;
    private Long animalId;
    private String animalName;
    private LocalDateTime createdAt;

    // Mapping
    public static FavoriteResponse from(Favorite favorite) {
        return FavoriteResponse.builder()
                .id(favorite.getId())
                .userId(favorite.getUser().getId())
                .animalId(favorite.getAnimal().getId())
                .animalName(favorite.getAnimal().getName())
                .createdAt(favorite.getCreatedAt())
                .build();
    }
}
