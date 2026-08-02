package exercicios.strings;

import static util.Testar.*;
import static util.Testar.resultado;

/**
 * <h2>Exercício 07 — Substituir caractere</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dada uma string, um caractere alvo e um caractere substituto, retorne
 * uma nova string onde todas as ocorrências do caractere alvo são
 * substituídas pelo caractere substituto.
 * </p>
 *
 * <p>
 * A substituição deve ser <strong>case-sensitive</strong> (maiúsculas e
 * minúsculas são tratadas como caracteres diferentes). Se o caractere alvo
 * não aparecer na string, a string original deve ser retornada sem
 * alterações.
 * </p>
 *
 * <p>
 * Você deve implementar a substituição <strong>manualmente</strong>,
 * percorrendo a string caractere por caractere com {@code StringBuilder}:
 * copie cada caractere normalmente; quando o caractere atual for igual ao
 * alvo, insira o substituto no lugar. Os métodos {@code replace} e
 * {@code replaceAll} de {@code String} estão proibidos neste exercício.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * "banana", 'a', 'o'  -> "bonono"
 * "teste",  'x', 'y'  -> "teste"
 * "",       'a', 'b'  -> ""
 * "Java",   'a', 'o'  -> "Jovo"
 * "a b c",  'a', ' '  -> "  b c"
 * "aaaa",   'a', 'b'  -> "bbbb"
 * "z",      'z', 'x'  -> "x"
 * "casa",   'a', 'e'  -> "cese"
 * "Hello",  'l', 'x'  -> "Hexxo"
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use {@code String.replace()} nem {@code String.replaceAll()}.</li>
 *     <li>Não use Streams.</li>
 *     <li>Não use regex.</li>
 *     <li>Não use bibliotecas externas.</li>
 *     <li>Use {@code StringBuilder} para construir a nova string.</li>
 *     <li>A substituição deve ser <strong>case-sensitive</strong>.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n)</li>
 *     <li>Espaço: O(n) (a nova string criada)</li>
 * </ul>
 */
public class Exercicio07 {

    public static String substituirCaractere(String texto, char alvo, char substituto) {

        StringBuilder substituido = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            substituido.append((texto.charAt(i) == alvo) ? substituto : texto.charAt(i));
        }

        return substituido.toString();
    }

    public static void main(String[] args) {
        iniciar(Exercicio07.class, 4, "substituirCaractere");

        resultado("substituicao comum", "bonono", substituirCaractere("banana", 'a', 'o'));
        resultado("nenhuma substituicao", "teste", substituirCaractere("teste", 'x', 'y'));
        resultado("string vazia", "", substituirCaractere("", 'a', 'b'));
        resultado("case-sensitive", "Jovo", substituirCaractere("Java", 'a', 'o'));
        resultado("substituir por espaco", "  b c", substituirCaractere("a b c", 'a', ' '));
        resultado("multiplas ocorrencias", "bbbb", substituirCaractere("aaaa", 'a', 'b'));
        resultado("um unico caractere", "x", substituirCaractere("z", 'z', 'x'));
        resultado("inicio e fim diferentes", "cese", substituirCaractere("casa", 'a', 'e'));
        resultado("maiusculas e minusculas", "Hexxo", substituirCaractere("Hello", 'l', 'x'));

        finalizar();
    }
}
