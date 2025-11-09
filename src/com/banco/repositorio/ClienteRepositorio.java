package com.banco.repositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ArrayList;

import com.banco.modelo.Cliente;

/**
 * Repositório responsável por gerenciar clientes.
 * Implementação simples em memória (poderia ser substituída por JDBC, JPA, etc.).
 */
public class ClienteRepositorio implements Repositorio<Cliente, String> {

    private final Map<String, Cliente> clientes = new HashMap<>();

    @Override
    public void salvar(Cliente cliente) {
        Objects.requireNonNull(cliente, "Cliente não pode ser nulo");
        clientes.put(cliente.getCpf(), cliente);
    }

    @Override
    public Optional<Cliente> buscarPorId(String cpf) {
        return Optional.ofNullable(clientes.get(cpf));
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes.values());
    }

    @Override
    public void remover(String cpf) {
        clientes.remove(cpf);
    }

    public boolean existePorCpf(String cpf) {
        return clientes.containsKey(cpf);
    }
}
