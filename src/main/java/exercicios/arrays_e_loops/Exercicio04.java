package exercicios.arrays_e_loops;

import static util.Testar.*;

/**
 * <h2>Exercício 04 — Encontrar o menor número</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dado um array de números inteiros com pelo menos um elemento,
 * retorne o menor valor presente nele.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * [3, 7, 2, 9, 4]  -> 2
 * [42]              -> 42
 * [-5, -2, -10, -1] -> -10
 * [7, 7, 7, 7]     -> 7
 * [-3, 0, 5, -2]   -> -3
 * [1, 10, 3, 4, 2] -> 1
 * [5, 6, 7, 1]     -> 1
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
public class Exercicio04 {

    public static int encontrarMenor(int[] numeros) {

        int menor = numeros[0];
        for (int numero : numeros) {
            if (numero < menor) {
                menor = numero;
            }
        }

        return menor;
    }

    public static void main(String[] args) {
        iniciar(Exercicio04.class, 5, "encontrarMenor");

        resultado("array comum", 2, encontrarMenor(new int[]{3, 7, 2, 9, 4}));
        resultado("um elemento", 42, encontrarMenor(new int[]{42}));
        resultado("todos negativos", -10, encontrarMenor(new int[]{-5, -2, -10, -1}));
        resultado("todos iguais", 7, encontrarMenor(new int[]{7, 7, 7, 7}));
        resultado("com negativos e positivos", -3, encontrarMenor(new int[]{-3, 0, 5, -2}));
        resultado("menor no inicio", 1, encontrarMenor(new int[]{1, 10, 3, 4, 2}));
        resultado("menor no final", 1, encontrarMenor(new int[]{5, 6, 7, 1}));

        finalizar();
    }
}
