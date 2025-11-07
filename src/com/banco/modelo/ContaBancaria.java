package com.banco.modelo;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Classe abstrata base para todas as contas bancárias.
 * Implementa comportamentos comuns e define o template para subclasses.
 * Aplicação dos princípios SRP e OCP.
 */
public abstract class ContaBancaria implements Conta {
    private final String numeroConta;
    private final Cliente cliente;
    protected BigDecimal saldo;
    
    protected ContaBancaria(String numeroConta, Cliente cliente, BigDecimal saldoInicial) {
        validarNumeroConta(numeroConta);
        validarCliente(cliente);
        validarSaldoInicial(saldoInicial);
        
        this.numeroConta = numeroConta;
        this.cliente = cliente;
        this.saldo = saldoInicial;
    }
    
    private void validarNumeroConta(String numeroConta) {
        if (numeroConta == null || numeroConta.trim().isEmpty()) {
            throw new IllegalArgumentException("Número da conta não pode ser vazio");
        }
    }
    
    private void validarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
    }
    
    private void validarSaldoInicial(BigDecimal saldoInicial) {
        if (saldoInicial == null) {
            throw new IllegalArgumentException("Saldo inicial não pode ser nulo");
        }
        if (saldoInicial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Saldo inicial não pode ser negativo");
        }
    }
    
    @Override
    public void depositar(BigDecimal valor) {
        validarValorPositivo(valor);
        saldo = saldo.add(valor);
    }
    
    @Override
    public boolean sacar(BigDecimal valor) {
        validarValorPositivo(valor);
        
        if (!possuiSaldoSuficiente(valor)) {
            return false;
        }
        
        saldo = saldo.subtract(valor);
        return true;
    }
    
    protected boolean possuiSaldoSuficiente(BigDecimal valor) {
        return saldo.compareTo(valor) >= 0;
    }
    
    protected void validarValorPositivo(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
    }
    
    @Override
    public BigDecimal consultarSaldo() {
        return saldo;
    }
    
    @Override
    public String getNumeroConta() {
        return numeroConta;
    }
    
    @Override
    public Cliente getCliente() {
        return cliente;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        ContaBancaria conta = (ContaBancaria) obj;
        return numeroConta.equals(conta.numeroConta);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(numeroConta);
    }
    
    @Override
    public String toString() {
        return String.format("%s - Número: %s - Cliente: %s - Saldo: R$ %.2f",
                getTipo().getDescricao(),
                numeroConta,
                cliente.getNome(),
                saldo);
    }
}