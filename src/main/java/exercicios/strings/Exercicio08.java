package exercicios.strings;

import static util.Testar.*;

/**
 * <h2>Exercício 08 — Contar palavras</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dada uma string, retorne a quantidade de palavras que ela contém.
 * Uma <strong>palavra</strong> é definida como uma sequência contígua de
 * caracteres que não são espaço ({@code ' '}). Ou seja, espaços funcionam
 * como separadores entre palavras.
 * </p>
 *
 * <p>
 * Você deve identificar as transições de um caractere espaço para um
 * caractere não-espaço. Cada transição desse tipo indica o início de uma
 * nova palavra.
 * </p>
 *
 * <p>
 * Dica: mantenha uma variável booleana que indica se você está "dentro" de
 * uma palavra. Percorra a string caractere por caractere e atualize o
 * contador sempre que sair de um espaço para um não-espaço.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * "ola mundo java"   -> 3
 * "ola   mundo"      -> 2   (três espaços consecutivos)
 * "  ola"            -> 1   (espaços no início)
 * "ola  "            -> 1   (espaços no fim)
 * ""                 -> 0   (string vazia)
 * "   "              -> 0   (somente espaços)
 * "teste"            -> 1   (uma única palavra)
 * "hello world"      -> 2
 * "ola, mundo!"      -> 2   (pontuação não separa palavras)
 * "a b c"            -> 3   (palavras de um único caractere)
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use {@code String.split()}.</li>
 *     <li>Não use {@code StringTokenizer}.</li>
 *     <li>Não use Streams.</li>
 *     <li>Não use regex.</li>
 *     <li>Não use bibliotecas externas.</li>
 *     <li>Percorra a string manualmente com
 *         {@code length()} e {@code charAt()}.</li>
 *     <li>Apenas o espaço ({@code ' '}) é considerado separador.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n)</li>
 *     <li>Espaço: O(1)</li>
 * </ul>
 */
public class Exercicio08 {

    public static int contarPalavras(String texto) {
        // TODO: implemente sua solução
        return 0;
    }

    public static void main(String[] args) {
        iniciar(Exercicio08.class, 5, "contarPalavras");

        resultado("frase comum", 3, contarPalavras("ola mundo java"));
        resultado("multiplos espacos consecutivos", 2, contarPalavras("ola   mundo"));
        resultado("espacos no inicio", 1, contarPalavras("  ola"));
        resultado("espacos no fim", 1, contarPalavras("ola  "));
        resultado("string vazia", 0, contarPalavras(""));
        resultado("somente espacos", 0, contarPalavras("   "));
        resultado("uma unica palavra", 1, contarPalavras("teste"));
        resultado("duas palavras", 2, contarPalavras("hello world"));
        resultado("com pontuacao", 2, contarPalavras("ola, mundo!"));
        resultado("espaco unico", 0, contarPalavras(" "));
        resultado("palavras de um caractere", 3, contarPalavras("a b c"));

        finalizar();
    }
}
