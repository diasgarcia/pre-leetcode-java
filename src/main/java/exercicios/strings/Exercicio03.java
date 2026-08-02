package exercicios.strings;

/**
 * <h2>Exercício 03 — Verificar palíndromo</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Uma string é um <strong>palíndromo</strong> quando pode ser lida da mesma
 * forma de trás para frente, ignorando diferenças entre maiúsculas e
 * minúsculas. Caracteres como espaços, números e símbolos são tratados
 * literalmente — ou seja, {@code "a b a"} é palíndromo, mas {@code "a b c"}
 * não é.
 * </p>
 *
 * <p>
 * Você pode resolver este problema de duas formas:
 * </p>
 * <ul>
 *     <li><strong>Abordagem 1:</strong> inverter a string com
 *         {@code StringBuilder} e comparar com {@code equals()};</li>
 *     <li><strong>Abordagem 2:</strong> usar dois ponteiros (um no início e
 *         outro no final) comparando os caracteres das extremidades até o
 *         centro.</li>
 * </ul>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * "arara"       -> true
 * "Radar"       -> true   (maiúsculas e minúsculas são ignoradas)
 * ""            -> true   (string vazia é considerada palíndromo)
 * "a"           -> true   (um caractere é palíndromo)
 * "Java"        -> false  (não é palíndromo)
 * "ola mundo"   -> false  (espaços são tratados literalmente)
 * "12321"       -> true   (palíndromo numérico)
 * "abc123"      -> false  (não é palíndromo)
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use Streams.</li>
 *     <li>Não use regex.</li>
 *     <li>Ignore diferenças entre maiúsculas e minúsculas.</li>
 *     <li>Trate espaços e outros caracteres literalmente.</li>
 *     <li>String vazia deve retornar {@code true}.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n)</li>
 *     <li>Espaço: O(1) (abordagem de dois ponteiros) ou O(n) (StringBuilder)</li>
 * </ul>
 */
public class Exercicio03 {

    public static boolean ehPalindromo(String texto) {
        // TODO: implemente sua solução
        return false;
    }

    public static void main(String[] args) {
        util.Testar.iniciar(Exercicio03.class, 5, "ehPalindromo");

        util.Testar.resultado("palindromo comum", true, ehPalindromo("arara"));
        util.Testar.resultado("com maiusculas", true, ehPalindromo("Radar"));
        util.Testar.resultado("string vazia", true, ehPalindromo(""));
        util.Testar.resultado("um caractere", true, ehPalindromo("a"));
        util.Testar.resultado("nao palindromo", false, ehPalindromo("Java"));
        util.Testar.resultado("com espacos (nao palindromo)", false, ehPalindromo("ola mundo"));
        util.Testar.resultado("palindromo numerico", true, ehPalindromo("12321"));
        util.Testar.resultado("nao palindromo alfanumerico", false, ehPalindromo("abc123"));

        util.Testar.finalizar();
    }
}
