package exercicios.strings;

import static util.Testar.*;

/**
 * <h2>Exercício 10 — Maior prefixo comum</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dadas duas strings {@code a} e {@code b}, retorne o maior prefixo comum
 * entre elas. O prefixo comum é a sequência inicial de caracteres que é
 * idêntica em ambas as strings, do início até a primeira posição em que
 * os caracteres diferem (ou até o final da string mais curta).
 * </p>
 *
 * <p>
 * Se não houver nenhum caractere em comum no início, retorne uma string
 * vazia ({@code ""}). Se uma ou ambas as strings forem vazias, o prefixo
 * comum também é vazio.
 * </p>
 *
 * <p>
 * A comparação deve ser <strong>case-sensitive</strong>: letras maiúsculas
 * e minúsculas são tratadas como caracteres diferentes.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * "abcdef"    ,  "abcxyz"       -> "abc"
 * "java"      ,  "javascript"   -> "java"
 * "abc"       ,  "def"          -> ""
 * ""          ,  "abc"          -> ""
 * ""          ,  ""             -> ""
 * "azul"      ,  "amarelo"      -> "a"
 * "java"      ,  "java"         -> "java"
 * "Java"      ,  "java"         -> ""
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Não use {@code String.startsWith()} ou métodos prontos que encontrem o prefixo.</li>
 *     <li>Não use Streams.</li>
 *     <li>Não use regex.</li>
 *     <li>Não use bibliotecas externas.</li>
 *     <li>Implemente a comparação <strong>caractere a caractere</strong> com um loop.</li>
 *     <li>A comparação deve ser <strong>case-sensitive</strong>.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(min(n, m)) — percorre no máximo até o final da string mais curta</li>
 *     <li>Espaço: O(min(n, m)) — o prefixo resultante tem no máximo o tamanho da menor string</li>
 * </ul>
 */
public class Exercicio10 {

    public static String maiorPrefixoComum(String a, String b) {

        int minLen = Math.min(a.length(), b.length());

        for (int i = 0; i < minLen; i++) {
            if (a.charAt(i) != b.charAt(i)) return a.substring(0, i);
        }

        return a.substring(0, minLen);
    }

    public static void main(String[] args) {
        iniciar(Exercicio10.class, 4, "maiorPrefixoComum");

        resultado("prefixo comum parcial", "abc", maiorPrefixoComum("abcdef", "abcxyz"));
        resultado("sem prefixo", "", maiorPrefixoComum("abc", "def"));
        resultado("strings iguais", "java", maiorPrefixoComum("java", "java"));
        resultado("primeira string vazia", "", maiorPrefixoComum("", "abc"));
        resultado("ambas vazias", "", maiorPrefixoComum("", ""));
        resultado("prefixo de um caractere", "a", maiorPrefixoComum("azul", "amarelo"));
        resultado("tamanhos diferentes", "java", maiorPrefixoComum("java", "javascript"));
        resultado("case-sensitive sem prefixo", "", maiorPrefixoComum("Java", "java"));

        finalizar();
    }
}
