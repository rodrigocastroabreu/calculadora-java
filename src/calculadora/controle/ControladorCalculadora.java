package calculadora.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import calculadora.interfacegrafica.JanelaCalculadora;
import calculadora.modelo.Calculadora;

/**
 * ControladorCalculadora
 * Classe responsável por conectar a interface com a lógica (modelo).
 * Implementa ActionListener para reagir aos cliques dos botões.
 */
public class ControladorCalculadora implements ActionListener {

    private JanelaCalculadora janela;
    private Calculadora calculadora;

    // Estado atual da calculadora: valor acumulado, operação pendente e texto em exibição
    private double valorAcumulado = 0.0;
    private String operacaoPendente = ""; // "+", "-", "*", "/" ou vazio
    private boolean limparVisorAoDigitar = false; // quando true, o próximo dígito substitui o visor

    public ControladorCalculadora(JanelaCalculadora janela) {
        this.janela = janela;
        this.calculadora = new Calculadora();
        registrarListeners();
    }

    // Registra este controlador como listener para todos os botões relevantes
    private void registrarListeners() {
        // Números
        for (int i = 0; i <= 9; i++) {
            janela.botoesNumeros[i].addActionListener(this);
        }

        // Operações e outros
        janela.botaoPonto.addActionListener(this);
        janela.botaoSoma.addActionListener(this);
        janela.botaoSubtracao.addActionListener(this);
        janela.botaoMultiplicacao.addActionListener(this);
        janela.botaoDivisao.addActionListener(this);
        janela.botaoIgual.addActionListener(this);
        janela.botaoLimpar.addActionListener(this);
        janela.botaoApagar.addActionListener(this);
    }

    // Lida com os eventos dos botões
    @Override
    public void actionPerformed(ActionEvent e) {
        Object origem = e.getSource();
        JTextField visor = janela.getVisor();

        // Números
        for (int i = 0; i <= 9; i++) {
            if (origem == janela.botoesNumeros[i]) {
                digitarNumero(String.valueOf(i));
                return;
            }
        }

        // Ponto decimal
        if (origem == janela.botaoPonto) {
            digitarPonto();
            return;
        }

        // Operações
        if (origem == janela.botaoSoma) {
            aplicarOperacao("+");
            return;
        }
        if (origem == janela.botaoSubtracao) {
            aplicarOperacao("-");
            return;
        }
        if (origem == janela.botaoMultiplicacao) {
            aplicarOperacao("*");
            return;
        }
        if (origem == janela.botaoDivisao) {
            aplicarOperacao("/");
            return;
        }

        // Igual
        if (origem == janela.botaoIgual) {
            calcularResultado();
            return;
        }

        // Limpar
        if (origem == janela.botaoLimpar) {
            limparTudo();
            return;
        }

        // Apagar último caractere
        if (origem == janela.botaoApagar) {
            apagarUltimo();
            return;
        }
    }

    // Insere um dígito no visor, cuidando do estado de limpeza
    private void digitarNumero(String digito) {
        JTextField visor = janela.getVisor();
        if (limparVisorAoDigitar || "0".equals(visor.getText())) {
            visor.setText(digito);
            limparVisorAoDigitar = false;
        } else {
            visor.setText(visor.getText() + digito);
        }
    }

    // Insere ponto decimal, evitando múltiplos pontos
    private void digitarPonto() {
        JTextField visor = janela.getVisor();
        if (limparVisorAoDigitar) {
            // se for para limpar, inicia com "0."
            visor.setText("0.");
            limparVisorAoDigitar = false;
            return;
        }
        if (!visor.getText().contains(".")) {
            if (visor.getText().isEmpty()) {
                visor.setText("0.");
            } else {
                visor.setText(visor.getText() + ".");
            }
        }
    }

    // Define ou altera a operação pendente acumulando o valor atual
    private void aplicarOperacao(String operacao) {
        JTextField visor = janela.getVisor();
        try {
            double valorVisor = parseVisor();
            if (!operacaoPendente.isEmpty()) {
                // Se já havia operação pendente, calcula primeiro
                valorAcumulado = calculadora.calcular(valorAcumulado, valorVisor, operacaoPendente);
            } else {
                valorAcumulado = valorVisor;
            }
            operacaoPendente = operacao;
            limparVisorAoDigitar = true;
            visor.setText(formatarNumero(valorAcumulado));
        } catch (NumberFormatException ex) {
            // Caso o visor não contenha um número válido, limpa
            visor.setText("0");
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(janela, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            limparTudo();
        }
    }

    // Calcula o resultado com a operação pendente
    private void calcularResultado() {
        JTextField visor = janela.getVisor();
        if (operacaoPendente.isEmpty()) {
            // Nada a calcular
            return;
        }
        try {
            double valorVisor = parseVisor();
            double resultado = calculadora.calcular(valorAcumulado, valorVisor, operacaoPendente);
            visor.setText(formatarNumero(resultado));
            // Reseta estado
            valorAcumulado = 0.0;
            operacaoPendente = "";
            limparVisorAoDigitar = true;
        } catch (NumberFormatException ex) {
            visor.setText("0");
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(janela, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            limparTudo();
        }
    }

    // Limpa todo o estado da calculadora
    private void limparTudo() {
        janela.getVisor().setText("0");
        valorAcumulado = 0.0;
        operacaoPendente = "";
        limparVisorAoDigitar = false;
    }

    // Apaga o último caractere do visor
    private void apagarUltimo() {
        JTextField visor = janela.getVisor();
        String texto = visor.getText();
        if (texto == null || texto.isEmpty() || limparVisorAoDigitar) {
            visor.setText("0");
            limparVisorAoDigitar = false;
            return;
        }
        if (texto.length() <= 1) {
            visor.setText("0");
        } else {
            visor.setText(texto.substring(0, texto.length() - 1));
        }
    }

    // Converte o texto do visor para double, tratando vírgula como separador se necessário
    private double parseVisor() {
        String texto = janela.getVisor().getText();
        if (texto == null || texto.isEmpty()) {
            return 0.0;
        }
        // O visor usa ponto como separador decimal
        return Double.parseDouble(texto);
    }

    // Formata o número removendo .0 desnecessário
    private String formatarNumero(double valor) {
        if (valor == (long) valor) {
            return String.format("%d", (long) valor);
        } else {
            return String.valueOf(valor);
        }
    }
}
