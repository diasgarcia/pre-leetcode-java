package exercicios.arrays_e_loops;

/**
 * <h2>Exercício 02 — Contar números pares</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dado um array de números inteiros, retorne a quantidade de números pares
 * presentes nele. Um número é par quando o resto da divisão por 2 é zero
 * ({@code n % 2 == 0}).
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * [1, 2, 3, 4, 5, 6] -> 3
 * [2, 4, 6, 8]       -> 4
 * [1, 3, 5, 7]       -> 0
 * []                  -> 0
 * [-2, -1, 0, 1, 2]  -> 3
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use Streams.</li>
 *     <li>Use um loop.</li>
 *     <li>Não altere o array original.</li>
 *     <li>Array vazio deve retornar 0.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n)</li>
 *     <li>Espaço: O(1)</li>
 * </ul>
 */
public class Exercicio02 {

    public static int contarPares(int[] numeros) {
        // TODO: implemente sua solução
        return 0;
    }

    public static void main(String[] args) {
        util.Testar.iniciar(Exercicio02.class, 5, "contarPares");

        util.Testar.resultado("array comum", 3, contarPares(new int[]{1, 2, 3, 4, 5, 6}));
        util.Testar.resultado("todos pares", 4, contarPares(new int[]{2, 4, 6, 8}));
        util.Testar.resultado("nenhum par", 0, contarPares(new int[]{1, 3, 5, 7}));
        util.Testar.resultado("array vazio", 0, contarPares(new int[]{}));
        util.Testar.resultado("com negativos e zero", 3, contarPares(new int[]{-2, -1, 0, 1, 2}));
        util.Testar.resultado("um elemento impar", 0, contarPares(new int[]{7}));
        util.Testar.resultado("um elemento par", 1, contarPares(new int[]{8}));

        util.Testar.finalizar();
    }
}
