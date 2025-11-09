# Sistema Bancário Simples

## Integrantes da Equipe
- Antonio Walter Araujo Filho
- Pedro Otávio Medeiros de Araujo
- Samuel Ótton Nogueira Maia

## Sobre o Sistema

Sistema bancário desenvolvido em Java como projeto da disciplina de Boas Práticas de Programação. O sistema simula operações bancárias básicas, incluindo:

- Cadastro de clientes
- Criação de contas corrente e poupança
- Operações de depósito, saque e transferência
- Aplicação de rendimentos em contas poupança
- Geração de relatórios consolidados

## Objetivos do Projeto

Aplicar na prática as boas práticas de programação estudadas em sala de aula:
- **Princípios SOLID**
- **Clean Code**
- **Programação Defensiva**
- **Padrões de Projeto**

## Estrutura do Projeto

```
sistema-bancario/
│
├── src/
│   └── com/
│       └── banco/
│           ├── modelo/              # Classes de domínio
│           │   ├── Cliente.java
│           │   ├── Conta.java (interface)
│           │   ├── ContaBancaria.java (abstrata)
│           │   ├── ContaCorrente.java
│           │   ├── ContaPoupanca.java
│           │   ├── Rendivel.java (interface)
│           │   └── TipoConta.java (enum)
│           │
│           ├── repositorio/         # Persistência em memória
│           │   ├── RepositorioClientes.java
│           │   └── RepositorioContas.java
│           │
│           ├── servico/             # Lógica de negócio
│           │   ├── ServicoCliente.java
│           │   ├── ServicoConta.java
│           │   └── ServicoRelatorio.java
│           │
│           ├── ui/             # Exibição da interface gráfica
│           │   └── BancoConsoleUI.java
│           │
│           ├── facade/              # Facade Pattern
│           │   └── SistemaBancario.java
│           │
│   └── Main.java            # Aplicação principal
│
├── bin/                             # Arquivos compilados (.class)
├── .gitignore
└── README.md
```

## Como Compilar

### Pré-requisitos
- **Java JDK 8** ou superior
- Terminal/Prompt de Comando

### Compilação via Terminal

1. Navegue até o diretório raiz do projeto:
```bash
cd caminho/para/sistema-bancario
```

2. Compile todos os arquivos Java:
```bash
javac -d bin src/com/banco/modelo/*.java
javac -d bin -cp bin src/com/banco/repositorio/*.java
javac -d bin -cp bin src/com/banco/fabrica/*.java
javac -d bin -cp bin src/com/banco/servico/*.java
javac -d bin -cp bin src/com/banco/facade/*.java
javac -d bin -cp bin src/Main.java
```

Ou compile tudo de uma vez:
```bash
javac -d bin src/com/banco/**/*.java src/*.java
```

## Como Executar

### Execução via Terminal

Após compilar, execute:
```bash
java -cp bin Main
```
## Compilação e Execução por Script

- **Windows** 
```
build.bat
```
- **Linux/macOs** 
```
chmod +x build.sh
./build.sh
```

## Funcionalidades

### Implementadas

#### Gerenciamento de Clientes
- [x] Cadastrar novo cliente (nome e CPF)
- [x] Verificar se cliente já existe
- [x] Validação de dados (nome mínimo 3 caracteres, CPF 11 dígitos)

#### Gerenciamento de Contas
- [x] Criar conta corrente
- [x] Criar conta poupança
- [x] Verificar se número da conta já existe
- [x] Definir saldo inicial

#### Operações Bancárias
- [x] Realizar depósito
- [x] Realizar saque (com verificação de saldo)
- [x] Realizar transferência entre contas
- [x] Consultar saldo de uma conta

#### Operações Especiais
- [x] Aplicar rendimento percentual em todas as contas poupança

#### Relatórios
- [x] Listar todas as contas ordenadas por saldo (decrescente)
- [x] Relatório consolidado com totalizações por tipo
- [x] Total geral de contas e saldo do banco

## Boas Práticas Aplicadas

### Princípios SOLID

#### 1. Single Responsibility Principle (SRP)
Cada classe tem uma única responsabilidade bem definida:
- `Cliente`: representa dados do cliente
- `ContaBancaria`: representa operações de conta
- `RepositorioClientes`: gerencia persistência de clientes
- `ServicoCliente`: implementa lógica de negócio de clientes

#### 2. Open-Closed Principle (OCP)
Sistema aberto para extensão, fechado para modificação:
- Novos tipos de conta podem ser adicionados sem alterar código existente
- Interface `Conta` permite polimorfismo
- `FabricaContas` centraliza criação de novos tipos

#### 3. Liskov Substitution Principle (LSP)
Subclasses podem substituir suas classes base:
- `ContaCorrente` e `ContaPoupanca` substituem `ContaBancaria` perfeitamente
- Comportamentos mantidos conforme esperado

#### 4. Interface Segregation Principle (ISP)
Interfaces específicas em vez de uma interface geral:
- Interface `Conta`: operações básicas de conta
- Interface `Rendivel`: apenas para contas com rendimento
- `ContaCorrente` não é obrigada a implementar `aplicarRendimento()`

#### 5. Dependency Inversion Principle (DIP)
Dependência de abstrações, não de implementações concretas:
- Serviços dependem de interfaces (`Conta`, `Rendivel`)
- Facilita testes e manutenção
- Baixo acoplamento entre camadas

### Clean Code

### Padrões de Projeto

#### Facade
`SistemaBancario` fornece interface simplificada para todo o sistema.

#### Repository
`RepositorioClientes` e `RepositorioContas` abstraem a persistência.

## Tecnologias e Recursos

- **Java 8+**
- **Collections Framework** (HashMap, ArrayList)
- **BigDecimal** (precisão em valores monetários)
- **Optional** (evitar NullPointerException)
- **Streams API** (operações funcionais)
- **Enums** (tipos seguros)

## Status do Projeto

- [x] Estrutura básica criada
- [x] Camada de modelo completa
- [x] Camada de repositório
- [x] Camada de serviço
- [x] Interface de usuário
- [ ] Testes unitários (planejado)
- [ ] Vídeo de apresentação (planejado)

## Licença

Projeto desenvolvido para fins acadêmicos na disciplina de **Boas Práticas de Programação** - UFRN//IMD/DIMAp.