package exercicios.arrays_e_loops;

import static util.Testar.*;

/**
 * <h2>Exercício 05 — Verificar se um valor existe</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dado um array de números inteiros e um valor alvo ({@code alvo}),
 * retorne {@code true} se o valor estiver presente no array
 * e {@code false} caso contrário.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * [1, 2, 3, 4, 5], 3    -> true
 * [1, 2, 3, 4, 5], 7    -> false
 * [], 1                  -> false
 * [10, 20, 30], 10       -> true
 * [10, 20, 30], 30       -> true
 * [-5, 0, 5], -5         -> true
 * [42], 42               -> true
 * [42], 7                -> false
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use Streams.</li>
 *     <li>Não use {@code Arrays.binarySearch(...)} nem nenhum método de busca pronto.</li>
 *     <li>Use um loop.</li>
 *     <li>Não altere o array original.</li>
 *     <li>Array vazio deve retornar {@code false}.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n)</li>
 *     <li>Espaço: O(1)</li>
 * </ul>
 */
public class Exercicio05 {

    public static boolean existe(int[] numeros, int alvo) {

        for (int numero : numeros) {
            if (numero == alvo) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        iniciar(Exercicio05.class, 4, "existe");

        resultado("valor existe", true, existe(new int[]{1, 2, 3, 4, 5}, 3));
        resultado("valor nao existe", false, existe(new int[]{1, 2, 3, 4, 5}, 7));
        resultado("array vazio", false, existe(new int[]{}, 1));
        resultado("primeiro elemento", true, existe(new int[]{10, 20, 30}, 10));
        resultado("ultimo elemento", true, existe(new int[]{10, 20, 30}, 30));
        resultado("com negativos", true, existe(new int[]{-5, 0, 5}, -5));
        resultado("um elemento (existe)", true, existe(new int[]{42}, 42));
        resultado("um elemento (nao existe)", false, existe(new int[]{42}, 7));

        finalizar();
    }
}
