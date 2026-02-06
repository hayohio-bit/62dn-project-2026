package com.dnproject.platform.service;

import com.dnproject.platform.dto.publicapi.AnimalItem;
import com.dnproject.platform.dto.publicapi.KindItem;
import com.dnproject.platform.dto.publicapi.PublicApiResponse;
import com.dnproject.platform.dto.publicapi.ShelterItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicApiService {

    /** API-Quick-Reference: 구조동물 상세 메인 엔드포인트 */
    private static final String ABANDONMENT_PATH = "/abandonmentPublicService_v2/abandonmentPublic_v2";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${animal.api.base-url}")
    private String baseUrl;

    @Value("${animal.api.service-key}")
    private String serviceKey;

    @Value("${animal.api.service-key-encoded:}")
    private String serviceKeyEncoded;

    @Value("${animal.api.params.bgnde:20260101}")
    private String defaultBgnde;

    @Value("${animal.api.params.endde:20260206}")
    private String defaultEndde;

    @Value("${animal.api.params.state:notice}")
    private String defaultState;

    @Value("${animal.api.params.numOfRows:100}")
    private int defaultNumOfRows;

    @Value("${animal.api.params._type:json}")
    private String defaultType;

    public List<AnimalItem> getAnimalList(String bgnde, String endde, String upkind, String uprCd, String orgCd,
            String careRegNo, int pageNo, int numOfRows) {

        // Use defaults if provided values are null or empty
        String effectiveBgnde = (bgnde == null || bgnde.isEmpty()) ? defaultBgnde : bgnde;
        String effectiveEndde = (endde == null || endde.isEmpty()) ? defaultEndde : endde;
        int effectiveNumOfRows = (numOfRows <= 0) ? defaultNumOfRows : numOfRows;

        String effectiveServiceKey = (serviceKeyEncoded != null && !serviceKeyEncoded.isEmpty())
                ? serviceKeyEncoded
                : serviceKey;

        URI uri = UriComponentsBuilder.fromUriString(baseUrl + ABANDONMENT_PATH)
                .queryParam("serviceKey", effectiveServiceKey)
                .queryParam("bgnde", effectiveBgnde)
                .queryParam("endde", effectiveEndde)
                .queryParam("upkind", upkind)
                .queryParam("upr_cd", uprCd)
                .queryParam("org_cd", orgCd)
                .queryParam("care_reg_no", careRegNo)
                .queryParam("state", defaultState)
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows", effectiveNumOfRows)
                .queryParam("_type", defaultType)
                .build(true)
                .toUri();

        log.debug("Calling Public API: {}", uri);

        try {
            ResponseEntity<PublicApiResponse<AnimalItem>> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<PublicApiResponse<AnimalItem>>() {
                    });
            return extractItems(response);
        } catch (Exception e) {
            log.error("Error calling Public API: {}", e.getMessage());
            return List.of();
        }
    }

    public List<ShelterItem> getShelterList(String uprCd, String orgCd) {
        String key = (serviceKeyEncoded != null && !serviceKeyEncoded.isEmpty()) ? serviceKeyEncoded : serviceKey;
        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/animalShelterSrvc_v2/shelterInfo_v2")
                .queryParam("serviceKey", key)
                .queryParam("upr_cd", uprCd)
                .queryParam("org_cd", orgCd)
                .queryParam("_type", "json")
                .build(true)
                .toUri();

        try {
            ResponseEntity<PublicApiResponse<ShelterItem>> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<PublicApiResponse<ShelterItem>>() {
                    });
            return extractItems(response);
        } catch (Exception e) {
            log.error("Error fetching shelter list: {}", e.getMessage());
            return List.of();
        }
    }

    public List<KindItem> getKindList(String upKindCd) {
        String key = (serviceKeyEncoded != null && !serviceKeyEncoded.isEmpty()) ? serviceKeyEncoded : serviceKey;
        URI uri = UriComponentsBuilder.fromUriString(baseUrl + "/abandonmentPublicService_v2/kind_v2")
                .queryParam("serviceKey", key)
                .queryParam("up_kind_cd", upKindCd)
                .queryParam("_type", "json")
                .build(true)
                .toUri();

        try {
            ResponseEntity<PublicApiResponse<KindItem>> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<PublicApiResponse<KindItem>>() {
                    });
            return extractItems(response);
        } catch (Exception e) {
            log.error("Error fetching kind list: {}", e.getMessage());
            return List.of();
        }
    }

    private <T> List<T> extractItems(ResponseEntity<PublicApiResponse<T>> response) {
        PublicApiResponse<T> body = response.getBody();
        if (body != null &&
                body.getResponse() != null &&
                body.getResponse().getBody() != null &&
                body.getResponse().getBody().getItems() != null) {
            List<T> items = body.getResponse().getBody().getItems().getItem();
            return items != null ? items : List.of();
        }
        return List.of();
    }
}
