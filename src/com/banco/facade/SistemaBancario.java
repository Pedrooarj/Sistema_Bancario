package com.banco.facade;

import com.banco.modelo.Cliente;
import com.banco.modelo.ContaBancaria;
import com.banco.repositorio.*;
import com.banco.servico.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class SistemaBancario {
    private final ServicoCliente servicoCliente;
    private final ServicoConta servicoConta;
    private final ServicoRelatorio servicoRelatorio;
    
    public SistemaBancario() {
        ClienteRepositorio clienteRepo = new ClienteRepositorio();
        ContaRepositorio contaRepo = new ContaRepositorio();
        
        this.servicoCliente = new ServicoCliente(clienteRepo);
        this.servicoConta = new ServicoConta(contaRepo);
        this.servicoRelatorio = new ServicoRelatorio(contaRepo);
    }
    
    // === Operações de Cliente ===
    public Cliente cadastrarCliente(String nome, String cpf) {
        return servicoCliente.cadastrarCliente(nome, cpf);
    }
    
    public boolean clienteExiste(String cpf) {
        return servicoCliente.buscarPorCpf(cpf).isPresent();
    }
    
    public List<Cliente> listarClientes() {
        return servicoCliente.listarTodos();
    }
    
    // === Operações de Conta ===
    public ContaBancaria criarContaCorrente(String numero, Cliente cliente, BigDecimal saldoInicial) {
        return servicoConta.criarContaCorrente(numero, cliente, saldoInicial);
    }
    
    public ContaBancaria criarContaPoupanca(String numero, Cliente cliente, BigDecimal saldoInicial) {
        return servicoConta.criarContaPoupanca(numero, cliente, saldoInicial);
    }
    
    // === Operações Bancárias ===
    public void depositar(String numeroConta, BigDecimal valor) {
        servicoConta.depositar(numeroConta, valor);
    }
    
    public void sacar(String numeroConta, BigDecimal valor) {
        servicoConta.sacar(numeroConta, valor);
    }
    
    public void transferir(String origem, String destino, BigDecimal valor) {
        servicoConta.transferir(origem, destino, valor);
    }
    
    public BigDecimal consultarSaldo(String numeroConta) {
        return servicoConta.consultarSaldo(numeroConta);
    }
    
    public void aplicarRendimentoPoupanca(BigDecimal percentual) {
        servicoConta.aplicarRendimentoEmTodas(percentual);
    }
    
    // === Relatórios ===
    public void imprimirRelatorioCompleto() {
        servicoRelatorio.imprimirRelatorioCompleto();
    }
    
    public List<ContaBancaria> listarContasPorSaldo() {
        return servicoRelatorio.listarContasPorSaldoDecrescente();
    }
    
    public Map<String, Object> obterResumoGeral() {
        return servicoRelatorio.gerarResumoGeral();
    }
}