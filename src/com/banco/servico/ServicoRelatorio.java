package com.banco.servico;

import com.banco.modelo.ContaBancaria;
import com.banco.modelo.TipoConta;
import com.banco.repositorio.ContaRepositorio;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Serviço responsável por gerar relatórios consolidados e ordenados.
 */
public class ServicoRelatorio {

    private final ContaRepositorio contaRepositorio;

    public ServicoRelatorio(ContaRepositorio contaRepositorio) {
        this.contaRepositorio = contaRepositorio;
    }

    public List<ContaBancaria> listarContasPorSaldoDecrescente() {
        return contaRepositorio.listarTodos().stream()
                .sorted(Comparator.comparing(ContaBancaria::consultarSaldo).reversed())
                .toList();
    }

    public Map<TipoConta, Map<String, Object>> gerarRelatorioPorTipo() {
        return contaRepositorio.listarTodos().stream()
                .collect(Collectors.groupingBy(
                        ContaBancaria::getTipo,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                contas -> {
                                    BigDecimal totalSaldo = contas.stream()
                                            .map(ContaBancaria::consultarSaldo)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                                    Map<String, Object> resumo = new LinkedHashMap<>();
                                    resumo.put("quantidade", contas.size());
                                    resumo.put("totalSaldo", totalSaldo);
                                    return resumo;
                                }
                        )
                ));
    }

    public Map<String, Object> gerarResumoGeral() {
        List<ContaBancaria> contas = contaRepositorio.listarTodos();

        BigDecimal totalSaldo = contas.stream()
                .map(ContaBancaria::consultarSaldo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> resumo = new LinkedHashMap<>();
        resumo.put("totalContas", contas.size());
        resumo.put("saldoTotalBanco", totalSaldo);
        return resumo;
    }

    public void imprimirRelatorioCompleto() {
        System.out.println("=== RELATÓRIO BANCÁRIO ===");

        System.out.println("\nContas ordenadas por saldo (decrescente):");
        listarContasPorSaldoDecrescente()
                .forEach(c -> System.out.printf("- %s | %s | Saldo: R$ %.2f%n",
                        c.getTipo().getDescricao(),
                        c.getCliente().getNome(),
                        c.consultarSaldo()));

        System.out.println("\nResumo por tipo de conta:");
        gerarRelatorioPorTipo().forEach((tipo, dados) -> {
            System.out.printf("%s → %d contas | Total: R$ %.2f%n",
                    tipo.getDescricao(),
                    dados.get("quantidade"),
                    ((BigDecimal) dados.get("totalSaldo")));
        });

        Map<String, Object> resumoGeral = gerarResumoGeral();
        System.out.printf("%nTotal de contas: %d%nSaldo total do banco: R$ %.2f%n",
                resumoGeral.get("totalContas"),
                resumoGeral.get("saldoTotalBanco"));
    }
}
