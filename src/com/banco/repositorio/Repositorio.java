package com.banco.repositorio;

import java.util.List;
import java.util.Optional;

/**
 * Interface genérica para operações CRUD em repositórios.
 */
public interface Repositorio<T, ID> {

    void salvar(T entidade);

    Optional<T> buscarPorId(ID id);

    List<T> listarTodos();

    void remover(ID id);
}