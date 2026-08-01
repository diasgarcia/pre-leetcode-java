package exercicios.arrays_e_loops;

/**
 * <h2>Exercício 01 — Somar todos os elementos</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dado um array de números inteiros, retorne a soma de todos os elementos.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * [1, 2, 3]   -> 6
 * [10, -5, 2] -> 7
 * []           -> 0
 * [-2, -3]    -> -5
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
public class Exercicio01 {

    public static int somar(int[] numeros) {
        // TODO: implemente sua solução
        return 0;
    }

    public static void main(String[] args) {
        util.Testar.resultado("array comum",       6, somar(new int[]{1, 2, 3}));
        util.Testar.resultado("com negativos",      7, somar(new int[]{10, -5, 2}));
        util.Testar.resultado("array vazio",        0, somar(new int[]{}));
        util.Testar.resultado("só negativos",      -5, somar(new int[]{-2, -3}));
        util.Testar.resultado("um elemento",       42, somar(new int[]{42}));
        util.Testar.resultado("soma zero (anula)",  0, somar(new int[]{5, -5, 3, -3}));
    }
}
