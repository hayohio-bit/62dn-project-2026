# Git 기반 프로젝트 표준 가이드
## 62댕냥이 플랫폼 (dn-project)

**작성일**: 2026-02-04  
**대상 프로젝트**: dn-project (유기동물 입양/임보 매칭 플랫폼)  
**버전**: 1.0

---

## 📋 필수 입력값 확인

### 확인된 정보
- **프로젝트 이름**: dn-project
- **레포지토리**: https://github.com/hayohio-bit/62dn-project-2026
- **호스팅**: GitHub
- **스택**: 
  - Backend: Spring Boot 3.4.2, Java 21, MySQL
  - Frontend: React 18.2, TypeScript, Vite
- **배포 대상**: EC2 (VM) + RDS (MySQL)
- **환경**: dev (로컬), prod (EC2)
- **팀**: 총 4명

### 가정 (Assumptions)

**A1. GitHub + GitHub Actions 기본**  
GitHub를 주 호스팅으로 하며, CI/CD는 GitHub Actions를 사용합니다.

**A2. Conventional Commits 형식**  
커밋 메시지는 `<type>[scope]: <description>` 형식을 따릅니다.  
예: `feat(auth): add JWT login`, `fix(animal): resolve null pointer`

**A3. CODEOWNERS + 브랜치 보호 규칙**  
코드 소유/리뷰를 위해 CODEOWNERS 파일을 사용하고, `main` 브랜치는 PR 기반 변경만 허용합니다.

**A4. 환경 구분**  
- `dev`: 로컬 개발 환경 (localhost:8080, localhost:5173)
- `prod`: EC2/RDS 프로덕션 환경 (자동 배포)

**A5. 시크릿 관리**  
모든 시크릿은 GitHub Secrets에 저장하며, 평문으로 코드에 포함하지 않습니다.

---

## 1. 🎯 목표 및 범위

### 1.1 문서 목표

이 문서의 목표는 **"이 가이드만 따라하면 dn-project 팀이 저장소 생성부터 운영 배포까지 막힘없이 진행"** 할 수 있도록 하는 것입니다.

**핵심 원칙**:
- ✅ **실행 가능성**: 모든 단계를 체크리스트로 제공, 클릭 한 번으로 실행 가능한 명령어
- 🔒 **보안 우선**: 최소 권한 원칙, 시크릿 분리, 승인 게이트
- 👥 **협업 최적화**: 4인 팀 특성에 맞는 워크플로우 (소규모 팀이므로 과도한 프로세스 지양)
- 📚 **지식 공유**: 30분 온보딩으로 신규 팀원도 즉시 기여 가능

### 1.2 적용 범위

| 항목          | 범위                    |
| ----------- | --------------------- |
| **Git 호스팅** | GitHub (primary)      |
| **CI/CD**   | GitHub Actions        |
| **브랜치 전략**  | GitHub Flow (간소화)     |
| **코드 리뷰**   | Pull Request 필수       |
| **배포 환경**   | dev (로컬) → prod (EC2) |
| **자동화**     | 린트, 테스트, 빌드, 배포       |

---

## 2. 🏗️ 저장소 생성 & 초기 세팅

### 2.1 저장소 생성 체크리스트

> dn-project는 이미 생성되어 있으므로, 신규 프로젝트 시 참고용으로 사용하세요.

- [ ] GitHub에서 조직(Organization) 또는 개인 계정으로 레포지토리 생성
  ```
  Repository name: <REPO_NAME>
  Visibility: Private (권장) 또는 Public
  Initialize: Add README, Add .gitignore (Java/Node 선택)
  ```
- [ ] 기본 브랜치를 `main`으로 설정 (Settings → Branches → Default branch)
- [ ] 리포지토리 Description 작성 및 Topics 추가 (검색 용이성)
- [ ] 로컬에 클론
  ```bash
  git clone https://github.com/<ORG>/<REPO_NAME>.git
  cd <REPO_NAME>
  ```

### 2.2 필수 디렉토리 구조

dn-project 기준 표준 구조:

```
dn-project/
├── .github/                   # GitHub 관련 설정
│   ├── ISSUE_TEMPLATE/        # 이슈 템플릿
│   ├── PULL_REQUEST_TEMPLATE.md  # PR 템플릿
│   ├── CODEOWNERS             # 코드 소유자
│   └── workflows/             # GitHub Actions
│       ├── ci.yml             # CI 워크플로우
│       └── deploy.yml         # CD 워크플로우
├── backend/                   # 백엔드 코드
│   ├── src/
│   ├── build.gradle
│   └── .env.example
├── frontend/                  # 프론트엔드 코드
│   ├── src/
│   ├── package.json
│   └── .env.example
├── docs/                      # 문서
│   ├── GIT_WORKFLOW_GUIDE.md  # 이 문서
│   ├── API.md
│   └── sql.txt
├── .gitignore                 # Git 제외 파일
├── README.md                  # 프로젝트 소개
└── LICENSE                    # 라이선스
```

### 2.3 .gitignore 설정

**중요**: IDE 설정, 빌드 산출물, 로컬 환경 파일은 절대 커밋하지 않습니다.

```gitignore
# IDE
.idea/
.vscode/
*.iml
*.iws
*.ipr

# Build outputs
backend/build/
backend/out/
backend/target/
frontend/dist/
frontend/build/

# Dependencies
node_modules/
.gradle/

# Environment
.env
.env.local
*.log

# OS
.DS_Store
Thumbs.db

# Secrets (절대 커밋 금지)
**/secrets/
*.pem
*.key
```

### 2.4 README.md 작성 가이드

README는 프로젝트의 얼굴입니다. 다음 섹션을 포함하세요:

- [ ] 프로젝트 소개 (1-2문장 + 배지)
- [ ] Tech Stack
- [ ] Project Structure
- [ ] Getting Started (Prerequisites, Setup, Running)
- [ ] Key Features
- [ ] Contributing Guide (브랜치 전략, 커밋 규칙)
- [ ] 라이선스

**참고**: 현재 README.md는 잘 작성되어 있습니다. ([링크](file:///d:/dn-project/README.md))

### 2.5 필수 템플릿 파일 생성

다음 섹션에서 제공되는 템플릿을 `.github/` 디렉토리에 추가하세요:

- [ ] `.github/ISSUE_TEMPLATE/bug_report.md`
- [ ] `.github/ISSUE_TEMPLATE/feature_request.md`
- [ ] `.github/PULL_REQUEST_TEMPLATE.md`
- [ ] `.github/CODEOWNERS`

> 템플릿 내용은 **섹션 10 부록**에서 확인하세요.

---

## 3. 🌿 브랜치 전략 & 작업 흐름

### 3.1 브랜치 전략: GitHub Flow (권장)

**선택 이유**: 4인 소규모 팀이므로 Git Flow는 과도하게 복잡합니다. GitHub Flow로 단순화하되, 필수 품질 게이트는 유지합니다.

### 3.2 브랜치 구조

| 브랜치 | 용도 | 보호 여부 | 수명 |
|--------|------|-----------|------|
| `main` | 프로덕션 배포 브랜치 | ✅ 보호 | 영구 |
| `feature/<NAME>` | 기능 개발 | ❌ | 단기 (머지 후 삭제) |
| `fix/<NAME>` | 버그 수정 | ❌ | 단기 |
| `hotfix/<NAME>` | 긴급 수정 | ❌ | 단기 |

**명명 규칙 예시**:
- `feature/user-auth`
- `feature/animal-recommendation`
- `fix/null-pointer-adoption`
- `hotfix/security-jwt-leak`

### 3.3 작업 흐름 (Workflow)

#### 3.3.1 신규 작업 시작

```bash
# 1. 최신 main 브랜치로 동기화
git checkout main
git pull origin main

# 2. 작업 브랜치 생성
git checkout -b feature/<FEATURE_NAME>

# 3. 코드 작성...

# 4. 커밋 (Conventional Commits)
git add .
git commit -m "feat(<scope>): <description>"

# 예시
git commit -m "feat(auth): add JWT login endpoint"
git commit -m "fix(animal): resolve null pointer in recommendation"
```

#### 3.3.2 중간 동기화 (권장)

작업이 길어질 경우 main 브랜치 변경사항을 주기적으로 반영하세요:

```bash
# 내 작업 브랜치에서
git fetch origin main
git merge origin/main

# 또는 (히스토리를 깔끔하게 유지하려면)
git rebase origin/main
```

#### 3.3.3 Pull Request 생성

```bash
# 1. 원격에 푸시
git push origin feature/<FEATURE_NAME>

# 2. GitHub에서 PR 생성
# - Base: main
# - Compare: feature/<FEATURE_NAME>
# - Template 자동 적용됨
```

#### 3.3.4 코드 리뷰 & 머지

- [ ] PR 생성 후 팀원에게 리뷰 요청
- [ ] CI 통과 확인 (자동)
- [ ] 최소 1명 승인 (CODEOWNERS 기준)
- [ ] "Squash and merge" 또는 "Merge commit" (팀 합의 필요)
- [ ] 머지 후 작업 브랜치 삭제

```bash
# 로컬 브랜치 삭제
git branch -d feature/<FEATURE_NAME>

# 원격 브랜치 삭제 (GitHub UI에서 자동 삭제 권장)
git push origin --delete feature/<FEATURE_NAME>
```

### 3.4 커밋 메시지 규칙 (Conventional Commits)

**형식**:
```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```


**Type 종류**:

| Type     | 설명              | 예시                                       |
| -------- | --------------- | ---------------------------------------- |
| feat     | 새 기능            | feat(auth): add social login             |
| fix      | 버그 수정           | fix(animal): resolve image load error    |
| docs     | 문서 변경           | docs(readme): update setup guide         |
| style    | 코드 포맷(로직 변경 없음) | style(backend): apply prettier           |
| refactor | 리팩토링            | refactor(api): extract helper function   |
| test     | 테스트 추가/수정       | test(user): add login unit test          |
| chore    | 빌드/설정           | chore(deps): update spring boot to 3.4.2 |

---

## 4. 🛡️ 품질 게이트 (Quality Gates)

### 4.1 브랜치 보호 규칙 설정

GitHub Repository Settings → Branches → Add rule:

#### 4.1.1 `main` 브랜치 보호 (필수)

- [ ] **Branch name pattern**: `main`
- [ ] ✅ **Require a pull request before merging**
  - [ ] ✅ Require approvals: `1` (최소)
  - [ ] ✅ Dismiss stale pull request approvals when new commits are pushed
- [ ] ✅ **Require status checks to pass before merging**
  - [ ] ✅ Require branches to be up to date before merging
  - [ ] Status checks: `CI` (workflow name)
- [ ] ✅ **Require conversation resolution before merging**
- [ ] ✅ **Require linear history** (선택사항, Squash merge 사용 시 불필요)
- [ ] ✅ **Do not allow bypassing the above settings** (Git 마스터도 PR 필수)
- [ ] ✅ **Restrict who can push to matching branches**
  - Allowed: GitHub Actions (자동 배포용)

### 4.2 CODEOWNERS 설정

`.github/CODEOWNERS` 파일로 코드 영역별 책임자를 지정합니다.

**파일 내용** (dn-project 기준):

```codeowners
# Global owners (fallback)
* @<GIT_MASTER_USERNAME>

# Backend
/backend/ @<GIT_MASTER_USERNAME> @<BACKEND_DEV1> @<BACKEND_DEV2>
/backend/src/main/java/com/dnproject/platform/domain/user/ @<GIT_MASTER_USERNAME>
/backend/src/main/java/com/dnproject/platform/domain/animal/ @<BACKEND_DEV1>
/backend/src/main/java/com/dnproject/platform/security/ @<GIT_MASTER_USERNAME>

# Frontend
/frontend/ @<FRONTEND_DEV>
/frontend/src/components/auth/ @<FRONTEND_DEV>

# Infrastructure
/.github/workflows/ @<GIT_MASTER_USERNAME>
/docs/ @<GIT_MASTER_USERNAME>

# Database
/docs/sql.txt @<GIT_MASTER_USERNAME>
```

**동작 방식**:
- PR 생성 시 변경된 파일의 CODEOWNER가 자동으로 리뷰어로 지정됨
- CODEOWNER의 승인 없이는 머지 불가 (브랜치 보호 규칙 설정 시)

### 4.3 Pull Request 체크리스트

PR을 생성하기 전에 다음을 확인하세요:

**PR 생성 전**:
- [ ] 로컬에서 빌드 성공 (`./gradlew build`, `npm run build`)
- [ ] 로컬에서 테스트 통과 (`./gradlew test`, `npm run test`)
- [ ] 린트 오류 없음 (`./gradlew check`, `npm run lint`)
- [ ] `.env` 파일은 제외되었는지 확인
- [ ] 커밋 메시지가 Conventional Commits 형식인지 확인
- [ ] 불필요한 파일(`.idea/`, `node_modules/` 등) 제외 확인

**PR 생성 시**:
- [ ] PR 제목이 명확한지 (예: `feat(auth): Add OAuth2.0 login`)
- [ ] PR 설명에 변경사항, 테스트 방법, 스크린샷(UI 변경 시) 포함
- [ ] 관련 이슈 링크 (`Closes #123`)
- [ ] Draft PR로 시작하고, 준비되면 "Ready for review"로 변경

**리뷰어 체크리스트**:
- [ ] 코드 품질: 가독성, 일관성, 베스트 프랙티스 준수
- [ ] 보안: SQL 인젝션, XSS, 시크릿 노출 여부
- [ ] 테스트: 새 기능에 테스트 추가 여부
- [ ] 문서: API 변경 시 문서 업데이트 여부
- [ ] 성능: N+1 쿼리, 불필요한 반복문 등

---

## 5. 🔧 CI 설계 (Continuous Integration)

### 5.1 CI 목표

- **자동화**: 모든 PR에서 린트, 테스트, 빌드 자동 실행
- **빠른 피드백**: 5분 이내 결과 확인
- **일관성**: 로컬과 동일한 환경

### 5.2 CI 워크플로우 구조

`.github/workflows/ci.yml`:

```yaml
name: CI

on:
  pull_request:
    branches: [main]
  push:
    branches: [main]

jobs:
  backend-ci:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Set up JDK 21
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'
          cache: 'gradle'

      - name: Grant execute permission for gradlew
        run: chmod +x backend/gradlew

      - name: Lint & Test
        run: |
          cd backend
          ./gradlew clean check test

      - name: Build
        run: |
          cd backend
          ./gradlew bootJar -x test

      - name: Upload artifacts (optional)
        uses: actions/upload-artifact@v3
        with:
          name: backend-jar
          path: backend/build/libs/*.jar
          retention-days: 7

  frontend-ci:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Set up Node.js
        uses: actions/setup-node@v4
        with:
          node-version: '20'
          cache: 'npm'
          cache-dependency-path: frontend/package-lock.json

      - name: Install dependencies
        run: |
          cd frontend
          npm ci

      - name: Lint
        run: |
          cd frontend
          npm run lint

      - name: Build
        env:
          VITE_API_BASE_URL: /api
          VITE_MAP_API_KEY: dummy-key-for-build
        run: |
          cd frontend
          npm run build

      - name: Upload artifacts (optional)
        uses: actions/upload-artifact@v3
        with:
          name: frontend-dist
          path: frontend/dist/
          retention-days: 7
```

### 5.3 CI 단계별 설명

| 단계 | Backend | Frontend | 실패 시 조치 |
|------|---------|----------|--------------|
| **Checkout** | ✅ | ✅ | - |
| **환경 설정** | JDK 21 | Node.js 20 | 버전 확인 |
| **의존성 설치** | Gradle 캐시 | `npm ci` | `package-lock.json` 확인 |
| **Lint** | `./gradlew check` | `npm run lint` | 린트 오류 수정 |
| **Test** | `./gradlew test` | `npm run test` | 테스트 수정 |
| **Build** | `./gradlew bootJar` | `npm run build` | 빌드 오류 수정 |
| **Artifact 업로드** | JAR 파일 | dist 폴더 | - |

### 5.4 로컬에서 CI 재현

CI 실패 시 로컬에서 동일한 명령어를 실행하여 디버깅하세요:

**Backend**:
```bash
cd backend
./gradlew clean check test bootJar
```

**Frontend**:
```bash
cd frontend
npm ci
npm run lint
npm run build
```

---

## 6. 🚀 CD 설계 (Continuous Deployment)

### 6.1 CD 목표

- **자동 배포**: `main` 브랜치 머지 시 자동으로 프로덕션 배포
- **승인 게이트**: 중요 변경 시 수동 승인 필요 (선택사항)
- **시크릿 보안**: 환경변수는 GitHub Secrets로 관리
- **롤백 가능**: 배포 실패 시 즉시 이전 버전으로 복구

### 6.2 CD 워크플로우 (현재 구성)

현재 `.github/workflows/deploy.yml`은 다음과 같이 동작합니다:

1. `main` 브랜치에 푸시 시 트리거
2. Backend JAR 빌드
3. Frontend 정적 파일 빌드
4. EC2에 SCP로 배포
5. Systemd 서비스 재시작

**참고**: 전체 워크플로우는 [deploy.yml](file:///d:/dn-project/.github/workflows/deploy.yml)을 확인하세요.

### 6.3 환경별 배포 전략

| 환경 | 트리거 | 승인 필요 | 배포 대상 |
|------|--------|-----------|-----------|
| **dev** | PR 생성 시 (선택) | ❌ | Dev 서버 또는 로컬 |
| **prod** | `main` 머지 시 | ✅ (권장) | EC2 프로덕션 |

### 6.4 시크릿 설정

GitHub Repository → Settings → Secrets and variables → Actions → New repository secret:

#### 6.4.1 필수 시크릿 목록

| Secret Name | 설명 | 예시 값 |
|-------------|------|---------|
| `EC2_HOST` | EC2 퍼블릭 IP | `<EC2_PUBLIC_IP>` |
| `EC2_SSH_KEY` | SSH Private Key | `-----BEGIN RSA PRIVATE KEY-----...` |
| `RDS_ENDPOINT` | MySQL 엔드포인트 | `<RDS_ENDPOINT>:3306` |
| `RDS_USERNAME` | DB 사용자명 | `<DB_USERNAME>` |
| `RDS_PASSWORD` | DB 비밀번호 | `<DB_PASSWORD>` |
| `JWT_SECRET` | JWT 서명 키 (32자 이상) | `<RANDOM_32CHAR_STRING>` |
| `VITE_MAP_API_KEY` | 카카오맵 API 키 | `<KAKAO_API_KEY>` |
| `DATA_API_KEY` | 공공데이터 API 키 | `<PUBLIC_DATA_KEY>` |

#### 6.4.2 시크릿 등록 방법

```bash
# 예시: SSH 키 등록
# 1. 로컬에서 키 생성 (이미 있으면 생략)
ssh-keygen -t rsa -b 4096 -C "deploy-key"

# 2. Private key 내용을 복사
cat ~/.ssh/id_rsa

# 3. GitHub에서 New secret → Name: EC2_SSH_KEY, Value: (복사한 내용 붙여넣기)
```

### 6.5 승인 게이트 설정 (선택사항)

프로덕션 배포 전 수동 승인이 필요한 경우:

1. GitHub Repository → Settings → Environments → New environment
2. Environment name: `production`
3. ✅ **Required reviewers**: Git 마스터 선택
4. `deploy.yml` 수정:

```yaml
jobs:
  build-and-deploy:
    runs-on: ubuntu-latest
    environment: production  # 추가
    steps:
      # ... (기존 내용)
```

**동작**:
- `main` 머지 후 워크플로우가 일시 중지
- Git 마스터에게 알림 발송
- 승인 후 배포 진행

### 6.6 배포 대상별 가이드

#### 6.6.1 EC2 (현재 구성)

**초기 서버 설정** (최초 1회):

```bash
# EC2에 SSH 접속
ssh -i <KEY_FILE>.pem ec2-user@<EC2_HOST>

# Java 21 설치
sudo yum install -y java-21-amazon-corretto

# Nginx 설치
sudo amazon-linux-extras install nginx1 -y
sudo systemctl enable nginx
sudo systemctl start nginx

# 애플리케이션 디렉토리 생성
mkdir -p /home/ec2-user/app
```

**수동 배포 테스트** (GitHub Actions 전 확인용):

```bash
# 로컬에서 빌드
cd backend && ./gradlew bootJar
cd ../frontend && npm run build

# SCP로 전송
scp backend/build/libs/*.jar ec2-user@<EC2_HOST>:/home/ec2-user/app/
scp -r frontend/dist/* ec2-user@<EC2_HOST>:/var/www/dn-platform/

# EC2에서 재시작
ssh ec2-user@<EC2_HOST>
sudo systemctl restart dn-platform
```

#### 6.6.2 Docker (선택사항)

Docker를 사용하려면 다음을 참고하세요:

**Backend Dockerfile**:
```dockerfile
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY build/libs/platform-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Frontend Dockerfile**:
```dockerfile
FROM nginx:alpine
COPY dist/ /usr/share/nginx/html/
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
```

**docker-compose.yml**:
```yaml
version: '3.8'
services:
  backend:
    build: ./backend
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://<RDS_ENDPOINT>/dn_platform
      SPRING_DATASOURCE_USERNAME: <DB_USERNAME>
      SPRING_DATASOURCE_PASSWORD: <DB_PASSWORD>

  frontend:
    build: ./frontend
    ports:
      - "80:80"
    depends_on:
      - backend
```

---

## 7. 🏷️ 릴리즈/버저닝/태깅

### 7.1 버저닝 전략: Semantic Versioning

**형식**: `MAJOR.MINOR.PATCH` (예: `1.2.3`)

- **MAJOR**: 호환성 깨지는 변경 (API 스펙 변경)
- **MINOR**: 하위 호환 기능 추가
- **PATCH**: 버그 수정

### 7.2 릴리즈 프로세스

#### 7.2.1 릴리즈 준비 체크리스트

- [ ] 모든 PR이 `main`에 머지되었는지 확인
- [ ] CI/CD가 모두 통과했는지 확인
- [ ] CHANGELOG.md 업데이트 (변경사항 요약)
- [ ] 버전 번호 결정 (Semantic Versioning 기준)
- [ ] 마이그레이션 스크립트 확인 (DB 스키마 변경 시)

#### 7.2.2 Git 태그 생성

```bash
# 1. 최신 main으로 이동
git checkout main
git pull origin main

# 2. 태그 생성 (annotated tag 권장)
git tag -a v1.2.3 -m "Release v1.2.3: Add animal recommendation feature"

# 3. 태그 푸시
git push origin v1.2.3
```

#### 7.2.3 GitHub Release 생성

1. GitHub Repository → Releases → Draft a new release
2. **Tag**: `v1.2.3` 선택
3. **Release title**: `v1.2.3 - Animal Recommendation`
4. **Description**: CHANGELOG에서 복사 + 주요 변경사항
5. **Assets**: JAR 파일, Docker 이미지 등 (선택)
6. **Publish release**

### 7.3 CHANGELOG.md 예시

```markdown
# Changelog

## [1.2.3] - 2026-02-10

### Added
- Animal recommendation algorithm based on user survey
- Email notification on adoption approval

### Changed
- Improve API response time by 30% (optimized N+1 queries)

### Fixed
- Fix null pointer exception in AnimalController
- Resolve JWT token expiration edge case

## [1.2.2] - 2026-02-03

### Fixed
- Critical security fix: SQL injection in search endpoint
```

### 7.4 핫픽스 프로세스

긴급 버그 수정 시:

```bash
# 1. 최신 릴리즈 태그에서 브랜치 생성
git checkout -b hotfix/security-fix v1.2.3

# 2. 수정 및 커밋
git commit -m "fix(security): patch SQL injection vulnerability"

# 3. 태그 생성 (PATCH 버전 증가)
git tag -a v1.2.4 -m "Hotfix v1.2.4: Security patch"

# 4. main에 머지 + 태그 푸시
git checkout main
git merge hotfix/security-fix
git push origin main
git push origin v1.2.4
```

---

## 8. 🔧 운영 가이드

### 8.1 모니터링 & 알림

#### 8.1.1 기본 모니터링 (EC2)

**로그 확인**:
```bash
ssh ec2-user@<EC2_HOST>

# 백엔드 로그
tail -f /home/ec2-user/log.out
tail -f /home/ec2-user/err.out

# Nginx 로그
sudo tail -f /var/log/nginx/access.log
sudo tail -f /var/log/nginx/error.log

# Systemd 서비스 상태
sudo systemctl status dn-platform
```

**헬스체크**:
```bash
# 백엔드 health endpoint (Spring Boot Actuator 추가 시)
curl http://<EC2_HOST>/actuator/health

# 프론트엔드 접근 테스트
curl -I http://<EC2_HOST>
```

#### 8.1.2 GitHub Actions 알림

Slack/Discord 통합:

```yaml
# .github/workflows/deploy.yml 마지막에 추가
- name: Notify Slack
  if: always()
  uses: slackapi/slack-github-action@v1
  with:
    webhook-url: ${{ secrets.SLACK_WEBHOOK_URL }}
    payload: |
      {
        "text": "Deployment ${{ job.status }}: ${{ github.event.head_commit.message }}"
      }
```

### 8.2 장애 대응

#### 8.2.1 롤백 경로 1: Git Revert (권장)

```bash
# 1. 문제가 있는 커밋 식별
git log --oneline -10

# 2. 해당 커밋 되돌리기 (새 커밋 생성)
git revert <BAD_COMMIT_HASH>

# 3. 푸시 (자동 배포 트리거)
git push origin main
```

#### 8.2.2 롤백 경로 2: 이전 태그 재배포

```bash
# 1. 이전 안정 버전 태그 확인
git tag -l

# 2. 해당 태그로 체크아웃
git checkout v1.2.2

# 3. 임시 브랜치 생성 후 main에 강제 머지 (주의!)
git checkout -b rollback-to-v1.2.2
git push origin rollback-to-v1.2.2

# 4. GitHub에서 PR 생성 후 긴급 머지
```

#### 8.2.3 수동 롤백 (EC2)

```bash
ssh ec2-user@<EC2_HOST>

# 1. 이전 JAR 파일로 교체 (백업이 있는 경우)
cp /home/ec2-user/backup/platform-v1.2.2.jar /home/ec2-user/app/platform-0.0.1-SNAPSHOT.jar

# 2. 서비스 재시작
sudo systemctl restart dn-platform

# 3. 상태 확인
sudo systemctl status dn-platform
```

### 8.3 일반적인 장애 시나리오

| 장애 | 증상 | 원인 | 해결 |
|------|------|------|------|
| **502 Bad Gateway** | Nginx 접속 시 502 오류 | 백엔드 서비스 중지 | `sudo systemctl restart dn-platform` |
| **DB 연결 실패** | `err.out`에 Connection refused | RDS 시크릿 오류 또는 네트워크 | 시크릿 확인, Security Group 확인 |
| **JWT 인증 실패** | 401 Unauthorized | JWT 시크릿 불일치 | `JWT_SECRET` 시크릿 확인 |
| **프론트 빌드 실패** | Vite build error | `VITE_*` 환경변수 누락 | GitHub Secrets 확인 |

### 8.4 정기 유지보수

- [ ] **주간**: 로그 파일 용량 확인 (`du -sh /home/ec2-user/*.out`)
- [ ] **월간**: 의존성 보안 패치 확인 (`npm audit`, `./gradlew dependencyUpdates`)
- [ ] **분기**: EC2 디스크 용량 확인, RDS 백업 정책 검토

---

## 9. 🎓 온보딩 가이드 (30분 코스)

### 9.1 신규 팀원이 처음 할 일

#### 0-10분: 환경 준비

- [ ] Git, JDK 21, Node.js 20 설치 확인
- [ ] GitHub 계정 레포지토리 접근 권한 확인 (Git 마스터에게 요청)
- [ ] 레포지토리 클론
  ```bash
  git clone https://github.com/hayohio-bit/62dn-project-2026.git
  cd 62dn-project-2026
  ```

#### 10-20분: 로컬 실행

- [ ] Backend `.env` 설정 (`.env.example` 복사)
- [ ] Frontend `.env` 설정
- [ ] MySQL 접속 및 `docs/sql.txt` 실행
- [ ] Backend 실행: `cd backend && ./gradlew bootRun`
- [ ] Frontend 실행: `cd frontend && npm install && npm run dev`
- [ ] 브라우저에서 http://localhost:5173 접속 확인

#### 20-30분: 첫 PR 만들기

- [ ] 간단한 수정 (예: README 오타 수정)
- [ ] 브랜치 생성: `git checkout -b fix/readme-typo`
- [ ] 커밋: `git commit -m "docs(readme): fix typo in setup guide"`
- [ ] 푸시 및 PR 생성
- [ ] CI 통과 확인

### 9.2 역할별 온보딩 체크리스트

**백엔드 개발자**:
- [ ] Spring Boot 프로젝트 구조 이해 (`backend/src` 탐색)
- [ ] JPA 엔티티 관계 파악 (`docs/sql.txt` 참고)
- [ ] Swagger UI 접속 (http://localhost:8080/swagger-ui/index.html)
- [ ] 첫 API 엔드포인트 작성 (예: `/api/test`)

**프론트엔드 개발자**:
- [ ] React 컴포넌트 구조 이해 (`frontend/src` 탐색)
- [ ] API 연동 방법 파악 (`frontend/API_INTEGRATION_GUIDE.md`)
- [ ] Tailwind CSS 설정 확인
- [ ] 첫 컴포넌트 수정 (예: 버튼 텍스트 변경)

**Git 마스터**:
- [ ] CODEOWNERS 파일 업데이트 (팀원 GitHub ID 추가)
- [ ] 브랜치 보호 규칙 설정 확인
- [ ] GitHub Secrets 설정 확인
- [ ] CI/CD 워크플로우 테스트

### 9.3 온보딩 완료 기준

- ✅ 로컬에서 앱 실행 성공
- ✅ 첫 PR 생성 및 머지 완료
- ✅ 팀 협업 방식 이해 (브랜치 전략, 커밋 규칙)
- ✅ Git 충돌 해결 방법 숙지 ([GIT_CONFLICT_POSTMORTEM.md](file:///d:/dn-project/docs/GIT_CONFLICT_POSTMORTEM.md) 참고)

---

## 10. 📚 부록

### 10.1 템플릿 모음

#### 10.1.1 Bug Report Template

`.github/ISSUE_TEMPLATE/bug_report.md`:

```markdown
---
name: Bug Report
about: 버그 제보
title: '[BUG] '
labels: bug
assignees: ''
---

## 🐛 버그 설명
명확하고 간결한 버그 설명을 작성해주세요.

## 📋 재현 단계
1. '...'로 이동
2. '...'를 클릭
3. '...'까지 스크롤
4. 오류 발생 확인

## ✅ 예상 동작
정상적으로 동작했을 때의 결과를 설명해주세요.

## 💥 실제 동작
실제로 발생한 오류를 설명해주세요.

## 🖼️ 스크린샷
(선택사항) 스크린샷을 추가해주세요.

## 🔧 환경
- OS: [예: Windows 11]
- Browser: [예: Chrome 120]
- Version: [예: v1.2.3]

## 📝 추가 정보
(선택사항) 추가적인 맥락을 제공해주세요.
```

#### 10.1.2 Feature Request Template

`.github/ISSUE_TEMPLATE/feature_request.md`:

```markdown
---
name: Feature Request
about: 새로운 기능 제안
title: '[FEATURE] '
labels: enhancement
assignees: ''
---

## 💡 기능 설명
명확하고 간결하게 제안하는 기능을 설명해주세요.

## 🎯 문제점
이 기능이 해결하는 문제가 무엇인가요?

## 📝 제안 내용
원하는 동작을 설명해주세요.

## 🔄 대안
고려한 다른 대안이 있나요?

## 📝 추가 정보
(선택사항) 추가적인 맥락이나 스크린샷을 제공해주세요.
```

#### 10.1.3 Pull Request Template

`.github/PULL_REQUEST_TEMPLATE.md`:

```markdown
## 📝 변경 사항
<!-- 이 PR에서 변경한 내용을 요약해주세요 -->

## 🎯 관련 이슈
<!-- Closes #123 형식으로 이슈 링크 -->
Closes #

## 🧪 테스트 방법
<!-- 리뷰어가 어떻게 테스트할 수 있는지 설명해주세요 -->

1. 
2. 
3. 

## 📸 스크린샷 (UI 변경 시)
<!-- Before/After 스크린샷 -->

## ✅ 체크리스트

- [ ] 로컬에서 빌드 성공
- [ ] 로컬에서 테스트 통과
- [ ] Commit message가 Conventional Commits 형식을 따름
- [ ] 변경사항에 대한 문서 업데이트 완료 (필요 시)
- [ ] Breaking change 여부 확인 (해당 시 MAJOR 버전 업)

## 💬 추가 정보
<!-- 리뷰어가 알아야 할 추가 정보 -->
```

### 10.2 트러블슈팅 TOP 10

#### 1. **Merge Conflict**

**증상**: `git merge` 또는 `git pull` 시 충돌 발생

**해결**:
```bash
# 1. 충돌 파일 확인
git status

# 2. 파일 열어서 수동 수정 (<<<<<<, ======, >>>>>> 부분)

# 3. 수정 후 스테이징
git add <CONFLICTED_FILE>

# 4. 머지 완료
git commit
```

**참고**: [GIT_CONFLICT_POSTMORTEM.md](file:///d:/dn-project/docs/GIT_CONFLICT_POSTMORTEM.md)

#### 2. **Permission Denied (SSH)**

**증상**: `git push` 시 `Permission denied (publickey)`

**해결**:
```bash
# SSH 키 생성
ssh-keygen -t rsa -b 4096 -C "your_email@example.com"

# 공개키 복사
cat ~/.ssh/id_rsa.pub

# GitHub Settings → SSH Keys → Add SSH key에 붙여넣기
```

#### 3. **CI Build Failure (Gradle)**

**증상**: GitHub Actions에서 `./gradlew build` 실패

**해결**:
- 로컬에서 `./gradlew clean build` 실행하여 재현
- `build.gradle` 의존성 버전 확인
- Java 버전 확인 (로컬 21, CI도 21인지)

#### 4. **Frontend Build Failure (Vite)**

**증상**: `npm run build` 실패

**해결**:
```bash
# node_modules 삭제 후 재설치
rm -rf node_modules package-lock.json
npm install

# TypeScript 오류 확인
npm run build -- --mode development
```

#### 5. **.gitignore Not Working**

**증상**: `.gitignore`에 추가했는데도 파일이 커밋됨

**해결**:
```bash
# 이미 추적 중인 파일은 캐시에서 제거 필요
git rm --cached <FILE_OR_FOLDER>
git commit -m "chore: remove ignored files from git tracking"
```

#### 6. **JWT Token Expiration**

**증상**: 로그인 후 갑자기 401 Unauthorized

**해결**:
- `JWT_ACCESS_VALIDITY` 값 확인 (초 단위, 기본 86400 = 24시간)
- Refresh Token 로직 구현 확인

#### 7. **DB Connection Failed**

**증상**: `err.out`에 `Connection refused` 또는 `Access denied`

**해결**:
- RDS Security Group에서 EC2 IP 허용 확인
- `RDS_ENDPOINT`, `RDS_USERNAME`, `RDS_PASSWORD` 시크릿 확인
- MySQL 클라이언트로 직접 접속 테스트
  ```bash
  mysql -h <RDS_ENDPOINT> -u <USERNAME> -p
  ```

#### 8. **GitHub Actions Secret Not Found**

**증상**: 워크플로우에서 `secret is not set`

**해결**:
- GitHub Repository → Settings → Secrets and variables → Actions 확인
- Secret 이름 대소문자 정확히 일치하는지 확인 (`EC2_HOST` ≠ `ec2_host`)

#### 9. **Port Already in Use**

**증상**: `./gradlew bootRun` 시 `Port 8080 already in use`

**해결**:
```bash
# 포트 사용 중인 프로세스 찾기 (macOS/Linux)
lsof -i :8080

# 프로세스 종료
kill -9 <PID>

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

#### 10. **Git Push Rejected (Branch Protection)**

**증상**: `main` 브랜치에 직접 푸시 시 거부됨

**해결**:
- 의도된 동작입니다! (보호 규칙)
- 반드시 PR을 통해 머지해야 합니다
- 긴급 상황 시 Git 마스터가 임시로 보호 규칙 해제 가능 (비권장)

---

### 10.3 유용한 Git 명령어 치트시트

```bash
# 브랜치 관리
git branch -a                      # 모든 브랜치 확인
git branch -d <BRANCH>             # 로컬 브랜치 삭제
git push origin --delete <BRANCH>  # 원격 브랜치 삭제

# 커밋 수정
git commit --amend                 # 마지막 커밋 메시지 수정
git reset --soft HEAD~1            # 마지막 커밋 취소 (변경사항 유지)
git reset --hard HEAD~1            # 마지막 커밋 취소 (변경사항 삭제) ⚠️

# 스테이징
git add -p                         # 변경사항을 부분적으로 스테이징
git restore --staged <FILE>        # 스테이징 취소

# 로그 확인
git log --oneline --graph -10      # 브랜치 그래프 포함 최근 10개
git log --author="<NAME>"          # 특정 작성자 커밋만
git show <COMMIT_HASH>             # 커밋 상세 내용

# 동기화
git fetch --prune                  # 삭제된 원격 브랜치 정리
git pull --rebase                  # Rebase 방식으로 pull

# 임시 저장
git stash                          # 작업 중인 변경사항 임시 저장
git stash pop                      # 임시 저장 복원
git stash list                     # 임시 저장 목록
```

---

### 10.4 GitLab / Bitbucket 차이점 (간략)

| 기능 | GitHub | GitLab | Bitbucket |
|------|--------|--------|-----------|
| **CI/CD** | GitHub Actions (`.github/workflows/`) | GitLab CI (`.gitlab-ci.yml`) | Bitbucket Pipelines (`bitbucket-pipelines.yml`) |
| **코드 소유자** | `CODEOWNERS` | `CODEOWNERS` | `CODEOWNERS` (동일) |
| **브랜치 보호** | Settings → Branches | Settings → Repository → Protected branches | Settings → Branch permissions |
| **시크릿 관리** | Secrets and variables | CI/CD → Variables | Repository settings → Repository variables |
| **리뷰 승인** | PR Reviewers | Merge Request Approvers | Pull Request Reviewers |

**마이그레이션 시 주의사항**:
- CI/CD 워크플로우 문법이 다르므로 재작성 필요
- Webhook URL 변경
- SSH 키 재등록

---

### 10.5 참고 자료

- [Conventional Commits](https://www.conventionalcommits.org/ko/v1.0.0/)
- [GitHub Flow](https://docs.github.com/en/get-started/quickstart/github-flow)
- [Semantic Versioning](https://semver.org/lang/ko/)
- [GitHub Actions 문서](https://docs.github.com/en/actions)
- [Spring Boot Deployment Best Practices](https://spring.io/guides)

---

## ✅ 마치며

이 가이드는 **dn-project 팀이 안전하고 효율적으로 협업**하기 위한 표준 프로세스를 담고 있습니다.

**핵심 원칙 요약**:
1. **항상 PR을 통해 main에 머지** (직접 푸시 금지)
2. **Conventional Commits 형식 준수**
3. **CI 통과 후에만 머지**
4. **시크릿은 절대 코드에 포함하지 않음**
5. **충돌 발생 시 팀원과 소통**

**문제가 생기면**:
- 섹션 8 "운영 가이드" 및 섹션 10.2 "트러블슈팅" 참고
- Git 마스터에게 문의
- [GIT_CONFLICT_POSTMORTEM.md](file:///d:/dn-project/docs/GIT_CONFLICT_POSTMORTEM.md) 숙지

**Happy Coding! 🚀**
