package exercicios.arrays_e_loops;

import static util.Testar.*;

/**
 * <h2>Exercício 07 — Inverter um array</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dado um array de números inteiros, retorne um <strong>novo array</strong>
 * com os elementos na ordem inversa. O array original não deve ser modificado.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * [1, 2, 3, 4]      -> [4, 3, 2, 1]
 * [42]              -> [42]
 * []                -> []
 * [10, -5, 0, 7]    -> [7, 0, -5, 10]
 * [5, 9]            -> [9, 5]
 * [1, 2, 1]         -> [1, 2, 1]
 * [7, 7, 8, 7]      -> [7, 8, 7, 7]
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use Streams.</li>
 *     <li>Não use métodos prontos como {@code Collections.reverse(...)}.</li>
 *     <li>Use um loop.</li>
 *     <li>Não altere o array original.</li>
 *     <li>Retorne um <strong>novo array</strong> com os elementos invertidos.</li>
 *     <li>Array vazio deve retornar um array vazio.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n)</li>
 *     <li>Espaço: O(n)</li>
 * </ul>
 */
public class Exercicio07 {

    public static int[] inverter(int[] numeros) {

        int[] invertido = new int[numeros.length];

        for (int i = 0; i < numeros.length; i++) {
            invertido[numeros.length - 1 - i] = numeros[i];
        }

        return invertido;
    }

    public static void main(String[] args) {
        iniciar(Exercicio07.class, 3, "inverter");

        resultado("array comum", new int[]{4, 3, 2, 1}, inverter(new int[]{1, 2, 3, 4}));
        resultado("um elemento", new int[]{42}, inverter(new int[]{42}));
        resultado("array vazio", new int[]{}, inverter(new int[]{}));
        resultado("com negativos", new int[]{7, 0, -5, 10}, inverter(new int[]{10, -5, 0, 7}));
        resultado("dois elementos", new int[]{9, 5}, inverter(new int[]{5, 9}));
        resultado("palindromo", new int[]{1, 2, 1}, inverter(new int[]{1, 2, 1}));
        resultado("valores repetidos", new int[]{7, 8, 7, 7}, inverter(new int[]{7, 7, 8, 7}));

        finalizar();
    }
}
