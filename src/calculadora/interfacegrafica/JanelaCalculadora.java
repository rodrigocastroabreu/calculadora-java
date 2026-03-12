package calculadora.interfacegrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * JanelaCalculadora
 * Classe responsável por criar a interface gráfica (Swing) da calculadora.
 * Contém o visor (campo de texto) e os botões organizados em grade.
 */
public class JanelaCalculadora extends JFrame {

    // Campo de texto que funciona como visor da calculadora
    private JTextField visor;

    // Painel que conterá os botões
    private JPanel painelBotoes;

    // Botões principais serão expostos para que o controlador possa adicionar listeners
    public JButton[] botoesNumeros; // 0..9
    public JButton botaoPonto;
    public JButton botaoSoma;
    public JButton botaoSubtracao;
    public JButton botaoMultiplicacao;
    public JButton botaoDivisao;
    public JButton botaoIgual;
    public JButton botaoLimpar;
    public JButton botaoApagar;

    /**
     * Construtor que monta a interface gráfica.
     */
    public JanelaCalculadora() {
        super("Calculadora Visual - POO (pt-BR)");
        inicializarComponentes();
        configurarJanela();
    }

    // Cria e organiza os componentes da interface
    private void inicializarComponentes() {
        // Configura o visor
        visor = new JTextField();
        visor.setEditable(false); // o usuário não digita diretamente no JTextField
        visor.setHorizontalAlignment(JTextField.RIGHT);
        visor.setFont(new Font("Monospaced", Font.BOLD, 24));
        visor.setPreferredSize(new Dimension(240, 50));
        visor.setBorder(BorderFactory.createCompoundBorder(visor.getBorder(), BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        visor.setBackground(Color.WHITE);

        // Cria painel de botões com GridLayout
        painelBotoes = new JPanel(new GridLayout(5, 4, 5, 5)); // 5 linhas x 4 colunas

        // Inicializa botões
        botoesNumeros = new JButton[10];
        for (int i = 0; i <= 9; i++) {
            botoesNumeros[i] = new JButton(String.valueOf(i));
            botoesNumeros[i].setFont(new Font("SansSerif", Font.BOLD, 18));
        }

        botaoPonto = new JButton(".");
        botaoIgual = new JButton("=");
        botaoSoma = new JButton("+");
        botaoSubtracao = new JButton("-");
        botaoMultiplicacao = new JButton("*");
        botaoDivisao = new JButton("/");
        botaoLimpar = new JButton("C");
        botaoApagar = new JButton("←");

        // Ajusta fonte dos botões de operação
        JButton[] botoesOp = {botaoPonto, botaoIgual, botaoSoma, botaoSubtracao, botaoMultiplicacao, botaoDivisao, botaoLimpar, botaoApagar};
        for (JButton b : botoesOp) {
            b.setFont(new Font("SansSerif", Font.BOLD, 18));
        }

        // Adiciona os botões ao painel (ordem visual desejada)
        // Linha 1: Limpar, Apagar, (vazio), Divisão
        painelBotoes.add(botaoLimpar);
        painelBotoes.add(botaoApagar);
        painelBotoes.add(new JPanel()); // espaço vazio para alinhar
        painelBotoes.add(botaoDivisao);

        // Linha 2: 7 8 9 *
        painelBotoes.add(botoesNumeros[7]);
        painelBotoes.add(botoesNumeros[8]);
        painelBotoes.add(botoesNumeros[9]);
        painelBotoes.add(botaoMultiplicacao);

        // Linha 3: 4 5 6 -
        painelBotoes.add(botoesNumeros[4]);
        painelBotoes.add(botoesNumeros[5]);
        painelBotoes.add(botoesNumeros[6]);
        painelBotoes.add(botaoSubtracao);

        // Linha 4: 1 2 3 +
        painelBotoes.add(botoesNumeros[1]);
        painelBotoes.add(botoesNumeros[2]);
        painelBotoes.add(botoesNumeros[3]);
        painelBotoes.add(botaoSoma);

        // Linha 5: 0 . = (vazio)
        painelBotoes.add(botoesNumeros[0]);
        painelBotoes.add(botaoPonto);
        painelBotoes.add(botaoIgual);
        painelBotoes.add(new JPanel());

        // Define espaçamento interno
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Layout principal
        this.setLayout(new BorderLayout(5, 5));
        this.add(visor, BorderLayout.NORTH);
        this.add(painelBotoes, BorderLayout.CENTER);
    }

    // Configurações básicas da janela
    private void configurarJanela() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null); // centraliza na tela
        this.setResizable(false);
    }

    // Métodos de acesso para o controlador manipular o visor
    public JTextField getVisor() {
        return visor;
    }
}
