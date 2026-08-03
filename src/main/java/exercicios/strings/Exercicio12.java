package exercicios.strings;

import static util.Testar.*;

/**
 * <h2>Exercício 12 — trim() manual</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dada uma string {@code texto}, retorne uma nova string com todos os espaços
 * ({@code ' '}) do <strong>início</strong> e do <strong>fim</strong> removidos.
 * Espaços que estão no meio da string (entre palavras) devem ser preservados.
 * </p>
 *
 * <p>
 * Se a string for vazia ou contiver apenas espaços, o resultado deve ser uma
 * string vazia ({@code ""}).
 * </p>
 *
 * <p>
 * Este é o comportamento do método {@code String.trim()}, mas você deve
 * implementá‑lo manualmente: encontre o índice do primeiro caractere que não
 * é espaço e o índice do último caractere que não é espaço e extraia a
 * substring entre eles.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * "  ola mundo  "     -> "ola mundo"
 * "ola"               -> "ola"
 * "   "               -> ""
 * ""                  -> ""
 * "  ola"             -> "ola"
 * "ola  "             -> "ola"
 * "a"                 -> "a"
 * " "                 -> ""
 * "  a  b  "         -> "a  b"
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use {@code String.trim()} ou {@code String.strip()}.</li>
 *     <li>Não use Streams.</li>
 *     <li>Não use regex.</li>
 *     <li>Não use bibliotecas externas.</li>
 *     <li>Remova apenas o caractere de espaço ({@code ' '}).</li>
 *     <li>Não modifique espaços no meio da string.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n) — percorre a string no máximo uma vez em cada direção</li>
 *     <li>Espaço: O(n) — a substring resultante pode ter até o tamanho da original</li>
 * </ul>
 */
public class Exercicio12 {
    public static String removerEspacosExtremos(String texto) { // trim

        if (texto.isEmpty()) return "";

        int inicio = 0;
        while (inicio < texto.length() && texto.charAt(inicio) == ' ') {
            inicio++;
        }

        if (inicio == texto.length()) return "";

        int fim = texto.length() - 1;
        while (fim >= 0 && texto.charAt(fim) == ' ') {
            fim--;
        }

        return texto.substring(inicio, fim + 1);
    }

    public static void main(String[] args) {
        iniciar(Exercicio12.class, 7, "removerEspacosExtremos");

        resultado("espacos em ambos os lados", "ola mundo", removerEspacosExtremos("  ola mundo  "));
        resultado("sem espacos", "ola", removerEspacosExtremos("ola"));
        resultado("apenas espacos", "", removerEspacosExtremos("   "));
        resultado("string vazia", "", removerEspacosExtremos(""));
        resultado("espacos so no inicio", "ola", removerEspacosExtremos("  ola"));
        resultado("espacos so no fim", "ola", removerEspacosExtremos("ola  "));
        resultado("um caractere", "a", removerEspacosExtremos("a"));
        resultado("um espaco", "", removerEspacosExtremos(" "));
        resultado("espacos internos preservados", "a  b", removerEspacosExtremos("  a  b  "));

        finalizar();
    }
}
