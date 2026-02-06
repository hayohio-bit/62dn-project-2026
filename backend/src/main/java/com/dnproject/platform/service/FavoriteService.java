package com.dnproject.platform.service;

import com.dnproject.platform.domain.Animal;
import com.dnproject.platform.domain.AnimalFavorite;
import com.dnproject.platform.domain.User;
import com.dnproject.platform.dto.response.FavoriteResponse;
import com.dnproject.platform.repository.AnimalFavoriteRepository;
import com.dnproject.platform.repository.AnimalRepository;
import com.dnproject.platform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteService {

    private final AnimalFavoriteRepository animalFavoriteRepository;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    @Transactional
    public FavoriteResponse addFavorite(Long userId, Long animalId) {
        if (animalFavoriteRepository.findByUserIdAndAnimalId(userId, animalId).isPresent()) {
            throw new IllegalArgumentException("이미 찜한 동물입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new EntityNotFoundException("동물을 찾을 수 없습니다."));

        AnimalFavorite favorite = AnimalFavorite.builder()
                .user(user)
                .animal(animal)
                .build();
        AnimalFavorite saved = animalFavoriteRepository.save(favorite);
        return FavoriteResponse.from(saved);
    }

    @Transactional
    public void removeFavorite(Long userId, Long animalId) {
        AnimalFavorite favorite = animalFavoriteRepository.findByUserIdAndAnimalId(userId, animalId)
                .orElseThrow(() -> new EntityNotFoundException("찜 정보를 찾을 수 없습니다."));
        animalFavoriteRepository.delete(favorite);
    }

    @Transactional
    public FavoriteResponse toggleFavorite(Long userId, Long animalId) {
        Optional<AnimalFavorite> favoriteOpt = animalFavoriteRepository.findByUserIdAndAnimalId(userId, animalId);

        if (favoriteOpt.isPresent()) {
            animalFavoriteRepository.delete(favoriteOpt.get());
            return null;
        }

        return addFavorite(userId, animalId);
    }

    public List<FavoriteResponse> getMyFavorites(Long userId) {
        return animalFavoriteRepository.findByUserId(userId).stream()
                .map(FavoriteResponse::from)
                .toList();
    }

    public List<Long> getMyFavoriteAnimalIds(Long userId) {
        return animalFavoriteRepository.findByUserId(userId).stream()
                .map(favorite -> favorite.getAnimal().getId())
                .toList();
    }
}
