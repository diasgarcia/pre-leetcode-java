package exercicios.strings;

import static util.Testar.*;

/**
 * <h2>Exercício 11 — Frequência de caracteres</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dada uma string, retorne um array de inteiros de tamanho 26 contendo a
 * frequência de cada letra do alfabeto inglês minúsculo ({@code 'a'} a
 * {@code 'z'}). O índice 0 representa {@code 'a'}, o índice 1 representa
 * {@code 'b'}, e assim por diante até o índice 25, que representa {@code 'z'}.
 * </p>
 *
 * <p>
 * A contagem deve ser <strong>case-insensitive</strong>: letras maiúsculas
 * e minúsculas são tratadas como equivalentes. Por exemplo, {@code 'A'} e
 * {@code 'a'} são contadas juntas como letra {@code 'a'} (índice 0).
 * Caracteres que não são letras do alfabeto inglês (números, espaços,
 * símbolos, acentuação) devem ser <strong>ignorados</strong>.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * "banana"                    -> [3, 1, 0, ..., 0, 2, 0, ..., 0]
 *                                (a=3, b=1, n=2)
 * "Java"                      -> [2, 0, ..., 0, 1, 0, ..., 0, 1, 0, ..., 0]
 *                                (a=2, j=1, v=1)
 * ""                          -> [0, 0, ..., 0]
 * "123!@#"                    -> [0, 0, ..., 0]
 * "aaaaa"                     -> [5, 0, ..., 0]
 * "HeLLo123!"                 -> [0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 2, 0, 0, 1, 0, ..., 0]
 *                                (e=1, h=1, l=2, o=1)
 * "abcdefghijklmnopqrstuvwxyz" -> [1, 1, 1, ..., 1]
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use {@code HashMap} ou outras coleções — o objetivo é praticar array de contagem.</li>
 *     <li>Não use Streams.</li>
 *     <li>Não use regex.</li>
 *     <li>Não use bibliotecas externas.</li>
 *     <li>A contagem deve ser case-insensitive.</li>
 *     <li>Caracteres que não são letras (a-z ou A-Z) devem ser ignorados.</li>
 *     <li>O array retornado deve ter exatamente 26 posições.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n)</li>
 *     <li>Espaço: O(1) — o array de retorno tem tamanho fixo (26)</li>
 * </ul>
 */
public class Exercicio11 {

    public static int[] frequenciaDeCaracteres(String texto) {

        int[] freq = new int[26];
        for (char c : texto.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                freq[c - 'a']++;
            }
        }

        return freq;
    }

    public static void main(String[] args) {
        iniciar(Exercicio11.class, 5, "frequenciaDeCaracteres");

        resultado("string comum", new int[]{3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, frequenciaDeCaracteres("banana"));
        resultado("com maiusculas", new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, frequenciaDeCaracteres("Java"));
        resultado("string vazia", new int[26], frequenciaDeCaracteres(""));
        resultado("so numeros e simbolos", new int[26], frequenciaDeCaracteres("123!@#"));
        resultado("uma letra repetida", new int[]{5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, frequenciaDeCaracteres("aaaaa"));
        resultado("caso misto com numeros", new int[]{0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, frequenciaDeCaracteres("HeLLo123!"));
        resultado("todas as letras uma vez", new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, frequenciaDeCaracteres("abcdefghijklmnopqrstuvwxyz"));

        finalizar();
    }
}
