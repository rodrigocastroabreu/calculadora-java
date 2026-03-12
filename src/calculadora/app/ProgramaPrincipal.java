package calculadora.app;

import javax.swing.SwingUtilities;
import calculadora.controle.ControladorCalculadora;
import calculadora.interfacegrafica.JanelaCalculadora;

/**
 * ProgramaPrincipal
 * Classe principal que inicia a aplicação da calculadora.
 */
public class ProgramaPrincipal {

    public static void main(String[] args) {
        // Inicializa a GUI na thread de eventos do Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JanelaCalculadora janela = new JanelaCalculadora();
                // Registra o controlador que conecta a interface com a lógica
                new ControladorCalculadora(janela);
                janela.setVisible(true);
            }
        });
    }
}
