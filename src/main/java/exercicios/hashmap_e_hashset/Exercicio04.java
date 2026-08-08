package exercicios.hashmap_e_hashset;

import static util.Testar.*;

import java.util.HashMap;

/**
 * <h2>Exercício 04 — Primeiro caractere único em uma string</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dada uma string {@code texto}, retorne o <strong>primeiro</strong> caractere
 * que aparece exatamente uma vez, respeitando a ordem de leitura da esquerda
 * para a direita. Se nenhum caractere for único (isto é, todos aparecem pelo
 * menos duas vezes ou a string está vazia), retorne o caractere sentinela
 * {@code '\0'} (caractere nulo).
 * </p>
 *
 * <p>
 * Este exercício aplica o padrão de {@code HashMap} de frequência com duas
 * passagens: na primeira, construa o mapa de frequência de cada caractere; na
 * segunda, percorra a string novamente na ordem original e retorne o primeiro
 * caractere cuja frequência é 1.
 * </p>
 *
 * <p><strong>Dica:</strong></p>
 * <p>
 * Crie um {@code HashMap<Character, Integer>} e preencha com
 * {@code getOrDefault(c, 0) + 1}. Depois, percorra a string do índice 0 até o
 * final e, para cada caractere, verifique se {@code mapa.get(c) == 1}. Se
 * encontrar, retorne esse caractere imediatamente. Se o loop terminar sem
 * encontrar nenhum, retorne {@code '\0'}.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * "banana" -> 'b'    (b=1, a=3, n=2 — único: b)
 * "aabbcc" -> '\0'   (a=2, b=2, c=2 — nenhum único)
 * ""       -> '\0'   (string vazia)
 * "x"      -> 'x'    (único caractere, frequência 1)
 * "aAbbA"  -> 'a'    (a=1, A=2, b=2 — primeiro único é 'a' minúsculo)
 * "aab"    -> 'b'    (a=2, b=1 — primeiro único está no final)
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Use {@code HashMap} para contar a frequência de cada caractere.</li>
 *     <li>Faça duas passagens: a primeira monta o mapa, a segunda percorre a
 *     string na ordem original.</li>
 *     <li>Trate maiúsculas e minúsculas como caracteres distintos.</li>
 *     <li>Se nenhum caractere for único, retorne {@code '\0'}.</li>
 *     <li>Não use Streams.</li>
 *     <li>O parâmetro {@code texto} nunca será {@code null}.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n) — duas passagens lineares pela string, cada operação no
 *     mapa é O(1) médio</li>
 *     <li>Espaço: O(k) — onde k é a quantidade de caracteres distintos na
 *     string</li>
 * </ul>
 */
public class Exercicio04 {

    public static char primeiroCaractereUnico(String texto) {
        // TODO: implemente sua solução
        return '\0';
    }

    public static void main(String[] args) {
        iniciar(Exercicio04.class, 4, "primeiroCaractereUnico");

        resultado("string comum (unico no inicio)", 'b', primeiroCaractereUnico("banana"));
        resultado("todos repetidos (nenhum unico)", '\0', primeiroCaractereUnico("aabbcc"));
        resultado("string vazia", '\0', primeiroCaractereUnico(""));
        resultado("um unico caractere", 'x', primeiroCaractereUnico("x"));
        resultado("maiusculas e minusculas distintas", 'a', primeiroCaractereUnico("aAbbA"));
        resultado("caractere unico no final", 'b', primeiroCaractereUnico("aab"));

        finalizar();
    }
}
