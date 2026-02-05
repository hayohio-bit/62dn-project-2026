@echo off
REM 콘솔을 UTF-8 코드 페이지로 전환 (출력용)
chcp 65001 >NUL

REM 1) 기본 안내
echo ============================================
echo   62댕냥이 Windows 개발 환경 세팅
echo   - GitHub CLI 설치 + 로그인
echo ============================================
echo.

REM 2) gh 설치 여부 확인
echo [1] gh 설치 여부 확인
where gh >NUL 2>&1
set GH_EXIST=%ERRORLEVEL%
echo where gh ERRORLEVEL=%GH_EXIST%
echo.

if "%GH_EXIST%"=="0" goto GH_OK

echo ❌ GitHub CLI(gh)가 설치되어 있지 않습니다.
echo.
echo 1^)^ 브라우저에서 https://cli.github.com 페이지를 열어 설치 프로그램을 받으세요.
echo 2^)^ gh 설치 후 이 파일(setup-gh-env.bat)을 다시 실행해주세요.
pause
goto END

:GH_OK
echo ✅ gh CLI 설치됨.
echo.

REM 3) GitHub 로그인 상태 확인
echo [2] GitHub 로그인 상태 확인...
gh auth status >NUL 2>&1
set GH_AUTH=%ERRORLEVEL%
echo gh auth status ERRORLEVEL=%GH_AUTH%
echo.

if "%GH_AUTH%"=="0" goto AUTH_OK

echo ❌ 아직 GitHub CLI 로그인이 되어 있지 않습니다.
echo 브라우저가 열리면 GitHub 로그인과 권한 허용을 완료해주세요.
echo.
gh auth login
goto AFTER_AUTH

:AUTH_OK
echo ✅ 이미 GitHub CLI 로그인 완료 상태입니다.

:AFTER_AUTH
echo.
echo [3] git 사용자 정보 설정 (최초 1회만)
echo git user.name / user.email 은 커밋 작성자 정보에 사용됩니다.
echo (그냥 Enter 를 누르면 현재 설정을 유지합니다.)
echo.

REM user.name 설정
git config user.name >NUL 2>&1
IF ERRORLEVEL 1 (
    echo 예시: 홍길동
)
set /p GIT_NAME=Enter git user.name (leave empty to keep current): 
if not "%GIT_NAME%"=="" git config --global user.name "%GIT_NAME%"

REM user.email 설정
git config user.email >NUL 2>&1
IF ERRORLEVEL 1 (
    echo 예시: you@example.com
)
set /p GIT_EMAIL=Enter git user.email (leave empty to keep current): 
if not "%GIT_EMAIL%"=="" git config --global user.email "%GIT_EMAIL%"

echo.
echo 🎉 개발 환경 세팅이 완료되었습니다!
echo 이제 deploy.bat 또는 deploy.sh 를 실행해서 add / commit / push / PR 을 자동으로 진행할 수 있습니다.

:END
pause
exit /b 0
