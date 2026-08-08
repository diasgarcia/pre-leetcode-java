package exercicios.hashmap_e_hashset;

import static util.Testar.*;

import java.util.HashMap;

/**
 * <h2>Exercício 03 — Dois números que somam ao alvo (two‑sum)</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dado um array de inteiros {@code numeros} e um inteiro {@code alvo}, retorne
 * um array com dois índices cujos valores somam exatamente ao alvo.
 * Se não existir nenhum par que some ao alvo, retorne {@code [-1, -1]}.
 * </p>
 *
 * <p>
 * O mesmo elemento não pode ser usado duas vezes. A ordem dos índices no array
 * retornado não importa.
 * </p>
 *
 * <p>
 * Este exercício introduz o padrão {@code two‑sum} com {@code HashMap}: em vez
 * de usar dois loops aninhados (O(n²)), percorra o array uma única vez e, para
 * cada elemento, verifique se o seu complemento ({@code alvo - elemento}) já
 * foi visto anteriormente. Se sim, você encontrou o par. Armazene no mapa o
 * valor do elemento como chave e o índice como valor.
 * </p>
 *
 * <p><strong>Dica:</strong></p>
 * <p>
 * Crie um {@code HashMap<Integer, Integer>} onde a chave é o valor do array e o
 * valor é o índice. Para cada índice {@code i}, calcule {@code complemento =
 * alvo - numeros[i]}. Se o complemento já estiver no mapa, retorne
 * {@code new int[]{mapa.get(complemento), i}}. Caso contrário, adicione
 * {@code numeros[i]} com índice {@code i} ao mapa.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * numeros = [2, 7, 11, 15], alvo = 9      -> [0, 1]  (2 + 7 = 9)
 * numeros = [3, 3], alvo = 6              -> [0, 1]  (3 + 3 = 6)
 * numeros = [-5, 2, 7, 5], alvo = 0       -> [0, 3]  (-5 + 5 = 0)
 * numeros = [1, 8, 12, 7], alvo = 15      -> [1, 3]  (8 + 7 = 15)
 * numeros = [1, 2, 3, 4], alvo = 99       -> [-1, -1]  (sem solução)
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Use {@code HashMap} para buscar o complemento em O(1) médio.</li>
 *     <li>Não use dois loops aninhados (força bruta O(n²)).</li>
 *     <li>Não ordene o array.</li>
 *     <li>Não use Streams.</li>
 *     <li>O array não está ordenado.</li>
 *     <li>O mesmo elemento não pode ser usado duas vezes.</li>
 *     <li>O array tem pelo menos dois elementos.</li>
 *     <li>Se não houver solução, retorne {@code [-1, -1]}.</li>
 *     <li>Os parâmetros nunca serão {@code null}.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n) — cada elemento é visitado no máximo uma vez, containsKey
 *     e put são O(1) médio</li>
 *     <li>Espaço: O(n) — no pior caso, o mapa armazena quase todos os elementos
 *     antes de encontrar o par</li>
 * </ul>
 */
public class Exercicio03 {

    public static int[] doisNumerosSomaAlvo(int[] numeros, int alvo) {

        HashMap<Integer, Integer> indicePorValor = new HashMap<>();
        for (int i = 0; i < numeros.length; i++) {
            int complemento = alvo - numeros[i];
            if (indicePorValor.containsKey(complemento)) {
                return new int[]{indicePorValor.get(complemento), i};
            }
            indicePorValor.put(numeros[i], i);
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        iniciar(Exercicio03.class, 4, "doisNumerosSomaAlvo");

        resultado("caso base (par no inicio)", new int[]{0, 1}, doisNumerosSomaAlvo(new int[]{2, 7, 11, 15}, 9));
        resultado("par no final do array", new int[]{1, 3}, doisNumerosSomaAlvo(new int[]{1, 8, 12, 7}, 15));
        resultado("com numeros negativos", new int[]{0, 3}, doisNumerosSomaAlvo(new int[]{-5, 2, 7, 5}, 0));
        resultado("com zeros", new int[]{0, 3}, doisNumerosSomaAlvo(new int[]{0, 4, 3, 0}, 0));
        resultado("valores duplicados", new int[]{0, 1}, doisNumerosSomaAlvo(new int[]{3, 3}, 6));
        resultado("alvo zero com complemento negativo/positivo", new int[]{0, 1}, doisNumerosSomaAlvo(new int[]{-1, 1, 5, -3}, 0));
        resultado("numeros grandes", new int[]{2, 3}, doisNumerosSomaAlvo(new int[]{10000, 20000, 50000, 80000}, 130000));
        resultado("primeiro e ultimo elemento", new int[]{0, 5}, doisNumerosSomaAlvo(new int[]{5, 8, 3, 2, 9, 4}, 9));
        resultado("par no meio do array", new int[]{1, 3}, doisNumerosSomaAlvo(new int[]{10, 4, 9, 6, 2}, 10));
        resultado("array com exatamente dois elementos", new int[]{0, 1}, doisNumerosSomaAlvo(new int[]{14, 6}, 20));
        resultado("mesmo valor em indices diferentes", new int[]{1, 3}, doisNumerosSomaAlvo(new int[]{5, 3, 8, 3, 1}, 6));
        resultado("sem solucao", new int[]{-1, -1}, doisNumerosSomaAlvo(new int[]{1, 2, 3, 4}, 99));

        finalizar();
    }
}
