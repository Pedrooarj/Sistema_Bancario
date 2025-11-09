package com.banco.repositorio;

import com.banco.modelo.ContaBancaria;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ArrayList;

/**
 * Repositório responsável por gerenciar contas bancárias.
 */
public class ContaRepositorio implements Repositorio<ContaBancaria, String> {

    private final Map<String, ContaBancaria> contas = new HashMap<>();

    @Override
    public void salvar(ContaBancaria conta) {
        Objects.requireNonNull(conta, "Conta não pode ser nula");
        contas.put(conta.getNumeroConta(), conta);
    }

    @Override
    public Optional<ContaBancaria> buscarPorId(String numeroConta) {
        return Optional.ofNullable(contas.get(numeroConta));
    }

    @Override
    public List<ContaBancaria> listarTodos() {
        return new ArrayList<>(contas.values());
    }

    @Override
    public void remover(String numeroConta) {
        contas.remove(numeroConta);
    }

    public boolean existePorNumero(String numeroConta) {
        return contas.containsKey(numeroConta);
    }

    /**
     * Retorna todas as contas de um cliente específico.
     */
    public List<ContaBancaria> buscarPorCpfCliente(String cpf) {
        return contas.values().stream()
                .filter(c -> c.getCliente().getCpf().equals(cpf))
                .toList();
    }
}
