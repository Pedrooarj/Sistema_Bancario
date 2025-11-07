package com.banco.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Representa uma conta poupança no banco.
 * Possui capacidade de aplicar rendimentos.
 */
public class ContaPoupanca extends ContaBancaria implements Rendivel {
    
    public ContaPoupanca(String numeroConta, Cliente cliente, BigDecimal saldoInicial) {
        super(numeroConta, cliente, saldoInicial);
    }
    
    @Override
    public TipoConta getTipo() {
        return TipoConta.POUPANCA;
    }
    
    @Override
    public void aplicarRendimento(BigDecimal percentual) {
        validarPercentual(percentual);
        
        BigDecimal rendimento = calcularRendimento(percentual);
        saldo = saldo.add(rendimento);
    }
    
    private void validarPercentual(BigDecimal percentual) {
        if (percentual == null) {
            throw new IllegalArgumentException("Percentual não pode ser nulo");
        }
        if (percentual.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Percentual não pode ser negativo");
        }
    }
    
    private BigDecimal calcularRendimento(BigDecimal percentual) {
        return saldo
                .multiply(percentual)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }
}