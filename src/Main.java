import com.banco.modelo.*;
import com.banco.repositorio.*;
import com.banco.servico.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        ClienteRepositorio clienteRepo = new ClienteRepositorio();
        ContaRepositorio contaRepo = new ContaRepositorio();

        ServicoCliente servicoCliente = new ServicoCliente(clienteRepo);
        ServicoConta servicoConta = new ServicoConta(contaRepo);
        ServicoRelatorio servicoRelatorio = new ServicoRelatorio(clienteRepo, contaRepo);

        Cliente maria = servicoCliente.cadastrarCliente("Maria Souza", "12345678901");
        Cliente joao = servicoCliente.cadastrarCliente("João Pereira", "98765432100");

        servicoConta.criarContaCorrente("001", maria, BigDecimal.valueOf(1000));
        servicoConta.criarContaPoupanca("002", maria, BigDecimal.valueOf(500));
        servicoConta.criarContaCorrente("003", joao, BigDecimal.valueOf(200));

        servicoConta.depositar("003", BigDecimal.valueOf(100));
        servicoConta.aplicarRendimento("002", BigDecimal.valueOf(5));

        servicoRelatorio.imprimirResumoBanco();
    }
}
