package exercicios.arrays_e_loops;

/**
 * <h2>Exercício 10 — Encontrar dois números com soma-alvo</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dado um array de números inteiros e um valor alvo ({@code alvo}),
 * encontre dois elementos cuja soma seja igual ao alvo e retorne
 * seus <strong>índices</strong> em um array de dois elementos
 * {@code [indice1, indice2]}.
 * </p>
 *
 * <p>
 * Use <strong>dois loops aninhados</strong> (força bruta): para cada
 * elemento, procure um par que complete a soma. Você pode assumir que
 * <strong>existe exatamente uma solução</strong> e que o mesmo elemento
 * não pode ser usado duas vezes (os índices devem ser diferentes).
 * </p>
 *
 * <p>
 * A ordem dos índices não importa, desde que diferentes:
 * tanto {@code [0, 1]} quanto {@code [1, 0]} são aceitos, mas
 * retorne os índices na ordem em que forem encontrados.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * [2, 7, 11, 15], 9         -> [0, 1]   (2 + 7 = 9)
 * [11, 15, 2, 7], 9         -> [2, 3]   (primeira solução, 2 + 7 = 9)
 * [3, 5], 8                 -> [0, 1]
 * [-1, -2, -3, -4, -5], -8  -> [2, 4]   (-3 + -5 = -8)
 * [0, 4, 3, 0], 0           -> [0, 3]   (0 + 0 = 0)
 * [3, 3], 6                 -> [0, 1]   (3 + 3 = 6)
 * [10, -5, 0, 7], 5         -> [0, 1]   (10 + -5 = 5)
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use Streams.</li>
 *     <li>Não use {@code HashMap} nem nenhuma estrutura além de arrays.</li>
 *     <li>Use dois loops aninhados (força bruta).</li>
 *     <li>Não altere o array original.</li>
 *     <li>O array tem no mínimo 2 elementos.</li>
 *     <li>Existe exatamente uma solução válida.</li>
 *     <li>Os índices retornados devem ser diferentes (mesmo elemento não pode ser usado duas vezes).</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n²)</li>
 *     <li>Espaço: O(1)</li>
 * </ul>
 */
public class Exercicio10 {

    public static int[] encontrarDoisSoma(int[] numeros, int alvo) {

        for (int i = 0; i < numeros.length; i++) {
            for (int j = i + 1; j < numeros.length; j++) {
                if (numeros[i] + numeros[j] == alvo) {
                    return new int[]{i, j};
                }
            }
        }

        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        util.Testar.iniciar(Exercicio10.class, 5, "encontrarDoisSoma");

        util.Testar.resultado("caso padrao (2+7=9)", new int[]{0, 1}, encontrarDoisSoma(new int[]{2, 7, 11, 15}, 9));
        util.Testar.resultado("solucao no meio (2+7=9)", new int[]{2, 3}, encontrarDoisSoma(new int[]{11, 15, 2, 7}, 9));
        util.Testar.resultado("array de dois elementos", new int[]{0, 1}, encontrarDoisSoma(new int[]{3, 5}, 8));
        util.Testar.resultado("negativos (-3 + -5 = -8)", new int[]{2, 4}, encontrarDoisSoma(new int[]{-1, -2, -3, -4, -5}, -8));
        util.Testar.resultado("com zeros (0+0=0)", new int[]{0, 3}, encontrarDoisSoma(new int[]{0, 4, 3, 0}, 0));
        util.Testar.resultado("valores duplicados (3+3=6)", new int[]{0, 1}, encontrarDoisSoma(new int[]{3, 3}, 6));
        util.Testar.resultado("mesmo valor em posicoes diferentes", new int[]{1, 3}, encontrarDoisSoma(new int[]{5, 3, 8, 3}, 6));
        util.Testar.resultado("misturado (10+ -5=5)", new int[]{0, 1}, encontrarDoisSoma(new int[]{10, -5, 0, 7}, 5));
        util.Testar.resultado("solucao no final", new int[]{3, 4}, encontrarDoisSoma(new int[]{1, 8, 12, 3, 4}, 7));
        util.Testar.resultado("valores grandes", new int[]{0, 2}, encontrarDoisSoma(new int[]{1000000, 500000, 2000000, 4000}, 3000000));
        util.Testar.resultado("todos negativos", new int[]{1, 3}, encontrarDoisSoma(new int[]{-10, -20, -5, -15}, -35));

        util.Testar.finalizar();
    }
}
