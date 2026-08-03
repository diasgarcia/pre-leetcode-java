package exercicios.arrays_e_loops;

import static util.Testar.*;

/**
 * <h2>Exercício 08 — Contar ocorrências de um número</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dado um array de números inteiros e um valor alvo ({@code alvo}),
 * retorne a quantidade de vezes que o valor aparece no array.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * [1, 2, 3, 2, 4, 2], 2    -> 3
 * [1, 2, 3, 4, 5], 7       -> 0
 * [], 1                     -> 0
 * [5, 5, 5, 5], 5           -> 4
 * [-2, -1, 0, -2, 3], -2    -> 2
 * [42], 42                  -> 1
 * [42], 7                   -> 0
 * [10, 20, 30], 99          -> 0
 * [0, 1, 0, 2, 0], 0        -> 3
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use Streams.</li>
 *     <li>Não use métodos prontos como {@code Collections.frequency(...)}.</li>
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
public class Exercicio08 {

    public static int contarOcorrencias(int[] numeros, int alvo) {

        int total = 0;
        for (int numero : numeros) {
            if (numero == alvo) total++;
        }

        return total;
    }

    public static void main(String[] args) {
        iniciar(Exercicio08.class, 5, "contarOcorrencias");

        resultado("array comum", 3, contarOcorrencias(new int[]{1, 2, 3, 2, 4, 2}, 2));
        resultado("valor nao existe", 0, contarOcorrencias(new int[]{1, 2, 3, 4, 5}, 7));
        resultado("array vazio", 0, contarOcorrencias(new int[]{}, 1));
        resultado("todos iguais", 4, contarOcorrencias(new int[]{5, 5, 5, 5}, 5));
        resultado("com negativos", 2, contarOcorrencias(new int[]{-2, -1, 0, -2, 3}, -2));
        resultado("um elemento (existe)", 1, contarOcorrencias(new int[]{42}, 42));
        resultado("um elemento (nao existe)", 0, contarOcorrencias(new int[]{42}, 7));
        resultado("nenhuma ocorrencia", 0, contarOcorrencias(new int[]{10, 20, 30}, 99));
        resultado("zero repetido", 3, contarOcorrencias(new int[]{0, 1, 0, 2, 0}, 0));

        finalizar();
    }
}
