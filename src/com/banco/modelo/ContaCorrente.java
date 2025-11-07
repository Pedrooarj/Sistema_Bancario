package com.banco.modelo;

import java.math.BigDecimal;

/**
 * Representa uma conta corrente no banco.
 * Conta simples sem rendimentos.
 */
public class ContaCorrente extends ContaBancaria {
    
    public ContaCorrente(String numeroConta, Cliente cliente, BigDecimal saldoInicial) {
        super(numeroConta, cliente, saldoInicial);
    }
    
    @Override
    public TipoConta getTipo() {
        return TipoConta.CORRENTE;
    }
}