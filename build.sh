#!/bin/bash
# ============================================
# Script de build e execução do projeto Banco
# ============================================

SRC_DIR="src"
OUT_DIR="bin"
MAIN_CLASS="Main"

GREEN="\033[0;32m"
RED="\033[0;31m"
NC="\033[0m"

echo -e "${GREEN}Criando diretório de saída...${NC}"
mkdir -p "$OUT_DIR"

echo -e "${GREEN}Compilando código-fonte...${NC}"
javac -d "$OUT_DIR" $(find "$SRC_DIR" -name "*.java")

if [ $? -ne 0 ]; then
    echo -e "${RED}Erro na compilação!${NC}"
    exit 1
fi

echo -e "${GREEN}Executando aplicação...${NC}"
java -cp "$OUT_DIR" "$MAIN_CLASS"
