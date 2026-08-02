package exercicios.strings;

/**
 * <h2>Exercício 06 — Extrair substring entre índices</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dada uma string e dois índices (início e fim), retorne a substring
 * compreendida entre esses índices. O índice de início é
 * <strong>inclusivo</strong> e o índice de fim é <strong>exclusivo</strong>,
 * seguindo a mesma convenção do método {@code substring()} da classe
 * {@code String}.
 * </p>
 *
 * <p>
 * Antes de extrair a substring, valide os índices. Se qualquer um dos
 * índices for inválido, retorne uma string vazia ({@code ""}). Um índice
 * é considerado inválido quando:
 * </p>
 * <ul>
 *     <li>É negativo.</li>
 *     <li>O índice de fim é maior que o comprimento da string.</li>
 *     <li>O índice de início é maior que o índice de fim.</li>
 * </ul>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * "banana", 2, 5    -> "nan"
 * "Java",   0, 4    -> "Java"
 * "hello",  1, 1    -> ""
 * "hello",  3, 5    -> "lo"
 * "",       0, 0    -> ""
 * "teste", -1, 3    -> ""     (início negativo)
 * "teste",  1, 10   -> ""     (fim ultrapassa o comprimento)
 * "teste",  4, 2    -> ""     (início maior que fim)
 * "openai", 0, 4    -> "open"
 * "abc",    0, 1    -> "a"
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use Streams.</li>
 *     <li>Não use regex.</li>
 *     <li>Não use bibliotecas externas.</li>
 *     <li>Valide os índices antes de chamar {@code substring()}.</li>
 *     <li>Índices inválidos devem retornar {@code ""}.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(k) onde k = fim - inicio</li>
 *     <li>Espaço: O(k) onde k = fim - inicio (a substring criada)</li>
 * </ul>
 */
public class Exercicio06 {

    public static String extrairSubstring(String texto, int indiceInicio, int indiceFim) {

        if (indiceInicio < 0 || indiceFim > texto.length() || indiceFim < indiceInicio) return "";

        return texto.substring(indiceInicio, indiceFim);
    }

    public static void main(String[] args) {
        util.Testar.iniciar(Exercicio06.class, 6, "extrairSubstring");

        util.Testar.resultado("substring normal", "nan", extrairSubstring("banana", 2, 5));
        util.Testar.resultado("string inteira", "Java", extrairSubstring("Java", 0, 4));
        util.Testar.resultado("indices iguais", "", extrairSubstring("hello", 1, 1));
        util.Testar.resultado("fim igual ao comprimento", "lo", extrairSubstring("hello", 3, 5));
        util.Testar.resultado("string vazia", "", extrairSubstring("", 0, 0));
        util.Testar.resultado("inicio negativo", "", extrairSubstring("teste", -1, 3));
        util.Testar.resultado("fim ultrapassa comprimento", "", extrairSubstring("teste", 1, 10));
        util.Testar.resultado("inicio maior que fim", "", extrairSubstring("teste", 4, 2));
        util.Testar.resultado("desde o inicio", "open", extrairSubstring("openai", 0, 4));
        util.Testar.resultado("um unico caractere", "a", extrairSubstring("abc", 0, 1));

        util.Testar.finalizar();
    }
}
