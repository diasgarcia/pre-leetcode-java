package exercicios.strings;

import static util.Testar.*;

/**
 * <h2>Exercício 05 — Encontrar primeira ocorrência</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dada uma string e um caractere, retorne o índice da primeira ocorrência
 * desse caractere na string. Se o caractere não estiver presente, retorne
 * {@code -1}. A busca é <strong>case-sensitive</strong>, portanto letras
 * maiúsculas e minúsculas são consideradas diferentes.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * "banana", 'b' -> 0
 * "banana", 'a' -> 1
 * "banana", 'n' -> 2
 * "Java",   'j' -> -1
 * "",       'a' -> -1
 * "a b c",  ' ' -> 1
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use Streams.</li>
 *     <li>Não use regex.</li>
 *     <li>Não use os métodos {@code indexOf()} ou {@code lastIndexOf()}.</li>
 *     <li>Não use bibliotecas externas.</li>
 *     <li>Use um loop para percorrer a string com {@code charAt()}.</li>
 *     <li>A comparação deve ser case-sensitive.</li>
 *     <li>Retorne o menor índice em que o caractere aparece.</li>
 *     <li>String vazia ou caractere ausente deve retornar {@code -1}.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n)</li>
 *     <li>Espaço: O(1)</li>
 * </ul>
 */
public class Exercicio05 {

    public static int encontrarPrimeiraOcorrencia(String texto, char caractere) {

        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == caractere) return i;
        }

        return -1;
    }

    public static void main(String[] args) {
        iniciar(Exercicio05.class, 4, "encontrarPrimeiraOcorrencia");

        resultado("primeiro caractere", 0, encontrarPrimeiraOcorrencia("banana", 'b'));
        resultado("primeira entre repetidas", 1, encontrarPrimeiraOcorrencia("banana", 'a'));
        resultado("caractere no meio", 2, encontrarPrimeiraOcorrencia("banana", 'n'));
        resultado("ultimo caractere", 5, encontrarPrimeiraOcorrencia("Hello!", '!'));
        resultado("case-sensitive ausente", -1, encontrarPrimeiraOcorrencia("Java", 'j'));
        resultado("string vazia", -1, encontrarPrimeiraOcorrencia("", 'a'));
        resultado("um caractere encontrado", 0, encontrarPrimeiraOcorrencia("a", 'a'));
        resultado("caractere ausente", -1, encontrarPrimeiraOcorrencia("teste", 'z'));
        resultado("espaco", 1, encontrarPrimeiraOcorrencia("a b c", ' '));

        finalizar();
    }
}
