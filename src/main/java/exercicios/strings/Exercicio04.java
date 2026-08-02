package exercicios.strings;

import static util.Testar.*;

/**
 * <h2>Exercício 04 — Contar ocorrências de um caractere</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dada uma string e um caractere, retorne quantas vezes o caractere aparece
 * na string. A contagem é <strong>case‑sensitive</strong>, ou seja, um
 * {@code 'a'} minúsculo não conta uma ocorrência de {@code 'A'} maiúsculo.
 * Caracteres especiais, números, espaços e pontuação são tratados como
 * qualquer outro caractere.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * "banana",    'a'  -> 3
 * "Java",      'j'  -> 0    (J maiúsculo é diferente de j minúsculo)
 * "Java",      'a'  -> 2
 * "",          'a'  -> 0    (string vazia não contém nenhum caractere)
 * "abc123abc", '1'  -> 1    (números também são contados)
 * "a b c",     ' '  -> 2    (espaços também são contados)
 * "Hello!",    'l'  -> 2    (case‑sensitive: só conta 'l' minúsculo)
 * "teste",     'z'  -> 0    (caractere ausente)
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use Streams.</li>
 *     <li>Não use regex.</li>
 *     <li>Não use o método {@code indexOf()} em loop.</li>
 *     <li>Não use bibliotecas externas.</li>
 *     <li>Use um laço {@code for} para percorrer a string com {@code charAt()}.</li>
 *     <li>A comparação deve ser case‑sensitive (trate maiúsculas e minúsculas como diferentes).</li>
 *     <li>String vazia deve retornar 0.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n)</li>
 *     <li>Espaço: O(1)</li>
 * </ul>
 */
public class Exercicio04 {

    public static int contarOcorrencias(String texto, char caractere) {

        int ocorrencias = 0;
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == caractere) {
                ocorrencias++;
            }
        }

        return ocorrencias;
    }

    public static void main(String[] args) {
        iniciar(Exercicio04.class, 5, "contarOcorrencias");

        resultado("string comum", 3, contarOcorrencias("banana", 'a'));
        resultado("case-sensitive minusculo vs maiusculo", 0, contarOcorrencias("Java", 'j'));
        resultado("case-sensitive minusculo", 2, contarOcorrencias("Java", 'a'));
        resultado("string vazia", 0, contarOcorrencias("", 'a'));
        resultado("com numeros", 1, contarOcorrencias("abc123abc", '1'));
        resultado("espacos", 2, contarOcorrencias("a b c", ' '));
        resultado("case-sensitive minusculo em texto misto", 2, contarOcorrencias("Hello!", 'l'));
        resultado("caractere ausente", 0, contarOcorrencias("teste", 'z'));

        finalizar();
    }
}
