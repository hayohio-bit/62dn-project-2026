# 🔗 INTEGRATION CHECK

시스템의 주요 구성 요소 간 연동 상태를 점검하는 프로토콜입니다.

---

## 🔍 연동 체크 항목

### 1. 데이터베이스 연동 (DB Integrity)
- **목적**: 엔티티와 실제 테이블 스키마가 일치하는지 확인.
- **방법**: 
    - 개발 환경: `./gradlew test` 실행 (H2 기반 JPA 테스트).
    - 운영 환경: 서버 가동 시 Hibernate `validate` 로그 확인.

### 2. 파일 업로드 및 조회 (File I/O)
- **목적**: `animal.upload-path` 설정이 정상 동작하는지 확인.
- **방법**:
    - 관리자 페이지에서 동물 이미지 업로드 시도.
    - 배포 서버 하위 폴더(`d:/dn-project/uploads`)에 파일이 생성되는지 확인.
    - 웹 브라우저에서 `/uploads/{filename}` 경로로 이미지가 보이는지 확인.

### 3. 외부 API (Third-party)
- **Resend (Email)**:
    - 알림 발송 로직 수행 시 에러 로그 확인.
    - `RESEND_API_KEY`가 유효하지 않을 경우의 예외 처리 확인.

### 4. 프론트엔드-백엔드 연동 (API Consistency)
- **OpenAPI 적용**: 개발 서버 실행 후 `http://localhost:8080/v3/api-docs` JSON이 정상 출력되는지 확인.
- **Thymeleaf 템플릿**: 각 컨트롤러 메서드(`@GetMapping`)가 지정된 HTML 템플릿을 올바르게 렌더링하는지 확인.

---

## 🛠️ 문제 해결 (Troubleshooting)
- **DB Connection Fail**: `PROD_DB_URL` 형식 및 방화벽 설정 확인.
- **Image Not Found**: `upload-path` 절대 경로 설정과 권한 확인.
- **JWT Auth Fail**: 클라이언트-서버 간 `JWT_SECRET` 동일 여부 확인.
