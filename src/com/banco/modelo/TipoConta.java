package com.banco.modelo;

/**
 * Enumeração dos tipos de conta disponíveis no banco.
 */
public enum TipoConta {
    CORRENTE("Conta Corrente"),
    POUPANCA("Conta Poupança");
    
    private final String descricao;
    
    TipoConta(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}