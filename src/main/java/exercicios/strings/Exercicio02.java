package exercicios.strings;

/**
 * <h2>Exercício 02 — Inverter uma string</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dada uma string, retorne uma nova string com os caracteres em ordem inversa.
 * Você deve percorrer a string original de trás para frente, usando um laço
 * {@code for} e construindo o resultado com {@code StringBuilder}.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * "hello"   -> "olleh"
 * "Java"    -> "avaJ"
 * "a"       -> "a"
 * ""        -> ""
 * "a b c"   -> "c b a"
 * "abc123"  -> "321cba"
 * "arara"   -> "arara"
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use Streams.</li>
 *     <li>Não use o método {@code reverse()} do {@code StringBuilder}.</li>
 *     <li>Não converta para array e use utilidades como {@code Collections.reverse()}.</li>
 *     <li>Percorra a string de trás para frente com um laço {@code for}.</li>
 *     <li>Use {@code StringBuilder} para acumular os caracteres.</li>
 *     <li>String vazia deve retornar string vazia.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n)</li>
 *     <li>Espaço: O(n)</li>
 * </ul>
 */
public class Exercicio02 {

    public static String inverter(String texto) {

        StringBuilder invertido = new StringBuilder();
        for (int i = texto.length() - 1; i >= 0; i--) invertido.append(texto.charAt(i));

        return invertido.toString();
    }

    public static void main(String[] args) {
        util.Testar.iniciar(Exercicio02.class, 4, "inverter");

        util.Testar.resultado("string comum", "olleh", inverter("hello"));
        util.Testar.resultado("maiusculas e minusculas", "avaJ", inverter("Java"));
        util.Testar.resultado("um caractere", "a", inverter("a"));
        util.Testar.resultado("string vazia", "", inverter(""));
        util.Testar.resultado("com espacos", "c b a", inverter("a b c"));
        util.Testar.resultado("com numeros", "321cba", inverter("abc123"));
        util.Testar.resultado("palindromo", "arara", inverter("arara"));

        util.Testar.finalizar();
    }
}
