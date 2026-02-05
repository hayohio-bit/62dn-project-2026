package com.dnproject.platform.service;

import com.dnproject.platform.domain.Animal;
import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.constant.AnimalStatus;
import com.dnproject.platform.dto.request.AnimalCreateRequest;
import com.dnproject.platform.dto.response.AnimalResponse;
import com.dnproject.platform.repository.AnimalRepository;
import com.dnproject.platform.repository.ShelterRepository;
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

    public Page<AnimalResponse> getAnimals(AnimalStatus status, String species, String breed, Pageable pageable) {
        return animalRepository.findAnimals(status, species, breed, pageable)
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
}
