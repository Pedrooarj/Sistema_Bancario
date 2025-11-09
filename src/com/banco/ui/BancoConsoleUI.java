package com.banco.ui;

import java.math.BigDecimal;
import java.util.Scanner;

import com.banco.facade.SistemaBancario;
import com.banco.modelo.Cliente;

public class BancoConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final SistemaBancario sistema;

    public BancoConsoleUI (SistemaBancario sistema) {
        this.sistema = sistema;
    }

    
    public void iniciar() {
        exibirBoasVindas();
        executarMenuPrincipal();
        scanner.close();
    }
    
    private void exibirBoasVindas() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA BANCÁRIO - Boas Práticas v1.0    ║");
        System.out.println("║   UFRN - IMD - DIMAp                       ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
    }
    
    private void executarMenuPrincipal() {
        boolean continuar = true;
        
        while (continuar) {
            exibirMenu();
            int opcao = lerOpcao();
            continuar = processarOpcao(opcao);
        }
        
        System.out.println("\n✓ Obrigado por usar o Sistema Bancário!");
    }
    
    private void exibirMenu() {
        System.out.println("\n╔════════════════ MENU PRINCIPAL ═══════════════╗");
        System.out.println("║  1. Cadastrar Cliente                         ║");
        System.out.println("║  2. Criar Conta Corrente                      ║");
        System.out.println("║  3. Criar Conta Poupança                      ║");
        System.out.println("║  4. Depositar                                 ║");
        System.out.println("║  5. Sacar                                     ║");
        System.out.println("║  6. Transferir                                ║");
        System.out.println("║  7. Consultar Saldo                           ║");
        System.out.println("║  8. Aplicar Rendimento (Poupança)             ║");
        System.out.println("║  9. Relatório Completo                        ║");
        System.out.println("║  0. Sair                                      ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.print("Escolha uma opção: ");
    }
    
    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private boolean processarOpcao(int opcao) {
        try {
            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    criarContaCorrente();
                    break;
                case 3:
                    criarContaPoupanca();
                    break;
                case 4:
                    realizarDeposito();
                    break;
                case 5:
                    realizarSaque();
                    break;
                case 6:
                    realizarTransferencia();
                    break;
                case 7:
                    consultarSaldo();
                    break;
                case 8:
                    aplicarRendimento();
                    break;
                case 9:
                    exibirRelatorio();
                    break;
                case 0:
                    return false;
                default:
                    System.out.println("❌ Opção inválida!");
            }
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
        return true;
    }
    
    private void cadastrarCliente() {
        System.out.println("\n=== CADASTRAR CLIENTE ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF (11 dígitos): ");
        String cpf = scanner.nextLine();
        
        Cliente cliente = sistema.cadastrarCliente(nome, cpf);
        System.out.println("✓ Cliente cadastrado: " + cliente);
    }
    
    private void criarContaCorrente() {
        System.out.println("\n=== CRIAR CONTA CORRENTE ===");
        Cliente cliente = buscarClientePorCpf();
        if (cliente == null) {
            System.out.println("❌ Cliente não encontrado!");
            return;
        }
        
        System.out.print("Número da conta: ");
        String numero = scanner.nextLine();
        System.out.print("Saldo inicial: R$ ");
        BigDecimal saldo = lerValor();
        
        sistema.criarContaCorrente(numero, cliente, saldo);
        System.out.println("✓ Conta corrente criada com sucesso!");
    }
    
    private void criarContaPoupanca() {
        System.out.println("\n=== CRIAR CONTA POUPANÇA ===");
        Cliente cliente = buscarClientePorCpf();
        if (cliente == null) {
            System.out.println("❌ Cliente não encontrado!");
            return;
        }
        
        System.out.print("Número da conta: ");
        String numero = scanner.nextLine();
        System.out.print("Saldo inicial: R$ ");
        BigDecimal saldo = lerValor();
        
        sistema.criarContaPoupanca(numero, cliente, saldo);
        System.out.println("✓ Conta poupança criada com sucesso!");
    }
    
    private void realizarDeposito() {
        System.out.println("\n=== DEPOSITAR ===");
        System.out.print("Número da conta: ");
        String numero = scanner.nextLine();
        System.out.print("Valor: R$ ");
        BigDecimal valor = lerValor();
        
        sistema.depositar(numero, valor);
        System.out.printf("✓ Depósito realizado! Novo saldo: R$ %.2f%n", 
                sistema.consultarSaldo(numero));
    }
    
    private void realizarSaque() {
        System.out.println("\n=== SACAR ===");
        System.out.print("Número da conta: ");
        String numero = scanner.nextLine();
        System.out.print("Valor: R$ ");
        BigDecimal valor = lerValor();
        
        sistema.sacar(numero, valor);
        System.out.printf("✓ Saque realizado! Novo saldo: R$ %.2f%n", 
                sistema.consultarSaldo(numero));
    }
    
    private void realizarTransferencia() {
        System.out.println("\n=== TRANSFERIR ===");
        System.out.print("Conta origem: ");
        String origem = scanner.nextLine();
        System.out.print("Conta destino: ");
        String destino = scanner.nextLine();
        System.out.print("Valor: R$ ");
        BigDecimal valor = lerValor();
        
        sistema.transferir(origem, destino, valor);
        System.out.println("✓ Transferência realizada com sucesso!");
    }
    
    private void consultarSaldo() {
        System.out.println("\n=== CONSULTAR SALDO ===");
        System.out.print("Número da conta: ");
        String numero = scanner.nextLine();
        
        BigDecimal saldo = sistema.consultarSaldo(numero);
        System.out.printf("Saldo atual: R$ %.2f%n", saldo);
    }
    
    private void aplicarRendimento() {
        System.out.println("\n=== APLICAR RENDIMENTO ===");
        System.out.print("Percentual (ex: 0.5 para 0,5%%): ");
        BigDecimal percentual = lerValor();
        
        sistema.aplicarRendimentoPoupanca(percentual);
        System.out.println("✓ Rendimento aplicado às contas poupança!");
    }
    
    private void exibirRelatorio() {
        System.out.println();
        sistema.imprimirRelatorioCompleto();
    }
    
    private Cliente buscarClientePorCpf() {
        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();
        
        return sistema.listarClientes().stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }
    
    private BigDecimal lerValor() {
        try {
            return new BigDecimal(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Valor inválido! Usando 0.00");
            return BigDecimal.ZERO;
        }
    }
}
