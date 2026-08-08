package exercicios.hashmap_e_hashset;

import static util.Testar.*;

import java.util.HashSet;

/**
 * <h2>Exercício 01 — Verificar duplicatas em um array</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dado um array de números inteiros, retorne {@code true} se o array contiver
 * algum valor duplicado (qualquer elemento que apareça duas ou mais vezes) e
 * {@code false} se todos os elementos forem distintos.
 * </p>
 *
 * <p>
 * Este exercício introduz o {@code HashSet}, uma coleção que não permite
 * duplicatas. O método {@code add()} retorna {@code true} se o elemento foi
 * adicionado (não existia) e {@code false} se o elemento já estava presente.
 * </p>
 *
 * <p><strong>Dica:</strong></p>
 * <p>
 * Crie um {@code HashSet<Integer>} vazio. Para cada número do array, tente
 * adicioná‑lo ao conjunto. Se {@code add()} retornar {@code false}, o número
 * já apareceu antes — há duplicata.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * [1, 2, 3, 1]       -> true   (1 aparece duas vezes)
 * [1, 2, 3, 4]       -> false  (todos distintos)
 * []                  -> false  (array vazio não tem duplicatas)
 * [5]                 -> false  (um único elemento)
 * [7, 7, 7]           -> true   (todos iguais)
 * [-1, -2, -1]        -> true   (negativos com duplicata)
 * [1000000, 2000000]  -> false  (números grandes, todos distintos)
 * [0, 1, 0]           -> true   (zero com duplicata)
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Use {@code HashSet} para obter tempo O(n).</li>
 *     <li>Não use dois loops aninhados (força bruta O(n²)).</li>
 *     <li>Não ordene o array.</li>
 *     <li>Não use Streams.</li>
 *     <li>Não altere o array original.</li>
 *     <li>O array pode conter números negativos e zeros.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n) — cada elemento é visitado uma vez, add() é O(1) médio</li>
 *     <li>Espaço: O(n) — no pior caso, todos os elementos são distintos</li>
 * </ul>
 */
public class Exercicio01 {

    public static boolean temDuplicatas(int[] numeros) {

        HashSet<Integer> conjunto = new HashSet<>();
        for (int numero : numeros) {
            if (!conjunto.add(numero)) return true;
        }

        return false;
    }

    public static void main(String[] args) {
        iniciar(Exercicio01.class, 5, "temDuplicatas");

        resultado("array com duplicata no meio", true, temDuplicatas(new int[]{1, 2, 3, 1}));
        resultado("array todos distintos", false, temDuplicatas(new int[]{1, 2, 3, 4}));
        resultado("array vazio", false, temDuplicatas(new int[]{}));
        resultado("um unico elemento", false, temDuplicatas(new int[]{5}));
        resultado("todos iguais", true, temDuplicatas(new int[]{7, 7, 7}));
        resultado("negativos com duplicata", true, temDuplicatas(new int[]{-1, -2, -1}));
        resultado("numeros grandes todos distintos", false, temDuplicatas(new int[]{1000000, 2000000}));
        resultado("zero com duplicata", true, temDuplicatas(new int[]{0, 1, 0}));

        finalizar();
    }
}
