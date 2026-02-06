package com.dnproject.platform.service;

import com.dnproject.platform.domain.Animal;
import com.dnproject.platform.domain.Preference;
import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.constant.AnimalStatus;
import com.dnproject.platform.domain.constant.Size;
import com.dnproject.platform.domain.constant.Species;
import com.dnproject.platform.dto.request.AnimalCreateRequest;
import com.dnproject.platform.dto.response.AnimalResponse;
import com.dnproject.platform.repository.AnimalRepository;
import com.dnproject.platform.repository.PreferenceRepository;
import com.dnproject.platform.repository.ShelterRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final ShelterRepository shelterRepository;
    private final PreferenceRepository preferenceRepository;

    public Page<AnimalResponse> getAnimals(AnimalStatus status, String speciesStr, String sizeStr, String region,
            String sigungu, String search, Pageable pageable) {
        Species species = null;
        if (speciesStr != null && !speciesStr.isEmpty()) {
            try {
                species = Species.valueOf(speciesStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Ignore
            }
        }

        Size size = null;
        if (sizeStr != null && !sizeStr.isEmpty()) {
            try {
                size = Size.valueOf(sizeStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Ignore
            }
        }

        if (pageable.getSort().getOrderFor("random") != null) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by("createdAt").descending());
        }

        return animalRepository.findAnimals(status, species, size, region, sigungu, search, pageable)
                .map(AnimalResponse::from);
    }

    public AnimalResponse getAnimal(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("동물을 찾을 수 없습니다. ID: " + id));
        return AnimalResponse.from(animal);
    }

    @Transactional
    public AnimalResponse createAnimal(AnimalCreateRequest request) {
        Shelter shelter = shelterRepository.findById(request.getShelterId())
                .orElseThrow(() -> new EntityNotFoundException("보호소를 찾을 수 없습니다. ID: " + request.getShelterId()));

        Animal animal = request.toEntity(shelter);
        Animal saved = animalRepository.save(animal);
        return AnimalResponse.from(saved);
    }

    @Transactional
    public AnimalResponse updateAnimal(Long id, AnimalCreateRequest request) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("동물을 찾을 수 없습니다. ID: " + id));

        // Update fields
        animal.setName(request.getName());
        animal.setSpecies(request.getSpecies());
        animal.setBreed(request.getBreed());
        animal.setAge(request.getAge());
        animal.setGender(request.getGender());
        animal.setSize(request.getSize());
        animal.setWeight(request.getWeight());
        animal.setDescription(request.getDescription());
        animal.setTemperament(request.getTemperament());
        animal.setHealthStatus(request.getHealthStatus());
        animal.setNeutered(request.getNeutered());
        animal.setVaccinated(request.getVaccinated());
        animal.setImageUrl(request.getImageUrl());

        return AnimalResponse.from(animal);
    }

    @Transactional
    public void deleteAnimal(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new EntityNotFoundException("동물을 찾을 수 없습니다. ID: " + id);
        }
        animalRepository.deleteById(id);
    }

    public Page<AnimalResponse> getAnimalsByShelter(Long shelterId, Pageable pageable) {
        return animalRepository.findByShelterId(shelterId, pageable)
                .map(AnimalResponse::from);
    }

    public Page<AnimalResponse> getRecommendations(Long userId, Pageable pageable) {
        Preference preference = preferenceRepository.findByUserId(userId).orElse(null);

        if (preference == null) {
            if (pageable.getSort().getOrderFor("random") != null) {
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by("createdAt").descending());
            }
            // 선호도 없으면 최신 동물 추천
            return animalRepository.findAnimals(AnimalStatus.PROTECTED, null, null, null, null, null, pageable)
                    .map(AnimalResponse::from);
        }

        return animalRepository.findRecommendedAnimals(
                preference.getSpecies(),
                preference.getSize(),
                preference.getMinAge(),
                preference.getMaxAge(),
                pageable)
                .map(AnimalResponse::from);
    }
}
