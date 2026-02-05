package com.antigravity.animal.service;

import com.antigravity.animal.dto.AnimalRequestDTO;
import com.antigravity.animal.dto.AnimalResponseDTO;
import com.antigravity.animal.entity.Animal;
import com.antigravity.animal.entity.AnimalStatus;
import com.antigravity.animal.repository.AnimalRepository;
import com.antigravity.global.exception.BusinessException;
import com.antigravity.global.exception.ErrorCode;
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

    @Transactional
    public AnimalResponseDTO registerAnimal(AnimalRequestDTO requestDTO) {
        Animal animal = requestDTO.toEntity();
        Animal savedAnimal = animalRepository.save(animal);
        return AnimalResponseDTO.fromEntity(savedAnimal);
    }

    public AnimalResponseDTO getAnimal(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ANIMAL_NOT_FOUND));
        return AnimalResponseDTO.fromEntity(animal);
    }

    public Page<AnimalResponseDTO> getAnimals(AnimalStatus status, Pageable pageable) {
        Page<Animal> animals;
        if (status != null) {
            animals = animalRepository.findByStatus(status, pageable);
        } else {
            animals = animalRepository.findAll(pageable);
        }
        return animals.map(AnimalResponseDTO::fromEntity);
    }

    @Transactional
    public AnimalResponseDTO updateAnimal(Long id, AnimalRequestDTO requestDTO) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ANIMAL_NOT_FOUND));

        animal.update(
                requestDTO.getName(),
                requestDTO.getSpecies(),
                requestDTO.getBreed(),
                requestDTO.getAge(),
                requestDTO.getGender(),
                requestDTO.getWeight(),
                requestDTO.getNeutered(),
                requestDTO.getHealthStatus(),
                requestDTO.getStatus(),
                requestDTO.getImageUrl());

        return AnimalResponseDTO.fromEntity(animal);
    }

    @Transactional
    public void deleteAnimal(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ANIMAL_NOT_FOUND));
        animalRepository.delete(animal);
    }

    @Transactional
    public void updateAnimalStatus(Long id, AnimalStatus status) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ANIMAL_NOT_FOUND));
        animal.updateStatus(status);
    }

    @Transactional
    public void updateAnimalImage(Long id, String imageUrl) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ANIMAL_NOT_FOUND));
        animal.update(
                animal.getName(),
                animal.getSpecies(),
                animal.getBreed(),
                animal.getAge(),
                animal.getGender(),
                animal.getWeight(),
                animal.getNeutered(),
                animal.getHealthStatus(),
                animal.getStatus(),
                imageUrl);
    }
}
