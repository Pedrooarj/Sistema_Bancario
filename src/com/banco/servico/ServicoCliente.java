package com.banco.servico;

import com.banco.modelo.Cliente;
import com.banco.repositorio.ClienteRepositorio;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por operações relacionadas a clientes.
 * Aplica regras de negócio de criação e busca.
 */
public class ServicoCliente {

    private final ClienteRepositorio clienteRepositorio;

    public ServicoCliente(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    public Cliente cadastrarCliente(String nome, String cpf) {
        if (clienteRepositorio.existePorCpf(cpf)) {
            throw new IllegalArgumentException("Já existe um cliente com este CPF");
        }

        Cliente cliente = new Cliente(nome, cpf);
        clienteRepositorio.salvar(cliente);
        return cliente;
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepositorio.buscarPorId(cpf);
    }

    public List<Cliente> listarTodos() {
        return clienteRepositorio.listarTodos();
    }

    public void removerCliente(String cpf) {
        clienteRepositorio.remover(cpf);
    }
}
