package com.banco.servico;

import com.banco.modelo.ContaBancaria;
import com.banco.modelo.ContaPoupanca;
import com.banco.modelo.ContaCorrente;
import com.banco.modelo.Cliente;
import com.banco.repositorio.ContaRepositorio;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Serviço responsável por operações de contas bancárias.
 * Lida com depósitos, saques, transferências e rendimentos.
 */
public class ServicoConta {

    private final ContaRepositorio contaRepositorio;

    public ServicoConta(ContaRepositorio contaRepositorio) {
        this.contaRepositorio = contaRepositorio;
    }

    public ContaBancaria criarContaCorrente(String numero, Cliente cliente, BigDecimal saldoInicial) {
        validarNovaConta(numero);
        ContaBancaria conta = new ContaCorrente(numero, cliente, saldoInicial);
        contaRepositorio.salvar(conta);
        return conta;
    }

    public ContaBancaria criarContaPoupanca(String numero, Cliente cliente, BigDecimal saldoInicial) {
        validarNovaConta(numero);
        ContaBancaria conta = new ContaPoupanca(numero, cliente, saldoInicial);
        contaRepositorio.salvar(conta);
        return conta;
    }

    public void depositar(String numeroConta, BigDecimal valor) {
        ContaBancaria conta = buscarContaObrigatoria(numeroConta);
        conta.depositar(valor);
    }

    public void sacar(String numeroConta, BigDecimal valor) {
        ContaBancaria conta = buscarContaObrigatoria(numeroConta);
        boolean sucesso = conta.sacar(valor);
        if (!sucesso) {
            throw new IllegalArgumentException("Saldo insuficiente para saque.");
        }
    }

    public void transferir(String contaOrigemNum, String contaDestinoNum, BigDecimal valor) {
        if (contaOrigemNum.equals(contaDestinoNum)) {
            throw new IllegalArgumentException("A conta de origem e destino devem ser diferentes.");
        }

        ContaBancaria origem = buscarContaObrigatoria(contaOrigemNum);
        ContaBancaria destino = buscarContaObrigatoria(contaDestinoNum);

        if (!origem.sacar(valor)) {
            throw new IllegalArgumentException("Saldo insuficiente na conta de origem.");
        }

        destino.depositar(valor);
    }

    public void aplicarRendimentoEmTodas(BigDecimal percentual) {
    if (percentual == null || percentual.compareTo(BigDecimal.ZERO) < 0) {
        throw new IllegalArgumentException("Percentual inválido para rendimento.");
    }

    contaRepositorio.listarTodos().forEach(conta -> {
        try {
            aplicarRendimento(conta.getNumeroConta(), percentual);
        } catch (IllegalArgumentException e) {
        }
    });
}

    private void aplicarRendimento(String numeroConta, BigDecimal percentual) {
        ContaBancaria conta = buscarContaObrigatoria(numeroConta);

        if (conta instanceof ContaPoupanca poupanca) {
            poupanca.aplicarRendimento(percentual);
        } else {
            throw new IllegalArgumentException("Rendimento só pode ser aplicado em contas poupança.");
        }
    }

    public BigDecimal consultarSaldo(String numeroConta) {
        return buscarContaObrigatoria(numeroConta).consultarSaldo();
    }

    private void validarNovaConta(String numeroConta) {
        if (contaRepositorio.existePorNumero(numeroConta)) {
            throw new IllegalArgumentException("Já existe uma conta com este número.");
        }
    }

    private ContaBancaria buscarContaObrigatoria(String numeroConta) {
        Optional<ContaBancaria> conta = contaRepositorio.buscarPorId(numeroConta);
        if (conta.isEmpty()) {
            throw new IllegalArgumentException("Conta não encontrada: " + numeroConta);
        }
        return conta.get();
    }
}
