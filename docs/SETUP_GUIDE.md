# 프로젝트 개발 환경 설정 가이드

문의하신 이메일 서비스, 파일 업로드, 환경 변수 설정에 대한 상세 설명입니다.

---

## 1. 이메일 서비스 설정 (SMTP / Resend)

이메일 서비스는 시스템에서 알림이나 인증을 위해 이메일을 발송할 때 사용됩니다. 현재 프로젝트 설정에는 **Resend**라는 API 서비스와 **SMTP** 방식 중 선택할 수 있는 구조입니다.

### 💡 SMTP란?
- **S**imple **M**ail **T**ransfer **P**rotocol의 약자로, 인터넷을 통해 이메일을 보내는 표준 프로토콜입니다.
- Gmail, Naver, Outlook 등의 메일 서버를 통해 이메일을 발송할 때 사용합니다.

### ✅ 설정 방법 (Resend 사용 시 - 권장)
현재 프로젝트의 `.env.example`에는 `RESEND_API_KEY`가 설정되어 있습니다. Resend는 개발자가 사용하기 편리한 최신 이메일 API 서비스입니다.
1. [Resend](https://resend.com) 회원가입 후 API Key를 발급받습니다.
2. 백엔드의 `.env` 파일에 `RESEND_API_KEY=발급받은_키`를 입력합니다.

### ✅ 설정 방법 (전통적인 SMTP/Gmail 사용 시)
Spring Boot 표준 방식으로 변경하고 싶다면 `application.yml`에 다음과 같은 설정이 추가되어야 합니다.
```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: 본인의_이메일@gmail.com
    password: 구글_앱_비밀번호 (2차인증 후 발급)
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```

---

## 2. 파일 업로드 저장 경로 설정

사용자가 올린 동물 사진이나 증빙 서류 등이 서버의 어디에 저장될지를 결정합니다.

### ✅ 현재 설정 확인
현재 `backend/src/main/resources/application.yml`에 다음과 같이 설정되어 있습니다.
```yaml
animal:
  upload-path: d:/dn-project/uploads
```

### ✅ 변경 방법
1. **Windows**: 본인이 원하는 경로로 수정하세요 (예: `c:/my-project/files`).
2. **Mac/Linux**: `/Users/username/uploads`와 같이 절대 경로로 설정하는 것이 좋습니다.
3. 해당 폴더가 없으면 서버 실행 시 `FileStorageService`가 자동으로 생성합니다.

---

## 3. 환경 변수(Environment Variables) 설정

보안상 코드(Git)에 직접 올리면 안 되는 민감한 정보(DB 비밀번호, API 키 등)를 별도의 파일로 관리하는 방식입니다.

### ✅ 설정 단계
1. **백엔드 (`/backend`)**:
   - `.env.example` 파일을 복사하여 `.env` 파일을 만듭니다.
   - 내부의 `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET` 등을 본인의 로컬 환경에 맞게 수정합니다.
   - **주의**: `JWT_SECRET`은 보안을 위해 최소 32자 이상의 임의의 긴 문자열을 사용하는 것이 좋습니다.

2. **프론트엔드 (`/frontend`)**:
   - `.env.example` 파일을 복사하여 `.env` 파일을 만듭니다.
   - `VITE_API_BASE_URL`: 백엔드 서버 주소 (기본값: http://localhost:8080/api)
   - `VITE_MAP_API_KEY`: 카카오 개발자 콘솔에서 발급받은 JavaScript 키를 넣습니다.

---

## 🚀 요약 및 팁
- **보안**: `.env` 파일은 절대 Git에 올리지 마세요 (이미 `.gitignore`에 등록되어 있습니다).
- **카카오 지도**: 카카오 API 설정 시 도메인에 `http://localhost:5173`을 등록해야 지도가 정상적으로 보입니다.
- **DB 생성**: 작업을 시작하기 전에 `docs/sql.txt`에 있는 쿼리를 실행하여 데이터베이스와 테이블을 먼저 만들어야 백엔드가 정상적으로 실행됩니다.
