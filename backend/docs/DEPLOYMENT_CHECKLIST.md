# 🚀 DEPLOYMENT CHECKLIST

Antigravity 동물 관리 시스템을 운영(Prod) 서버에 배포하기 전 확인해야 할 항목들입니다.

---

## 📋 필수 체크리스트

### 1. 환경 준비 (Prerequisites)
- [ ] **Java 17 runtime** 설치 확인
- [ ] **MySQL DB** 가용성 확인 (RDS 등)
- [ ] **Network**: 8080 포트 인바운드 허용 확인

### 2. 환경 변수 설정 (Environment Variables)
배포 서버의 OS 환경 변수 또는 보안 저장소에 아래 항목들이 설정되어야 합니다.
- [ ] `PROD_DB_URL`: MySQL 연결 URL (`jdbc:mysql://...`)
- [ ] `PROD_DB_USERNAME`: DB 계정명
- [ ] `PROD_DB_PASSWORD`: DB 비밀번호
- [ ] `RESEND_API_KEY`: 이메일 발송을 위한 Resend API 키
- [ ] `JWT_SECRET`: 토큰 서명을 위한 충분한 길이의 비밀 문자열

### 3. 파일 스토리지 (File Storage)
- [ ] `application.yml`에 설정된 `animal.upload-path` 경로가 서버에 존재하고 쓰기 권한이 있는지 확인.
- [ ] 업로드된 이미지 서빙을 위한 경로 설정 확인.

### 4. 빌드 및 배포 방법 (Production Build)
```bash
# 1. 빌드 (테스트 포함)
./gradlew clean build

# 2. 실행 (운영 프로필 활성화)
java -jar build/libs/antigravity-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### 5. 사후 검증 (Post-Deployment Verification)
- [ ] `/swagger-ui/index.html` 접속 확인
- [ ] 로그에서 `Started AntigravityApplication` 메시지 확인
- [ ] DB 연결 성공 로그 확인
