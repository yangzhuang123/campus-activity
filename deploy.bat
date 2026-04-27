@echo off
REM ============================================
REM 学生社团活动管理系统 - Windows部署脚本
REM ============================================

setlocal enabledelayedexpansion

REM 项目配置
set PROJECT_NAME=springbootnp4n3
set JAR_FILE=target\%PROJECT_NAME%-0.0.1-SNAPSHOT.jar

REM Java配置
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_181
set PATH=%JAVA_HOME%\bin;%PATH%

echo ============================================
echo 学生社团活动管理系统 - 部署脚本
echo ============================================
echo.

REM 检查参数
if "%1"=="" goto show_help
if "%1"=="build" goto build
if "%1"=="start" goto start
if "%1"=="stop" goto stop
if "%1"=="restart" goto restart
if "%1"=="status" goto status
if "%1"=="help" goto show_help

echo [ERROR] 未知命令: %1
goto show_help

:build
echo [INFO] 检查Java环境...
java -version
if errorlevel 1 (
    echo [ERROR] Java未安装或JAVA_HOME配置错误
    pause
    exit /b 1
)

echo [INFO] 检查Maven环境...
mvn -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Maven未安装
    pause
    exit /b 1
)

echo [INFO] 开始编译项目...
call mvn clean package -DskipTests

if errorlevel 1 (
    echo [ERROR] 编译失败!
    pause
    exit /b 1
)

echo [INFO] 编译成功!
dir %JAR_FILE%
goto end

:start
echo [INFO] 检查JAR文件...
if not exist %JAR_FILE% (
    echo [ERROR] JAR文件不存在: %JAR_FILE%
    echo [INFO] 请先运行: %0 build
    pause
    exit /b 1
)

echo [INFO] 启动服务...
start "Student Club Activity Management System" java -Xms512m -Xmx1024m -jar %JAR_FILE%

echo [INFO] 服务启动中...
timeout /t 3 /nobreak >nul

echo [INFO] 服务已启动!
echo [INFO] 访问地址:
echo   - 后端API: http://localhost:8080/springbootnp4n3
echo   - 管理面板: http://localhost:8080/springbootnp4n3/admin/admin/index.html
echo   - 学生门户: http://localhost:8080/springbootnp4n3/front/front/index.html
echo.
goto end

:stop
echo [INFO] 停止服务...
taskkill /FI "WINDOWTITLE eq Student Club Activity Management System" /T /F
if errorlevel 1 (
    echo [WARN] 未找到运行中的服务
) else (
    echo [INFO] 服务已停止
)
goto end

:restart
echo [INFO] 重启服务...
call :stop
timeout /t 2 /nobreak >nul
call :start
goto end

:status
echo [INFO] 检查服务状态...
tasklist /FI "WINDOWTITLE eq Student Club Activity Management System" | find /I "java.exe" >nul
if errorlevel 1 (
    echo [INFO] 服务未运行
) else (
    echo [INFO] 服务运行中
    tasklist /FI "WINDOWTITLE eq Student Club Activity Management System" /V
)
goto end

:show_help
echo 用法: %0 {build^|start^|stop^|restart^|status^|help}
echo.
echo 命令:
echo   build    编译项目
echo   start    启动服务
echo   stop     停止服务
echo   restart  重启服务
echo   status   查看服务状态
echo   help     显示此帮助信息
echo.
echo 示例:
echo   %0 build          编译项目
echo   %0 start          启动服务
echo   %0 restart        重启服务
echo.
goto end

:end
echo.
endlocal
pause
