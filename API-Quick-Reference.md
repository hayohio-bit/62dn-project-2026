# 🔑 API Key & Endpoint Quick Reference

**프로젝트**: 62dn (유기동물 입양/임보 매칭 플랫폼)  
**작성**: 2026-02-06  
**용도**: 프로젝트 작업 시 빠른 참조용

---

## 📌 환경 변수 (백엔드)

백엔드는 `backend/src/main/resources/application.yml`에서 아래 변수를 참조합니다.  
실행 시 **시스템 환경 변수**로 설정하거나, IDE 실행 설정에 넣으세요. (Spring Boot는 기본적으로 `.env` 파일을 읽지 않습니다.)

```env
# Government API (공공데이터 구조동물조회) - animal.api.service-key
GOV_API_KEY=yMP6lpEQqhP8+loxfdMTCQFf6L7lis9mWT2UtOUFTyo+bG0ZGCpAN3VwGQj0tC217bLATyoRw7+dLMQ5mfC1Wg==
# GET 요청 시 URL 인코딩된 키가 필요할 때만 사용
GOV_API_KEY_ENCODED=yMP6lpEQqhP8%2BloxfdMTCQFf6L7lis9mWT2UtOUFTyo%2BbG0ZGCpAN3VwGQj0tC217bLATyoRw7%2BdLMQ5mfC1Wg%3D%3D

# Kakao (맵/로그인) - application.yml kakao.*
KAKAO_REST_API_KEY=abe5b23bb7cbeb9f0f77760d4f742d7f
KAKAO_JAVASCRIPT_KEY=bb97221a6d16600ec42f6e1b12d68ed3
KAKAO_ADMIN_KEY=b4d55266c81e1d7170d2cf9d6b2b19db

# API 제한 - application.yml api.config
API_CALL_TIMEOUT_SECONDS=30
API_RETRY_COUNT=3
API_DAILY_LIMIT=10000
```

- `backend/.env.example`에 위 항목 설명이 있습니다. 복사 후 값을 채워 사용하세요.

---

## 🌐 API Endpoints

### Government APIs (공공데이터포털)

| API명 | Base URL | 엔드포인트 | 설명 |
|------|----------|----------|------|
| **구조동물 상세** ⭐ | `https://apis.data.go.kr/1543061` | `/abandonmentPublicService_v2/abandonmentPublic_v2` | 메인 사용 API |
| 시도 조회 | 동일 | `/abandonmentPublicService_v2/sido` | 시도 마스터 |
| 시군구 조회 | 동일 | `/abandonmentPublicService_v2/sigungu` | 시군구 마스터 |
| 품종 조회 | 동일 | `/abandonmentPublicService_v2/kind` | 품종 마스터 |
| 분실동물 | 동일 | `/lossInfoService/lossInfo` | 분실동물 조회 |
| 보호소 정보 | 동일 | `/animalShelterSrvc_v2/shelterInfo_v2` (별도 서비스) | 보호소 상세정보 |
| 통계 | 동일 | `/rescueAnimalStatsService/rescueAnimalStats` | 통계 데이터 |

### Kakao Map API

| API명 | Base URL | 엔드포인트 | 설명 |
|------|----------|----------|------|
| 주소 검색 | `https://dapi.kakao.com` | `/v2/local/search/address.json` | 주소 → 좌표 |
| 역지오코딩 | 동일 | `/v2/local/geo/coord2address.json` | 좌표 → 주소 |
| 장소 검색 | 동일 | `/v2/local/search/keyword.json` | 지정된 반경 내 검색 |

---

## 📊 주요 파라미터

### 구조동물 조회 (`getAbandonedAnimals`)

```
serviceKey: ${GOV_API_KEY}
bgnde: 20260101 (시작일, YYYYMMDD)
endde: 20260206 (종료일, YYYYMMDD)
state: notice (공고중) or protect (보호중)
pageNo: 1
numOfRows: 100 (1페이지 행수)
_type: json
```

**Java 호출 예**:
```java
govApiClient.getAbandonedAnimals(
    LocalDate.of(2026, 1, 1),     // startDate
    LocalDate.of(2026, 2, 6),     // endDate
    "notice",                      // state
    1,                            // pageNo
    100                           // numOfRows
);
```

### 축종 코드 (upkind)

| 축종 | 코드 |
|------|------|
| 개 | `417000` |
| 고양이 | `422400` |
| 기타 | `429900` |

### 성별 코드 (sex_cd)

| 성별 | 코드 |
|------|------|
| 수컷 | `M` |
| 암컷 | `F` |
| 미상 | `Q` |

### 중성화 여부 (neuter_yn)

| 상태 | 코드 |
|------|------|
| 예 | `Y` |
| 아니오 | `N` |
| 미상 | `U` |

---

## 🔗 백엔드 REST API (이 프로젝트)

### 1️⃣ 동물 목록 조회 (입양 페이지용)

```
GET http://localhost:8080/api/animals?page=0&size=12&species=DOG&status=PROTECTED
```

- 쿼리: `page`, `size`, `species`(DOG/CAT), `status`, `breed` 등

### 2️⃣ 공공 API → DB 동기화 (관리자)

```
POST http://localhost:8080/api/admin/animals/sync?days=7&maxPages=1&species=417000
```

- `days`: 조회 기간(일), 기본 7  
- `maxPages`: 최대 페이지 수, 기본 1  
- `species`: 생략(전체) / `417000`(개) / `422400`(고양이)  
- 인증: 관리자 JWT 필요

### 3️⃣ 동기화 이력 조회

```
GET http://localhost:8080/api/admin/animals/sync-history?page=0&size=10
```

---

## 🛠️ 이 프로젝트 코드 위치

| 역할 | 파일 |
|------|------|
| 공공 API 호출 | `backend/.../service/PublicApiService.java` |
| 동기화 로직 | `backend/.../service/AnimalSyncService.java` |
| 관리자 동기화 API | `backend/.../controller/AdminAnimalController.java` |
| 설정 (base-url, service-key) | `backend/src/main/resources/application.yml` → `animal.api.*` |

- 구조동물 조회: `PublicApiService.getAnimalList(bgnde, endde, upkind, ...)`  
- 엔드포인트: `animal.api.base-url` + `/abandonmentPublicService_v2/abandonmentPublic_v2`  
- serviceKey: `GOV_API_KEY` 또는 URL 인코딩 시 `GOV_API_KEY_ENCODED` 사용

---

## 📱 Curl 예시

```bash
# 동물 목록 (페이지)
curl -X GET "http://localhost:8080/api/animals?page=0&size=12"

# 공공 API 동기화 (관리자 토큰 필요)
curl -X POST "http://localhost:8080/api/admin/animals/sync?days=7&maxPages=1" \
  -H "Authorization: Bearer YOUR_ADMIN_JWT"
```

---

## ⚠️ 주의사항

| 항목 | 내용 |
|------|------|
| **API 한도** | 일일 10,000건 |
| **타임아웃** | 30초 (개발환경) / 15초 (프로덕션) |
| **인코딩** | UTF-8 필수 |
| **날짜 형식** | YYYYMMDD (예: 20260206) |
| **재시도** | 최대 3회 (개발환경) / 2회 (프로덕션) |
| **.env 파일** | .gitignore에 등록 필수 |

---

## 🚨 일반적인 에러

| 에러 | 원인 | 해결 |
|------|------|------|
| `Could not resolve placeholder` | 환경 변수 미설정 | GOV_API_KEY 등 설정 또는 application.yml 기본값 확인 |
| `Connection refused` | DB/서버 미실행 | H2/MySQL 및 백엔드 서버 실행 확인 |
| `Bad API Key` / 인증 실패 | 키 오타·만료·인코딩 | GOV_API_KEY 또는 GOV_API_KEY_ENCODED 확인 |
| `503 Service Unavailable` | API 서버 점검 | data.go.kr 상태 확인 |
| `Port 8080 already in use` | 포트 충돌 | `application.yml`에서 포트 변경 |

---

## 📚 빠른 링크

- 🔗 [공공데이터포털](https://www.data.go.kr) - 구조동물 API 관리
- 🔗 [카카오 개발자](https://developers.kakao.com/console/app) - 맵 API 키 확인
- 🔗 [API 상태 확인](https://www.data.go.kr/data/15098915/openapi.do) - 구조동물 상세 조회
- 🔗 [MySQL 접속](localhost:3306) - 데이터베이스 관리

---

## 💾 파일 위치 (62dn 프로젝트)

```
62dn-project-2026/
├── API-Quick-Reference.md     ← 이 문서
├── backend/
│   ├── .env.example           ← 환경 변수 템플릿 (GOV_API_KEY, KAKAO_* 등)
│   └── src/main/
│       ├── java/.../platform/
│       │   ├── config/         (SecurityConfig, TestDataLoader 등)
│       │   ├── controller/     (AdminAnimalController, AnimalController)
│       │   └── service/       (PublicApiService, AnimalSyncService)
│       └── resources/
│           └── application.yml  ← animal.api.base-url, service-key, kakao.*
└── frontend/
    └── .env.example           ← VITE_API_BASE_URL, VITE_MAP_API_KEY 등
```

---

**프로젝트 진행 중 자주 참조하세요! 🚀**
