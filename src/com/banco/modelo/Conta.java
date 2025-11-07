package com.banco.modelo;

import java.math.BigDecimal;

/**
 * Interface que define o contrato para todas as contas bancárias.
 * Aplicação do ISP (Interface Segregation Principle).
 */
public interface Conta {
    
    /**
     * Realiza um depósito na conta.
     * @param valor valor a ser depositado (deve ser positivo)
     * @throws IllegalArgumentException se o valor for inválido
     */
    void depositar(BigDecimal valor);
    
    /**
     * Realiza um saque da conta.
     * @param valor valor a ser sacado
     * @return true se o saque foi realizado com sucesso
     * @throws IllegalArgumentException se o valor for inválido
     */
    boolean sacar(BigDecimal valor);
    
    /**
     * Retorna o saldo atual da conta.
     * @return saldo atual
     */
    BigDecimal consultarSaldo();
    
    /**
     * Retorna o número da conta.
     * @return número da conta
     */
    String getNumeroConta();
    
    /**
     * Retorna o cliente titular da conta.
     * @return cliente
     */
    Cliente getCliente();
    
    /**
     * Retorna o tipo da conta.
     * @return tipo da conta
     */
    TipoConta getTipo();
}