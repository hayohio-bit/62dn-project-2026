# Git 기반 프로젝트 표준 가이드
## 62댕냥이 플랫폼 (dn-project)

**작성일**: 2026-02-04  
**대상 프로젝트**: dn-project (유기동물 입양/임보 매칭 플랫폼)  
**버전**: 1.0

---

이 문서는 팀이 저장소 생성부터 운영 배포까지 막힘없이 진행할 수 있도록 하는 **완전한 Git 워크플로우 가이드**입니다.

**📖 전체 가이드**: [GIT_WORKFLOW_GUIDE.md](file:///C:/Users/M/.gemini/antigravity/brain/7ac74727-e9d0-4fb0-b0d3-91ccd9dfc091/GIT_WORKFLOW_GUIDE.md)

## 빠른 시작 (Quick Start)

### 신규 팀원
1. 레포 클론: `git clone https://github.com/hayohio-bit/62dn-project-2026.git`
2. `.env` 설정 (backend, frontend 각각)
3. DB 세팅: `docs/sql.txt` 실행
4. 실행: `./gradlew bootRun` (backend), `npm run dev` (frontend)
5. 첫 PR 만들기 → 상세: 섹션 9 참고

### 작업 시작
```bash
git checkout main
git pull origin main
git checkout -b feature/<기능명>
# 작업...
git commit -m "feat(scope): 설명"
git push origin feature/<기능명>
# GitHub에서 PR 생성
```

### 커밋 규칙
- `feat(auth): add JWT login` - 새 기능
- `fix(animal): resolve null pointer` - 버그 수정
- `docs(readme): update guide` - 문서
- `refactor(api): extract helper` - 리팩토링

### 긴급 상황
- **배포 실패**: 섹션 8.2 "롤백 경로" 참고
- **Merge 충돌**: [GIT_CONFLICT_POSTMORTEM.md](file:///d:/dn-project/docs/GIT_CONFLICT_POSTMORTEM.md)
- **CI 실패**: 섹션 10.2 "트러블슈팅" 참고

## 주요 섹션

| 섹션 | 내용 | 대상 |
|------|------|------|
| **2. 저장소 세팅** | 디렉토리 구조, .gitignore, README | 전체 |
| **3. 브랜치 전략** | GitHub Flow, 작업 흐름 | 전체 |
| **4. 품질 게이트** | 브랜치 보호, CODEOWNERS, PR 체크리스트 | Git 마스터 |
| **5. CI 설계** | 자동 테스트/빌드 | Backend/Frontend |
| **6. CD 설계** | EC2 배포, 시크릿 관리 | Git 마스터 |
| **7. 릴리즈** | 버저닝, 태깅, CHANGELOG | Git 마스터 |
| **8. 운영** | 모니터링, 장애 대응, 롤백 | 전체 |
| **9. 온보딩** | 30분 코스 | 신규 팀원 |
| **10. 부록** | 템플릿, 트러블슈팅 TOP 10 | 전체 |

## 핵심 원칙

✅ **항상 PR을 통해 main에 머지** (직접 푸시 금지)  
✅ **Conventional Commits 형식 준수**  
✅ **CI 통과 후에만 머지**  
✅ **시크릿은 절대 코드에 포함하지 않음**  
✅ **충돌 발생 시 팀원과 소통**

## 문서 위치

- **이 문서**: 빠른 참조용
- **전체 가이드**: Brain 아티팩트 폴더
- **충돌 가이드**: [docs/GIT_CONFLICT_POSTMORTEM.md](file:///d:/dn-project/docs/GIT_CONFLICT_POSTMORTEM.md)
- **템플릿**: `.github/` 폴더

---

**Happy Coding! 🚀**
