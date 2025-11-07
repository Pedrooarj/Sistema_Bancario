package com.banco.modelo;

import java.math.BigDecimal;

/**
 * Interface para contas que podem render juros.
 * Aplicação do ISP - Interface Segregation Principle.
 */
public interface Rendivel {
    
    /**
     * Aplica rendimento sobre o saldo da conta.
     * @param percentual percentual de rendimento (ex: 0.5 para 0,5%)
     * @throws IllegalArgumentException se o percentual for inválido
     */
    void aplicarRendimento(BigDecimal percentual);
}