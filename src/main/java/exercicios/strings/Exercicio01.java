package exercicios.strings;

/**
 * <h2>Exercício 01 — Contar vogais</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dada uma string, retorne quantas vogais ela contém.
 * As vogais são: {@code a}, {@code e}, {@code i}, {@code o} e {@code u}.
 * A busca deve ignorar maiúsculas e minúsculas (ex: tanto {@code 'A'} quanto {@code 'a'} contam).
 * Caracteres acentuados (como {@code 'á'}, {@code 'ê'}, {@code 'õ'}) e o {@code 'y'} não contam como vogais.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * "hello"        -> 2
 * "Java"         -> 2
 * "AEIOU"        -> 5
 * ""             -> 0
 * "xyz"           -> 0
 * "Pre-LeetCode"  -> 5
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use Streams.</li>
 *     <li>Não use regex.</li>
 *     <li>Use um loop para percorrer a string.</li>
 *     <li>String vazia deve retornar 0.</li>
 *     <li>Considere apenas as vogais {@code a, e, i, o, u} (maiúsculas ou minúsculas).</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n)</li>
 *     <li>Espaço: O(1)</li>
 * </ul>
 */
public class Exercicio01 {

    public static int contarVogais(String texto) {
        // TODO: implemente sua solução
        return 0;
    }

    public static void main(String[] args) {
        util.Testar.iniciar(Exercicio01.class, 9, "contarVogais");

        util.Testar.resultado("palavra comum", 2, contarVogais("hello"));
        util.Testar.resultado("maiusculas e minusculas", 2, contarVogais("Java"));
        util.Testar.resultado("todas maiusculas", 5, contarVogais("AEIOU"));
        util.Testar.resultado("string vazia", 0, contarVogais(""));
        util.Testar.resultado("sem vogais", 0, contarVogais("xyz"));
        util.Testar.resultado("com hifen", 5, contarVogais("Pre-LeetCode"));
        util.Testar.resultado("vogais com espacos", 5, contarVogais("a e i o u"));
        util.Testar.resultado("caso misto", 2, contarVogais("AbCdEf"));

        util.Testar.finalizar();
    }
}
