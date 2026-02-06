package com.dnproject.platform.service;

import com.dnproject.platform.domain.Animal;
import com.dnproject.platform.domain.Shelter;
import com.dnproject.platform.domain.SyncHistory;
import com.dnproject.platform.domain.constant.AnimalStatus;
import com.dnproject.platform.domain.constant.Size;
import com.dnproject.platform.domain.constant.Species;
import com.dnproject.platform.domain.constant.SyncTriggerType;
import com.dnproject.platform.dto.publicapi.AnimalItem;
import com.dnproject.platform.repository.AnimalRepository;
import com.dnproject.platform.repository.ShelterRepository;
import com.dnproject.platform.repository.SyncHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnimalSyncService {

    private final PublicApiService publicApiService;
    private final AnimalRepository animalRepository;
    private final ShelterRepository shelterRepository;
    private final SyncHistoryRepository syncHistoryRepository;

    @Transactional
    public void syncFromPublicApi(Integer days, Integer maxPages, String species) {
        String bgnde = LocalDate.now().minusDays(days == null ? 7 : days)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String endde = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String upkind = "417000".equals(species) ? "417000" : ("422400".equals(species) ? "422400" : ""); // 개(417000),
                                                                                                          // 고양이(422400)

        syncAnimals(bgnde, endde, upkind, days, species);
    }

    @Transactional
    public void syncAnimals(String bgnde, String endde, String upkind, Integer daysParam, String speciesFilter) {
        log.info("Starting animal synchronization: {} to {}, kind: {}", bgnde, endde, upkind);

        List<AnimalItem> items = publicApiService.getAnimalList(bgnde, endde, upkind, null, null, null, 1, 1000);

        int addedCount = 0;
        int updatedCount = 0;
        int successCount = 0;
        int totalCount = items.size();

        // 기본 보호소 조회 (Fallback용)
        Shelter defaultShelter = shelterRepository.findAll().stream().findFirst().orElse(null);

        for (AnimalItem item : items) {
            try {
                Shelter shelter = shelterRepository.findByPublicApiShelterId(item.getCareNm())
                        .orElseGet(() -> shelterRepository.findAll().stream()
                                .filter(s -> s.getName().equals(item.getCareNm()))
                                .findFirst()
                                .orElse(defaultShelter));

                if (shelter == null) {
                    log.warn("Shelter not found and no default shelter available. Skipping animal: {}",
                            item.getDesertionNo());
                    continue;
                }

                Optional<Animal> animalOpt = animalRepository.findByPublicApiAnimalId(item.getDesertionNo());
                Animal animal;
                if (animalOpt.isPresent()) {
                    animal = animalOpt.get();
                    updatedCount++;
                } else {
                    animal = new Animal();
                    addedCount++;
                }

                mapItemToEntity(item, animal, shelter);
                animalRepository.save(animal);
                successCount++;

            } catch (Exception e) {
                log.error("Error syncing animal {}: {}", item.getDesertionNo(), e.getMessage());
            }
        }

        SyncHistory history = SyncHistory.builder()
                .triggerType(SyncTriggerType.MANUAL)
                .totalCount(totalCount)
                .successCount(successCount)
                .addedCount(addedCount)
                .updatedCount(updatedCount)
                .deletedCount(0)
                .correctedCount(0)
                .daysParam(daysParam)
                .speciesFilter(speciesFilter)
                .status("SUCCESS")
                .build();

        syncHistoryRepository.save(history);
        log.info("Synchronization completed. Total: {}, Added: {}, Updated: {}", totalCount, addedCount, updatedCount);
    }

    public org.springframework.data.domain.Page<com.dnproject.platform.dto.response.SyncHistoryResponse> getSyncHistory(
            org.springframework.data.domain.Pageable pageable) {
        return syncHistoryRepository.findAll(pageable)
                .map(com.dnproject.platform.dto.response.SyncHistoryResponse::from);
    }

    private void mapItemToEntity(AnimalItem item, Animal animal, Shelter shelter) {
        animal.setShelter(shelter);
        animal.setPublicApiAnimalId(item.getDesertionNo());
        animal.setOrgName(item.getOrgNm());
        animal.setChargeName(item.getChargeNm());
        animal.setChargePhone(item.getOfficetel());

        // Species mapping
        if (item.getKindCd().contains("개")) {
            animal.setSpecies(Species.DOG);
        } else if (item.getKindCd().contains("고양이")) {
            animal.setSpecies(Species.CAT);
        } else {
            animal.setSpecies(Species.ETC);
        }

        // Breed extraction: "[개] 믹스견" -> "믹스견"
        String breed = item.getKindCd();
        if (breed.contains("]")) {
            breed = breed.substring(breed.indexOf("]") + 1).trim();
        }
        animal.setBreed(breed);

        animal.setGender(item.getSexCd());
        animal.setAge(extractAge(item.getAge()));
        animal.setWeight(extractWeight(item.getWeight()));
        animal.setDescription(item.getSpecialMark());
        animal.setNeutered("Y".equalsIgnoreCase(item.getNeuterYn()));
        animal.setImageUrl(item.getPopfile());

        // Status mapping
        if (item.getProcessState().contains("보호중")) {
            animal.setStatus(AnimalStatus.PROTECTED);
        } else if (item.getProcessState().contains("입양")) {
            animal.setStatus(AnimalStatus.ADOPTED);
        } else if (item.getProcessState().contains("안락사")) {
            animal.setStatus(AnimalStatus.EUTHANIZED);
        } else if (item.getProcessState().contains("자연사")) {
            animal.setStatus(AnimalStatus.NATURAL_DEATH);
        } else if (item.getProcessState().contains("반환")) {
            animal.setStatus(AnimalStatus.RETURNED);
        } else {
            animal.setStatus(AnimalStatus.PROTECTED); // Default status
        }

        if (item.getHappenDt() != null && item.getHappenDt().length() == 8) {
            animal.setRegisterDate(LocalDate.parse(item.getHappenDt(), DateTimeFormatter.ofPattern("yyyyMMdd")));
        }

        // Size mapping (rough estimation based on weight)
        animal.setSize(determineSize(animal.getWeight()));
    }

    private Integer extractAge(String ageStr) {
        try {
            // "2023(년생)" -> 2023
            return Integer.parseInt(ageStr.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return null;
        }
    }

    private BigDecimal extractWeight(String weightStr) {
        try {
            // "5.2(Kg)" -> 5.2
            return new BigDecimal(weightStr.replaceAll("[^0-9.]", ""));
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private Size determineSize(BigDecimal weight) {
        if (weight == null)
            return Size.MEDIUM;
        if (weight.compareTo(new BigDecimal("5")) <= 0)
            return Size.SMALL;
        if (weight.compareTo(new BigDecimal("15")) <= 0)
            return Size.MEDIUM;
        return Size.LARGE;
    }
}
