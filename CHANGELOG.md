# Changelog

모든 주목할만한 변경사항이 이 파일에 기록됩니다.

형식은 [Keep a Changelog](https://keepachangelog.com/ko/1.0.0/)를 따르며,
이 프로젝트는 [Semantic Versioning](https://semver.org/lang/ko/)을 준수합니다.

## [0.0.3-SNAPSHOT] - 2026-02-06

### Fixed

- **Frontend**: 관리자 대시보드(`AdminDashboardPage.tsx`) 리팩토링 및 기능 복구
  - 중복 코드 제거 및 이벤트 핸들러(승인, 동기화, 이메일 등) 재구현
  - 전역적인 타입 에러 및 `any` 관련 린트 에러 수정 (`BoardsPage`, `DonationsPage` 등)
  - 빌드 및 린트 블로커 전면 해결
- **Backend**: `DonationServiceTest.java` 테스트 에러 수정
  - 하드코딩된 ID 제거 및 동적 ID 사용, 테스트 데이터 초기화 로직 추가로 테스트 통과 보장

## [0.0.2-SNAPSHOT] - 2026-02-05

### Added

- 백엔드 문서화 파일 추가: `DATABASE.md`, `DEPLOYMENT_CHECKLIST.md`, `INTEGRATION_CHECK.md`, `PUBLIC_API.md`
- REST API 기반 백엔드 기능 구현 (Thymeleaf 미사용)
- 전역 예외 처리(`GlobalExceptionHandler`) 및 에러 응답(`ErrorResponse`) 구현
- 로컬 파일 업로드 및 관리(`FileStorageService`, `FileController`) 구현
- 동물 관리(`AnimalController`, `AnimalService`) CRUD 및 필터링 기능 구현
- 입양/임보 신청(`AdoptionController`, `AdoptionService`) 프로세스 및 상태 관리 구현
- 통합 게시판(`BoardController`, `BoardService`) 및 댓글(`CommentService`) 기능 구현

### Changed

- GitHub Workflow 수정: `auto-push-pr.yml` (자동 푸시 및 PR 생성 로직 최적화)
- VSCode 설정 업데이트: `.vscode/settings.json` (Java 컴파일 및 빌드 설정 변경)

### Fixed

- 프론트엔드 CI 빌드 및 Lint 오류 수정

## [0.0.1-SNAPSHOT] - 2026-02-04

### Added

- 프로젝트 초기 설정
- Spring Boot 3.4.2 백엔드 기본 구조
- React 18.2 프론트엔드 기본 구조
- MySQL 데이터베이스 스키마
- EC2 배포 워크플로우
- JWT 인증 시스템
- 유기동물 데이터 관리 API

---

## 변경 유형 (Change Types)

- `Added`: 새로운 기능
- `Changed`: 기존 기능 변경
- `Deprecated`: 곧 제거될 기능
- `Removed`: 제거된 기능
- `Fixed`: 버그 수정
- `Security`: 보안 관련 변경
