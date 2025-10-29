# Sistema_Bancario
Trabalho Boas Práticas de Programação


# Sistema Bancário Simples

Simulação de um sistema bancário básico para controle de clientes, contas e operações financeiras.

- Linguagem: Java (recomendado Java 17+)
- Precisão monetária: BigDecimal (escala 2, RoundingMode.HALF_UP)
- Interface inicial: CLI (console). Futuramente, API REST (mantendo camada de serviços)
- Persistência inicial: In-memory com interfaces de repositório para substituição futura por banco de dados
- Arquitetura: camadas Domain, Service, Repository e Presentation

---

## Sumário
- [Arquitetura e Stack](#arquitetura-e-stack)
- [Requisitos Funcionais (RF)](#requisitos-funcionais-rf)
- [Requisitos Não Funcionais (RNF)](#requisitos-não-funcionais-rnf)
- [Regras de Negócio](#regras-de-negócio)
- [Modelo de Domínio](#modelo-de-domínio)
- [Backlog do Produto](#backlog-do-produto)
- [Definição de Pronto (DoD)](#definição-de-pronto-dod)
- [Plano de Sprints](#plano-de-sprints)
- [Distribuição entre Desenvolvedores](#distribuição-entre-desenvolvedores)
- [Critérios de Aceitação por Operação](#critérios-de-aceitação-por-operação)
- [Casos de Teste Essenciais](#casos-de-teste-essenciais)
- [Riscos e Mitigação](#riscos-e-mitigação)
- [Como Executar (CLI)](#como-executar-cli)
- [Próximos Passos Práticos](#próximos-passos-práticos)

---

## Arquitetura e Stack
- Domain: Entidades, enums e regras centrais
- Service: Casos de uso (depósito, saque, transferência, rendimento, relatórios)
- Repository: Interfaces e implementação in-memory (substituível por DB)
- Presentation: CLI (console) na 1ª entrega; API REST opcional posteriormente
- Logging: INFO para operações e ERROR para falhas

Sugestão de estrutura de pastas:
```
/domain
/service
/repository
/cli
```

---

## Requisitos Funcionais (RF)
- RF-01 Cadastro de cliente
  - Criar cliente com nome e CPF
  - CPF único (não permitir duplicidade)
- RF-02 Cadastro de contas
  - Criar conta com número único, cliente associado e saldo inicial
  - Tipos: Corrente e Poupança
- RF-03 Depósito
  - Adicionar valor ao saldo (valor > 0)
- RF-04 Saque
  - Subtrair valor do saldo se houver saldo suficiente (valor > 0)
- RF-05 Transferência entre contas
  - Transferir valor de conta origem para destino
  - Operação atômica; origem ≠ destino
- RF-06 Consulta de saldo
  - Exibir saldo atual de uma conta por número
- RF-07 Aplicar rendimento de poupanças
  - Aplicar taxa percentual informada a todas as contas Poupança
- RF-08 Listagem de contas
  - Exibir tipo, nome do cliente, número e saldo
  - Ordenação: saldo desc; desempate por número asc
- RF-09 Relatório de consolidação
  - Total de contas e soma de saldos por tipo
  - Total geral de contas e saldo total do banco

---

## Requisitos Não Funcionais (RNF)
- RNF-01 Precisão financeira com BigDecimal (escala 2, HALF_UP)
- RNF-02 Validações: CPF válido e único; valores monetários > 0
- RNF-03 Confiabilidade: transferências atômicas (transacional na camada de serviço)
- RNF-04 Observabilidade: logs de operações (INFO) e erros (ERROR)
- RNF-05 Testes: cobertura mínima 80% em serviços/regras; casos positivos e negativos
- RNF-06 Manutenibilidade: camadas, interfaces para repositório, Javadoc
- RNF-07 Concorrência: sincronização em operações de escrita

---

## Regras de Negócio
- Cliente
  - CPF obrigatório e único
  - Nome não vazio
- Conta
  - Número obrigatório e único
  - Saldo inicial ≥ 0
  - Tipo ∈ {CORRENTE, POUPANCA}
- Operações
  - Depósito/saque/transferência com valor > 0
  - Saque e débito de transferência exigem saldo suficiente
  - Transferência: origem ≠ destino
  - Rendimento: taxa percentual aplicada a cada poupança; arredondamento HALF_UP no resultado por conta

---

## Modelo de Domínio
Entidades:
- Cliente {id, nome, cpf}
- Conta {numero, cliente, saldo: BigDecimal, tipo: enum TipoConta}
- Transacao {id, tipo: enum (DEPOSITO, SAQUE, TRANSFERENCIA, RENDIMENTO), valor, dataHora, contaOrigem?, contaDestino?}

Repositórios (interfaces):
- ClienteRepository: salvar, buscarPorCpf, listar
- ContaRepository: salvar, buscarPorNumero, listar, existeNumero
- TransacaoRepository: registrar, listarPorConta

Serviços:
- ClienteService: cadastrarCliente, obterPorCpf
- ContaService: abrirConta, listarContas, consultarSaldo
- OperacaoService: depositar, sacar, transferir, aplicarRendimentoPoupancas
- RelatorioService: consolidacaoPorTipo, consolidacaoGeral

---

## Backlog do Produto
Épico E1: Cadastro e Domínio
- US-01 Cadastrar cliente (3 pts)
  - Critérios: CPF válido e único; nome obrigatório; erro se CPF duplicado
- US-02 Abrir conta (5 pts)
  - Critérios: número único; cliente existe; tipo obrigatório; saldo inicial ≥ 0

Épico E2: Operações financeiras
- US-03 Depositar em conta (3 pts)
  - Critérios: valor > 0; conta existe; saldo atualizado
- US-04 Sacar de conta (5 pts)
  - Critérios: valor > 0; saldo suficiente; rejeitar caso contrário
- US-05 Transferir entre contas (8 pts)
  - Critérios: origem ≠ destino; valor > 0; ambas existem; operação atômica
- US-06 Aplicar rendimento em poupanças (5 pts)
  - Critérios: aplica taxa a todas as poupanças; HALF_UP; log do total aplicado e quantidade

Épico E3: Consultas e Relatórios
- US-07 Consultar saldo (2 pts)
  - Critérios: conta existe; erro se inexistente
- US-08 Listar contas ordenadas (3 pts)
  - Critérios: listar tipo, cliente, número, saldo; ordenação saldo desc e número asc no empate
- US-09 Relatório de consolidação (5 pts)
  - Critérios: total e soma por tipo; total geral de contas e saldos

Épico E4: Qualidade, Infra e Observabilidade
- US-10 Tratamento de erros padronizado (3 pts)
  - Critérios: exceções específicas; mensagens claras; logs apropriados
- US-11 Testes e cobertura (5 pts)
  - Critérios: ≥ 80% serviços/regras; casos de fronteira (zero/negativo, duplicidades, saldo insuficiente)
- US-12 Documentação e CLI (5 pts)
  - Critérios: README com build/run; CLI com comandos principais

---

## Definição de Pronto (DoD)
- Código revisado por 1 par
- Testes unitários passando e cobertura ≥ 80% em serviços
- Logs mínimos implementados
- Tratamento de erros previsíveis com mensagens claras
- Build limpo e execução demonstrável (CLI) ou endpoints REST testáveis
- Documentação de uso e exemplos

---

## Plano de Sprints
Sprint 1 (Fundação e operações básicas) — 16 pts
- US-01 (3), US-02 (5), US-03 (3), US-07 (2), US-10 (3, parcial)

Sprint 2 (Débito, transferências e listagens) — 21 pts
- US-04 (5), US-05 (8), US-08 (3), US-11 (5, parcial)

Sprint 3 (Rendimento, relatórios e hardening) — 22 pts
- US-06 (5), US-09 (5), US-11 (5, concluir), US-12 (5), US-10 (2, ajustes)

---

## Distribuição entre Desenvolvedores
- Dev A (Domínio e Repositórios): Entidades, enums, interfaces, in-memory; US-01, parte de US-02; suporte a ordenação/consultas
- Dev B (Serviços e Regras): Depósito, saque, transferência, rendimento; US-03, US-04, US-05, US-06
- Dev C (Apresentação, Relatórios e Qualidade): CLI/REST, listagem, relatórios, erros, documentação e testes; US-07, US-08, US-09, US-10, US-11, US-12

---

## Critérios de Aceitação por Operação
Depósito
- Valor > 0; conta existe; saldo aumenta exatamente pelo valor
- Log INFO com id da transação, conta e valor

Saque
- Valor > 0; conta existe
- Se saldo ≥ valor, debita; senão erro “Saldo insuficiente” e saldo inalterado

Transferência
- Valor > 0; origem ≠ destino; ambas as contas existem
- Se saldo origem ≥ valor, debita origem e credita destino atomicamente; rollback em falha
- Log INFO com id da transação e contas envolvidas

Rendimento (Poupança)
- Para cada poupança: novoSaldo = saldo × (1 + taxa), arredondando HALF_UP a 2 casas
- Relatar quantidade de contas afetadas e valor total aplicado

Listagem
- Ordenação por saldo desc; empate por número asc
- Campos: tipo, nome do cliente, número, saldo

Relatório de Consolidação
- Por tipo: total de contas e soma de saldos
- Geral: total de contas e soma total

---

## Casos de Teste Essenciais
Cliente
- Cadastro com CPF válido novo → sucesso
- Cadastro com CPF duplicado → erro

Conta
- Abrir conta para cliente inexistente → erro
- Abrir conta com número já usado → erro
- Saldo inicial negativo → erro

Depósito
- Valor positivo → saldo correto
- Zero/negativo → erro

Saque
- Saldo suficiente → saldo correto
- Acima do saldo → erro, saldo inalterado

Transferência
- Contas válidas e saldo suficiente → saldos atualizados
- Origem = destino → erro
- Conta inexistente → erro
- Falha intermediária → rollback, saldos inalterados

Rendimento
- Aplicar 0,5% em múltiplas poupanças → verificar arredondamento
- Corrente não afetada

Listagem e Relatório
- Ordenação correta
- Agregação por tipo e total geral corretas

---

## Riscos e Mitigação
- Precisão monetária: usar BigDecimal; evitar double
- Concorrência: sincronizar operações de escrita; se REST, considerar locks por conta
- Evolução de persistência: programar contra interfaces; separar implementação in-memory
- Escopo: começar com CLI; serviços desacoplados para futura API REST

---

## Como Executar (CLI)
Pré-requisitos:
- Java 17+
- Maven ou Gradle (exemplos com Maven)

Build e testes:
```bash
mvn clean verify
```

Execução (exemplo):
```bash
mvn -q exec:java -Dexec.mainClass="com.seubanco.cli.Main"
```

Comandos esperados (exemplo de interface CLI):
```bash
# Clientes
banco cadastrar-cliente --nome "Ana Silva" --cpf 12345678901

# Contas
banco abrir-conta --cpf 12345678901 --tipo POUPANCA --numero 1001 --saldo-inicial 100.00

# Operações
banco depositar --conta 1001 --valor 50.00
banco sacar --conta 1001 --valor 20.00
banco transferir --origem 1001 --destino 1002 --valor 10.00
banco rendimento --taxa 0.5   # aplica 0,5% a todas as poupanças

# Consultas e relatórios
banco saldo --conta 1001
banco listar-contas
banco relatorio
```

---

## Próximos Passos Práticos
1) Iniciar repositório com módulos: domain, service, repository, cli
2) Definir entidades, enums, exceções e interfaces de repositório
3) Implementar repositórios in-memory e serviços de cliente/conta
4) Implementar operações e testes por TDD
5) Entregar CLI mínima com comandos: cadastrar-cliente, abrir-conta, depositar, sacar, transferir, saldo, listar, rendimento, relatorio

Se desejar, posso abrir issues no seu repositório com cada história do backlog (títulos, descrições e critérios de aceitação).
