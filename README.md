# 🐶 유기동물 입양/임보 AI 매칭 플랫폼 (62dn-project-2026)

이 프로젝트는 유기동물과 새로운 가족을 연결해주는 AI 기반 매칭 플랫폼입니다. 사용자 맞춤형 추천 시스템을 통해 유기동물 입양 및 임시 보호 활성화를 목표로 합니다.

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
├── docs/             # 프로젝트 문서 및 설계 자료 ( [sql.txt](docs/sql.txt) )
└── README.md         # 메인 설명 파일
```

## 🚀 Getting Started

### Prerequisites
- JDK 21 이상
- Node.js 18 이상
- MySQL 8.x 이상

### Backend Setup
1. `backend` 디렉토리로 이동합니다.
2. `src/main/resources/application.properties` (또는 `.yml`)에서 데이터베이스 설정을 확인합니다.
3. 다음 명령어를 실행하여 서버를 시작합니다:
   ```bash
   ./gradlew bootRun
   ```
4. Swagger UI: `http://localhost:8080/swagger-ui/index.html` 에서 API 명세를 확인할 수 있습니다.

### Frontend Setup
1. `frontend` 디렉토리로 이동합니다.
2. 의존성 패키지를 설치합니다:
   ```bash
   npm install
   ```
3. 로컬 개발 서버를 실행합니다:
   ```bash
   npm run dev
   ```
4. 브라우저에서 `http://localhost:5173` 으로 접속합니다.

### Database Setup
1. MySQL 서버에 접속합니다.
2. [docs/sql.txt](docs/sql.txt) 파일의 내용을 실행하여 데이터베이스(`DN_Platform`)와 필요한 테이블들을 생성합니다.

#### 주요 테이블 명세
| 테이블명 | 설명 |
| :--- | :--- |
| **users** | 회원 정보 및 권한 (USER, MANAGER) 관리 |
| **animals** | 보호 중인 유기동물 상세 프로필 (공공데이터 API 연동) |
| **shelters** | 보호소 정보 및 담당 관리자 매칭 |
| **adoptions** | 입양 및 임시보호 신청/승인 프로세스 관리 |
| **boards** | 자유게시판, 공지사항, 입양 후기 등 커뮤니티 |
| **volunteers** | 봉사 모집 및 신청 내역 관리 |
| **donations** | 물품/현금 후원 신청 및 내역 관리 |

## ✨ Key Features
- **AI 매칭**: 설문 데이터를 기반으로 사용자에게 가장 적합한 유기동물 추천
- **입양/임보 관리**: 입양 신청 및 승인 프로세스 관리
- **성공 사례**: 실제 입양 성공 사례 공유 및 커뮤니티
- **반응형 디자인**: 모바일과 데스크탑 모두 최적화된 UX/UI

## 🤝 협업 가이드
- **Branch Strategy**: Git Flow 또는 GitHub Flow 전략을 따릅니다.
- **Commit Message**: `feat:`, `fix:`, `docs:`, `style:`, `refactor:`, `test:`, `chore:` 접두사를 사용합니다.
- **Pull Request**: 모든 코드 변경은 PR을 통해 리뷰를 거친 후 병합됩니다.
