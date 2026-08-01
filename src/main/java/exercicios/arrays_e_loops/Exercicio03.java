package exercicios.arrays_e_loops;

/**
 * <h2>Exercício 03 — Encontrar o maior número</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dado um array de números inteiros com pelo menos um elemento,
 * retorne o maior valor presente nele.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * [3, 7, 2, 9, 4] -> 9
 * [42]             -> 42
 * [-5, -2, -10, -1] -> -1
 * [7, 7, 7, 7]    -> 7
 * [-3, 0, 5, -2]  -> 5
 * [10, 3, 4, 2]   -> 10
 * [1, 2, 3, 10]   -> 10
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use Streams.</li>
 *     <li>Não use {@code Arrays.sort(...)} nem nenhum método de ordenação.</li>
 *     <li>Use um loop.</li>
 *     <li>Não altere o array original.</li>
 *     <li>O array tem pelo menos um elemento.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n)</li>
 *     <li>Espaço: O(1)</li>
 * </ul>
 */
public class Exercicio03 {

    public static int encontrarMaior(int[] numeros) {
        // TODO: implemente sua solução
        return 0;
    }

    public static void main(String[] args) {
        util.Testar.iniciar(Exercicio03.class, 5, "encontrarMaior");

        util.Testar.resultado("array comum", 9, encontrarMaior(new int[]{3, 7, 2, 9, 4}));
        util.Testar.resultado("um elemento", 42, encontrarMaior(new int[]{42}));
        util.Testar.resultado("todos negativos", -1, encontrarMaior(new int[]{-5, -2, -10, -1}));
        util.Testar.resultado("todos iguais", 7, encontrarMaior(new int[]{7, 7, 7, 7}));
        util.Testar.resultado("com negativos e positivos", 5, encontrarMaior(new int[]{-3, 0, 5, -2}));
        util.Testar.resultado("maior no inicio", 10, encontrarMaior(new int[]{10, 3, 4, 2}));
        util.Testar.resultado("maior no final", 10, encontrarMaior(new int[]{1, 2, 3, 10}));

        util.Testar.finalizar();
    }
}
