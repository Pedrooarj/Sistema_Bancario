@echo off
REM ============================================
REM Script de build e execução do projeto Banco
REM ============================================

set SRC_DIR=src
set OUT_DIR=bin
set MAIN_CLASS=Main

echo Criando diretório de saída...
if not exist %OUT_DIR% mkdir %OUT_DIR%

echo Compilando código-fonte...
for /R %SRC_DIR% %%f in (*.java) do (
    javac -d %OUT_DIR% "%%f"
)

if %ERRORLEVEL% neq 0 (
    echo Erro na compilação!
    exit /b 1
)

echo Executando aplicação...
java -cp %OUT_DIR% %MAIN_CLASS%
