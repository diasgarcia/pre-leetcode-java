package exercicios.strings;

import static util.Testar.*;

/**
 * <h2>Exercício 09 — Remover caracteres duplicados consecutivos</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dada uma string, retorne uma nova string onde caracteres duplicados
 * consecutivos (em sequência, um após o outro) são reduzidos a uma única
 * ocorrência. Ou seja, se o mesmo caractere aparecer várias vezes seguidas,
 * apenas a primeira ocorrência de cada bloco deve ser mantida.
 * </p>
 *
 * <p>
 * A comparação deve ser <strong>case-sensitive</strong>: {@code 'a'} e
 * {@code 'A'} são considerados caracteres diferentes e não são duplicatas
 * consecutivas entre si.
 * </p>
 *
 * <p>
 * Você deve implementar a remoção <strong>manualmente</strong>, percorrendo
 * a string caractere por caractere com {@code StringBuilder}: adicione
 * sempre o primeiro caractere; para cada posição seguinte, adicione o
 * caractere atual ao resultado apenas se ele for diferente do caractere
 * imediatamente anterior na string original. Métodos como
 * {@code String.replaceAll} com regex estão proibidos neste exercício.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * "aabbbcc"       -> "abc"
 * "aaaa"          -> "a"
 * "abcde"         -> "abcde"
 * ""              -> ""
 * "a"             -> "a"
 * "aahello"       -> "ahelo"
 * "helloo"        -> "helo"
 * "aa  bb"       -> "a b"
 * "ababab"        -> "ababab"
 * "abccde"        -> "abcde"
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use {@code String.replaceAll()} com regex.</li>
 *     <li>Não use {@code String.replace()} para este fim.</li>
 *     <li>Não use Streams.</li>
 *     <li>Não use regex.</li>
 *     <li>Não use bibliotecas externas.</li>
 *     <li>Use {@code StringBuilder} para construir a nova string.</li>
 *     <li>A comparação deve ser <strong>case-sensitive</strong>.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n)</li>
 *     <li>Espaço: O(n) (a nova string construída)</li>
 * </ul>
 */
public class Exercicio09 {

    public static String removerDuplicadosConsecutivos(String texto) {

        StringBuilder desduplicada = new StringBuilder();
        char anterior = '\0';

        for (int i = 0; i < texto.length(); i++) {

            char c = texto.charAt(i);
            if (c != anterior) {
                desduplicada.append(c);
                anterior = c;
            }
        }

        return desduplicada.toString();
    }

    public static void main(String[] args) {
        iniciar(Exercicio09.class, 5, "removerDuplicadosConsecutivos");

        resultado("string comum", "abc", removerDuplicadosConsecutivos("aabbbcc"));
        resultado("todos caracteres iguais", "a", removerDuplicadosConsecutivos("aaaa"));
        resultado("sem duplicados", "abcde", removerDuplicadosConsecutivos("abcde"));
        resultado("string vazia", "", removerDuplicadosConsecutivos(""));
        resultado("um unico caractere", "a", removerDuplicadosConsecutivos("a"));
        resultado("duplicados no inicio", "ahelo", removerDuplicadosConsecutivos("aahello"));
        resultado("duplicados no fim", "helo", removerDuplicadosConsecutivos("helloo"));
        resultado("com espacos consecutivos", "a b", removerDuplicadosConsecutivos("aa  bb"));
        resultado("caracteres alternados", "ababab", removerDuplicadosConsecutivos("ababab"));
        resultado("duplicados no meio", "abcde", removerDuplicadosConsecutivos("abccde"));

        finalizar();
    }
}
