package com.dnproject.platform.dto.response;

import com.dnproject.platform.domain.AnimalFavorite;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoriteResponse {
    private Long id;
    private Long animalId;
    private String animalName;
    private String animalImageUrl;

    public static FavoriteResponse from(AnimalFavorite favorite) {
        return FavoriteResponse.builder()
                .id(favorite.getId())
                .animalId(favorite.getAnimal().getId())
                .animalName(favorite.getAnimal().getName())
                .animalImageUrl(favorite.getAnimal().getImageUrl())
                .build();
    }
}
