package calculadora.modelo;

/**
 * Calculadora
 * Classe responsável pelas operações matemáticas básicas.
 * Este arquivo faz parte da estrutura do projeto:
 * 
 * src/
 *   calculadora/
 *     modelo/Calculadora.java        -> lógica das operações
 *     interfacegrafica/JanelaCalculadora.java -> interface Swing
 *     controle/ControladorCalculadora.java   -> controla eventos
 *     app/ProgramaPrincipal.java              -> inicia a aplicação
 * 
 * Todos os nomes, variáveis e comentários estão em português conforme solicitado.
 */
public class Calculadora {

    // Construtor padrão
    public Calculadora() {
        // não há estado persistente necessário para esta calculadora básica
    }

    /**
     * Soma dois valores e retorna o resultado.
     */
    public double somar(double a, double b) {
        return a + b;
    }

    /**
     * Subtrai b de a e retorna o resultado.
     */
    public double subtrair(double a, double b) {
        return a - b;
    }

    /**
     * Multiplica dois valores e retorna o resultado.
     */
    public double multiplicar(double a, double b) {
        return a * b;
    }

    /**
     * Divide a por b e retorna o resultado.
     * Lança ArithmeticException em caso de divisão por zero.
     */
    public double dividir(double a, double b) {
        if (b == 0.0) {
            throw new ArithmeticException("Divisão por zero");
        }
        return a / b;
    }

    /**
     * Método utilitário que recebe uma operação em forma de string e aplica
     * a operação apropriada entre a e b.
     * Operações suportadas: "+", "-", "*", "/".
     */
    public double calcular(double a, double b, String operacao) {
        switch (operacao) {
            case "+":
                return somar(a, b);
            case "-":
                return subtrair(a, b);
            case "*":
                return multiplicar(a, b);
            case "/":
                return dividir(a, b);
            default:
                // Se a operação não for reconhecida, retorna b (comportamento neutro)
                return b;
        }
    }
}
