import com.banco.facade.SistemaBancario;
import com.banco.ui.BancoConsoleUI;


public class Main {
    public static void main(String[] args) {
        SistemaBancario sistema = new SistemaBancario();
        BancoConsoleUI bancoConsoleUI = new BancoConsoleUI(sistema);
        bancoConsoleUI.iniciar();
    }
}