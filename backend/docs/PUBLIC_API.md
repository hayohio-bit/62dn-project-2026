# 🌐 PUBLIC API Documentation

Antigravity 동물 관리 시스템에서 제공하는 주요 API 엔드포인트와 연동 가이드입니다.

---

## 🔑 인증 및 보안

- **인증 방식**: JSON Web Token (JWT)
- **전달 방식**: HTTP Header `Authorization: Bearer {TOKEN}`
- **공개 엔드포인트**: 동물 목록 조회, 상세 정보, 게시판 읽기 등 (추후 설정에 따라 변경 가능)

---

## 🛤️ 주요 API 엔드포인트

전체 API 상세 속성은 [Swagger UI](http://localhost:8080/swagger-ui/index.html)에서 확인할 수 있습니다.

### 1. 동물 관리 (Animal API)
- `GET /animals`: 동물 목록 페이징 조회
- `GET /animals/{id}`: 특정 동물 상세 정보 조회
- `POST /animals`: 신규 동물 등록 (Admin 전용)
- `PUT /animals/{id}`: 동물 정보 수정 (Admin 전용)

### 2. 입양 신청 (Adoption API)
- `POST /adoptions/request`: 입양 신청 제출
- `GET /adoptions/my`: 내가 신청한 입양 현황 조회
- `POST /adoptions/{id}/approve`: 입양 신청 승인 (Admin 전용)

### 3. 게시판 (Board API)
- `GET /boards?type={NOTICE|COMMUNITY}`: 타입별 게시글 목록 조회
- `GET /boards/{id}`: 게시글 상세 및 댓글 조회
- `POST /boards`: 게시글 작성

---

## 📂 요청/응답 예시 (Quick Start)

### 동물 정보 조회 응답 (`GET /animals/1`)
```json
{
  "id": 1,
  "name": "초코",
  "species": "Dog",
  "breed": "Poodle",
  "age": 3,
  "gender": "FEMALE",
  "status": "AVAILABLE",
  "imageUrl": "/uploads/uuid-choco.jpg",
  "createdAt": "2026-02-05T12:00:00"
}
```

### 입양 신청 요청 (`POST /adoptions/request`)
```json
{
  "animalId": 1,
  "applicantName": "홍길동",
  "applicantContact": "010-1234-5678",
  "reason": "마당이 있는 집에서 함께 활발하게 뛰어놀고 싶습니다."
}
```

---

## ⚠️ 에러 코드 가이드
시스템 공통 에러 핸들러(`GlobalExceptionHandler`)를 통해 아래와 같은 형식으로 응답합니다.
- `400 BAD REQUEST`: 파라미터 유효성 검사 실패
- `404 NOT FOUND`: 요청한 리소스가 존재하지 않음
- `500 INTERNAL SERVER ERROR`: 서버 내부 오류
