# 🐶 유기동물 입양/임보 매칭 플랫폼 (62dn-project-2026)

이 프로젝트는 유기동물과 새로운 가족을 연결해주는 입양/임보 매칭 플랫폼입니다. 사용자 맞춤형 추천 시스템을 통해 유기동물 입양 및 임시 보호 활성화를 목표로 합니다.

## 🛠 Tech Stack

### Backend
- **Framework**: Spring Boot 3.4.2
- **Language**: Java 21
- **Database**: MySQL (Connector/J)
- **Security**: Spring Security + JWT (jjwt 0.12.5)
- **Build Tool**: Gradle
- **API Documentation**: SpringDoc OpenAPI (Swagger UI)

### Frontend
- **Framework**: React 18.2
- **Language**: TypeScript
- **Build Tool**: Vite
- **Styling**: Tailwind CSS, Framer Motion
- **State Management**: Zustand, React Query (TanStack Query)
- **HTTP Client**: Axios

## 📂 Project Structure

```text
dn-project/
├── backend/          # Spring Boot API 서버
├── frontend/         # React 웹 애플리케이션
├── docs/             # 프로젝트 문서 및 설계 자료
└── README.md         # 메인 설명 파일
```

## 🚀 Getting Started

### Prerequisites
- JDK 21 이상
- Node.js 18 이상
- MySQL 8.x 이상

### 1. Repository Clone
팀원들은 먼저 레포지토리를 클론합니다.
```bash
git clone https://github.com/hayohio-bit/62dn-project-2026.git
cd 62dn-project-2026
```

### 2. Environment Setup (.env)
로컬 개발 환경 설정을 위해 각 디렉토리의 `.env.example` 파일을 복사하여 `.env` 파일을 생성합니다.

#### Backend (`/backend`)
1. `backend` 폴더로 이동합니다.
2. `.env.example` 파일을 `.env`로 복사합니다.
3. 데이터베이스 계정 정보 및 JWT 시크릿 키 등을 환경에 맞게 수정합니다.
   - `DB_USERNAME`: 본인의 MySQL 계정 (기본값: root)
   - `DB_PASSWORD`: 본인의 MySQL 비밀번호
   - `JWT_SECRET`: 최소 32자 이상의 임의 문자열

#### Frontend (`/frontend`)
1. `frontend` 폴더로 이동합니다.
2. `.env.example` 파일을 `.env`로 복사합니다.
3. 필요한 API 키를 설정합니다. (상세 내용은 [프론트엔드 연동 가이드](frontend/API_INTEGRATION_GUIDE.md) 참고)
   - `VITE_API_BASE_URL`: 백엔드 API 주소 (기본값: http://localhost:8080/api)
   - `VITE_MAP_API_KEY`: [카카오 개발자 콘솔](https://developers.kakao.com/)에서 발급받은 JavaScript 키

### 3. Database Setup
1. MySQL 서버에 접속합니다.
2. [docs/sql.txt](docs/sql.txt) 파일의 내용을 실행하여 데이터베이스(`DN_Platform`)와 필요한 테이블들을 생성합니다.

#### 주요 테이블 명세
전체 테이블 구조는 [docs/sql.txt](docs/sql.txt)에서 확인할 수 있습니다. (총 15개 테이블)

| 테이블명 | 설명 |
| :--- | :--- |
| **users** | 회원 정보 및 권한 (USER, MANAGER) 관리 |
| **animals** | 보호 중인 유기동물 상세 프로필 (공공데이터 API 연동) |
| **shelters** | 보호소 정보 및 담당 관리자 매칭 |
| **adoptions** | 입양 및 임시보호 신청/승인 프로세스 관리 |
| **boards** | 자유게시판, 공지사항, 입양 후기 등 커뮤니티 |
| **volunteers** | 봉사 모집 및 신청 내역 관리 |
| **donations** | 물품/현금 후원 신청 및 내역 관리 |
| **...** | 알림, 댓글, 이미지, 동기화 로그 등 |

### 4. Running the Application

#### Backend Setup
1. `backend` 디렉토리에서 다음 명령어를 실행하여 서버를 시작합니다:
   ```bash
   ./gradlew bootRun
   ```
2. Swagger UI: `http://localhost:8080/swagger-ui/index.html` 에서 API 명세를 확인할 수 있습니다.

#### Frontend Setup
1. `frontend` 디렉토리에서 의존성 패키지를 설치합니다:
   ```bash
   npm install
   ```
2. 로컬 개발 서버를 실행합니다:
   ```bash
   npm run dev
   ```
3. 브라우저에서 `http://localhost:5173` 으로 접속합니다.

## ✨ Key Features
- **맞춤형 추천**: 설문 데이터를 기반으로 사용자에게 가장 적합한 유기동물 추천
- **입양/임보 관리**: 입양 신청 및 승인 프로세스 관리
- **성공 사례**: 실제 입양 성공 사례 공유 및 커뮤니티
- **반응형 디자인**: 모바일과 데스크탑 모두 최적화된 UX/UI

## 🤝 협업 가이드

### Git 워크플로우
**📖 완전한 가이드**: [docs/GIT_WORKFLOW.md](docs/GIT_WORKFLOW.md)

#### 브랜치 전략
- **GitHub Flow** 사용 (간소화된 워크플로우)
- `main`: 프로덕션 배포 브랜치 (보호됨)
- `feature/<name>`: 기능 개발
- `fix/<name>`: 버그 수정
- `hotfix/<name>`: 긴급 수정

#### 작업 흐름
```bash
# 1. 최신 main으로 동기화
git checkout main && git pull origin main

# 2. 작업 브랜치 생성
git checkout -b feature/<기능명>

# 3. 작업 후 커밋 (Conventional Commits)
git commit -m "feat(scope): 설명"

# 4. 푸시 및 PR 생성
git push origin feature/<기능명>
```

#### 커밋 메시지 (Conventional Commits)
- `feat(auth): add JWT login` - 새 기능
- `fix(animal): resolve null pointer` - 버그 수정
- `docs(readme): update setup guide` - 문서
- `refactor(api): extract helper function` - 리팩토링
- `chore(deps): update spring boot` - 빌드/설정

#### Pull Request 규칙
- ✅ 모든 코드 변경은 **PR 필수** (main 직접 푸시 금지)
- ✅ **최소 1명 승인** 후 머지
- ✅ **CI 통과** 확인 (자동 린트, 테스트, 빌드)
- ✅ PR 템플릿 작성 (변경사항, 테스트 방법, 체크리스트)

#### 코드 리뷰
- CODEOWNERS 파일에 따라 자동으로 리뷰어 지정
- 보안, 성능, 테스트 커버리지 확인
- 건설적인 피드백 제공

**자세한 내용**: [Git 워크플로우 완전 가이드](docs/GIT_WORKFLOW.md) | [충돌 해결 가이드](docs/GIT_CONFLICT_POSTMORTEM.md)
