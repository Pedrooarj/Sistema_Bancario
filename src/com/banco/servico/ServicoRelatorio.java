package com.banco.servico;

import com.banco.modelo.ContaBancaria;
import com.banco.modelo.ContaPoupanca;
import com.banco.modelo.TipoConta;
import com.banco.repositorio.ClienteRepositorio;
import com.banco.repositorio.ContaRepositorio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço responsável por gerar relatórios do sistema bancário.
 * Não altera estado — apenas consulta e agrega informações.
 */
public class ServicoRelatorio {

    private final ClienteRepositorio clienteRepositorio;
    private final ContaRepositorio contaRepositorio;

    public ServicoRelatorio(ClienteRepositorio clienteRepositorio, ContaRepositorio contaRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
        this.contaRepositorio = contaRepositorio;
    }

    public BigDecimal calcularSaldoTotalBanco() {
        return contaRepositorio.listarTodos().stream()
                .map(ContaBancaria::consultarSaldo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long contarContasPorTipo(TipoConta tipo) {
        return contaRepositorio.listarTodos().stream()
                .filter(c -> c.getTipo() == tipo)
                .count();
    }

    public Map<TipoConta, Long> resumoContasPorTipo() {
        return contaRepositorio.listarTodos().stream()
                .collect(Collectors.groupingBy(ContaBancaria::getTipo, Collectors.counting()));
    }

    public List<ContaPoupanca> listarContasPoupancaComRendimentoAcima(BigDecimal valorMinimo) {
        return contaRepositorio.listarTodos().stream()
                .filter(c -> c instanceof ContaPoupanca poupanca && poupanca.consultarSaldo().compareTo(valorMinimo) > 0)
                .map(c -> (ContaPoupanca) c)
                .toList();
    }

    public void imprimirResumoBanco() {
        System.out.println("=== RELATÓRIO GERAL DO BANCO ===");
        System.out.println("Total de clientes: " + clienteRepositorio.listarTodos().size());
        System.out.println("Total de contas: " + contaRepositorio.listarTodos().size());
        System.out.println("Saldo total: R$ " + calcularSaldoTotalBanco());
        System.out.println("Contas por tipo: " + resumoContasPorTipo());
    }
}
