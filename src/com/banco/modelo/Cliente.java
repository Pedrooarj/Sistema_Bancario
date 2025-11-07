package com.banco.modelo;

import java.util.Objects;

/**
 * Representa um cliente do banco.
 * Responsável por armazenar informações básicas do cliente.
 */
public class Cliente {
    private final String nome;
    private final String cpf;

    public Cliente(String nome, String cpf) {
        validarNome(nome);
        validarCpf(cpf);
        
        this.nome = nome;
        this.cpf = cpf;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio");
        }
        if (nome.trim().length() < 3) {
            throw new IllegalArgumentException("Nome do cliente deve ter pelo menos 3 caracteres");
        }
    }

    private void validarCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos");
        }
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Cliente cliente = (Cliente) obj;
        return cpf.equals(cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return String.format("Cliente: %s (CPF: %s)", nome, cpf);
    }
}