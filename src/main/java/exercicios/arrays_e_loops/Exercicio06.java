package exercicios.arrays_e_loops;

/**
 * <h2>Exercício 06 — Retornar o índice de um valor</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dado um array de números inteiros e um valor alvo ({@code alvo}),
 * retorne o <strong>índice da primeira ocorrência</strong> desse valor no array.
 * Se o valor não estiver presente, retorne {@code -1}.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * [1, 2, 3, 4, 5], 3    -> 2
 * [1, 2, 3, 4, 5], 7    -> -1
 * [], 1                  -> -1
 * [10, 20, 30], 10       -> 0
 * [10, 20, 30], 30       -> 2
 * [-5, 0, 5], -5         -> 0
 * [5, 2, 5, 8], 5        -> 0
 * [42], 42               -> 0
 * [42], 7                -> -1
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use Streams.</li>
 *     <li>Não use {@code Arrays.binarySearch(...)} nem nenhum método de busca pronto.</li>
 *     <li>Use um loop.</li>
 *     <li>Não altere o array original.</li>
 *     <li>Array vazio deve retornar {@code -1}.</li>
 *     <li>Se houver múltiplas ocorrências, retorne o índice da <strong>primeira</strong>.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n)</li>
 *     <li>Espaço: O(1)</li>
 * </ul>
 */
public class Exercicio06 {

    public static int indiceDe(int[] numeros, int alvo) {
        // TODO: implemente sua solução
        return -1;
    }

    public static void main(String[] args) {
        util.Testar.iniciar(Exercicio06.class, 4, "indiceDe");

        util.Testar.resultado("valor no meio", 2, indiceDe(new int[]{1, 2, 3, 4, 5}, 3));
        util.Testar.resultado("valor nao existe", -1, indiceDe(new int[]{1, 2, 3, 4, 5}, 7));
        util.Testar.resultado("array vazio", -1, indiceDe(new int[]{}, 1));
        util.Testar.resultado("primeiro elemento", 0, indiceDe(new int[]{10, 20, 30}, 10));
        util.Testar.resultado("ultimo elemento", 2, indiceDe(new int[]{10, 20, 30}, 30));
        util.Testar.resultado("com negativos", 0, indiceDe(new int[]{-5, 0, 5}, -5));
        util.Testar.resultado("primeira ocorrencia (repetido)", 0, indiceDe(new int[]{5, 2, 5, 8}, 5));
        util.Testar.resultado("um elemento (existe)", 0, indiceDe(new int[]{42}, 42));
        util.Testar.resultado("um elemento (nao existe)", -1, indiceDe(new int[]{42}, 7));

        util.Testar.finalizar();
    }
}
