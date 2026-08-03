package exercicios.strings;

import static util.Testar.*;

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

        int contador = 0;
        for (int i = 0; i < texto.length(); i++) {
            char c = Character.toLowerCase(texto.charAt(i));
//            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
//            if ("aeiou".indexOf(Character.toLowerCase(texto.charAt(i))) >= 0) { // elimina a nescessidade da variavel c, mas fica mais dificil de ler
            if ("aeiou".indexOf(c) >= 0) {
                contador++;
            }
        }

        return contador;
    }

    public static void main(String[] args) {
        iniciar(Exercicio01.class, 5, "contarVogais");

        resultado("palavra comum", 2, contarVogais("hello"));
        resultado("maiusculas e minusculas", 2, contarVogais("Java"));
        resultado("todas maiusculas", 5, contarVogais("AEIOU"));
        resultado("string vazia", 0, contarVogais(""));
        resultado("sem vogais", 0, contarVogais("xyz"));
        resultado("com hifen", 5, contarVogais("Pre-LeetCode"));
        resultado("vogais com espacos", 5, contarVogais("a e i o u"));
        resultado("caso misto", 2, contarVogais("AbCdEf"));

        finalizar();
    }
}
