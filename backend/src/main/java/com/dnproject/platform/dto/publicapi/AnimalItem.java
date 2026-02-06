package com.dnproject.platform.dto.publicapi;

import lombok.Data;

@Data
public class AnimalItem {
    private String desertionNo; // 유기번호 (DB: public_api_animal_id)
    private String filename; // thumbnail 이미지
    private String happenDt; // 접수일 (YYYYMMDD)
    private String happenPlace; // 발견장소
    private String kindCd; // 품종 코드 (API v2: 숫자 코드)
    private String upKindNm; // 종류명 (API v2: 개, 고양이 등)
    private String kindFullNm; // 종류+품종 전체 명칭 (API v2: [개] 믹스견 등)
    private String colorCd; // 색상
    private String age; // 나이 (2023(년생) 등)
    private String weight; // 체중 (0.5(Kg) 등)
    private String noticeNo; // 공고번호
    private String noticeSdt; // 공고시작일
    private String noticeEdt; // 공고종료일
    @com.fasterxml.jackson.annotation.JsonAlias("popfile1")
    private String popfile; // image
    private String processState; // 상태 (보호중, 종료(입양) 등)
    private String sexCd; // 성별 (M, F, Q)
    private String neuterYn; // 중성화여부 (Y, N, U)
    private String specialMark; // 특징 (DB: description)
    private String careNm; // 보호소이름
    private String careTel; // 보호소전화번호
    private String careAddr; // 보호소주소
    private String careRegNo; // 보호소 고유번호 (ID)
    private String orgNm; // 시군구명
    private String chargeNm; // 담당자
    private String officetel; // 담당자연락처
}
