package exercicios.arrays_e_loops;

/**
 * <h2>Exercício 09 — Verificar se o array está ordenado</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dado um array de números inteiros, verifique se os elementos estão
 * em ordem <strong>não-decrescente</strong>, ou seja, se cada elemento é
 * menor ou igual ao próximo ({@code numeros[i] <= numeros[i+1]}).
 * Retorne {@code true} se o array estiver ordenado e {@code false}
 * caso contrário.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * [1, 2, 3, 4, 5]        -> true
 * [3, 1, 4, 1, 5]        -> false
 * []                     -> true
 * [42]                   -> true
 * [1, 2, 2, 3, 4]        -> true
 * [5, 4, 3, 2, 1]        -> false
 * [7, 7, 7, 7]           -> true
 * [1, 2, 3, 5, 4]        -> false
 * [-5, -2, 0, 3]         -> true
 * [10, 20]               -> true
 * [20, 10]               -> false
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use Streams.</li>
 *     <li>Não use {@code Arrays.sort(...)} nem nenhum método de ordenação.</li>
 *     <li>Use um loop.</li>
 *     <li>Não altere o array original.</li>
 *     <li>Array vazio e array com um único elemento são considerados ordenados.</li>
 *     <li>Valores iguais consecutivos são permitidos (ordem não-decrescente).</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n)</li>
 *     <li>Espaço: O(1)</li>
 * </ul>
 */
public class Exercicio09 {

    public static boolean estaOrdenado(int[] numeros) {

        for (int i = 0; i < numeros.length - 1; i++) {
            if (numeros[i] > numeros[i+1]) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        util.Testar.iniciar(Exercicio09.class, 4, "estaOrdenado");

        util.Testar.resultado("ordenado crescente", true, estaOrdenado(new int[]{1, 2, 3, 4, 5}));
        util.Testar.resultado("nao ordenado", false, estaOrdenado(new int[]{3, 1, 4, 1, 5}));
        util.Testar.resultado("array vazio", true, estaOrdenado(new int[]{}));
        util.Testar.resultado("um elemento", true, estaOrdenado(new int[]{42}));
        util.Testar.resultado("com duplicados (nao-decrescente)", true, estaOrdenado(new int[]{1, 2, 2, 3, 4}));
        util.Testar.resultado("decrescente", false, estaOrdenado(new int[]{5, 4, 3, 2, 1}));
        util.Testar.resultado("todos iguais", true, estaOrdenado(new int[]{7, 7, 7, 7}));
        util.Testar.resultado("ordenado ate o final", false, estaOrdenado(new int[]{1, 2, 3, 5, 4}));
        util.Testar.resultado("negativos ordenados", true, estaOrdenado(new int[]{-5, -2, 0, 3}));
        util.Testar.resultado("dois elementos ordenados", true, estaOrdenado(new int[]{10, 20}));
        util.Testar.resultado("dois elementos nao ordenados", false, estaOrdenado(new int[]{20, 10}));

        util.Testar.finalizar();
    }
}
