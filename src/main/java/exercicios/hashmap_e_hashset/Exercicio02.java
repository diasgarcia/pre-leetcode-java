package exercicios.hashmap_e_hashset;

import static util.Testar.*;

import java.util.HashMap;

/**
 * <h2>Exercício 02 — Contar frequência de caracteres com HashMap</h2>
 *
 * <p><strong>Enunciado:</strong></p>
 * <p>
 * Dada uma string {@code texto}, retorne um {@code HashMap<Character, Integer>}
 * contendo a frequência (quantidade de ocorrências) de cada caractere presente
 * na string.
 * </p>
 *
 * <p>
 * Este exercício introduz {@code HashMap} para contagem de frequência. Use o
 * método {@code getOrDefault(chave, valorPadrao)} para obter a contagem atual
 * de um caractere (retornando 0 se ele ainda não apareceu) e então incremente.
 * </p>
 *
 * <p><strong>Dica:</strong></p>
 * <p>
 * Crie um {@code HashMap<Character, Integer>} vazio. Para cada caractere da
 * string, use {@code mapa.put(c, mapa.getOrDefault(c, 0) + 1)} para atualizar
 * a contagem. Ao final, retorne o mapa.
 * </p>
 *
 * <p><strong>Exemplos:</strong></p>
 * <pre>{@code
 * "banana" -> {'b'=1, 'a'=3, 'n'=2}
 * ""       -> {}
 * "aaaa"   -> {'a'=4}
 * "Aa"     -> {'A'=1, 'a'=1}
 * "a b c"  -> {'a'=1, ' '=2, 'b'=1, 'c'=1}
 * "122333" -> {'1'=1, '2'=2, '3'=3}
 * }</pre>
 *
 * <p><strong>Restrições:</strong></p>
 * <ul>
 *     <li>Use {@code HashMap} e {@code getOrDefault()} para a contagem.</li>
 *     <li>Não use array de contagem de tamanho fixo (ex: {@code int[26]}).</li>
 *     <li>Não use Streams.</li>
 *     <li>A string pode conter qualquer caractere (letras, dígitos, espaços,
 *     símbolos), incluindo maiúsculas e minúsculas — trate cada caractere
 *     distinto separadamente.</li>
 *     <li>String vazia deve retornar um mapa vazio (sem entradas).</li>
 *     <li>O parâmetro {@code texto} nunca será {@code null}.</li>
 * </ul>
 *
 * <p><strong>Complexidade esperada:</strong></p>
 * <ul>
 *     <li>Tempo: O(n) — cada caractere é visitado uma vez, put/getOrDefault
 *     são O(1) médio</li>
 *     <li>Espaço: O(k) — onde k é a quantidade de caracteres distintos no
 *     texto (no máximo o tamanho do alfabeto envolvido)</li>
 * </ul>
 */
public class Exercicio02 {

    public static HashMap<Character, Integer> contarFrequencia(String texto) {

        HashMap<Character, Integer> freq = new HashMap<>();
        for (int i = 0; i < texto.length(); i++) {

            char c = texto.charAt(i);
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        return freq;
    }

    public static void main(String[] args) {
        iniciar(Exercicio02.class, 3, "contarFrequencia");

        resultado("string comum", mapa('b', 1, 'a', 3, 'n', 2), contarFrequencia("banana"));
        resultado("string vazia", new HashMap<>(), contarFrequencia(""));
        resultado("um unico caractere", mapa('z', 1), contarFrequencia("z"));
        resultado("caracteres repetidos", mapa('a', 4), contarFrequencia("aaaa"));
        resultado("maiusculas e minusculas distintas", mapa('A', 1, 'a', 1), contarFrequencia("Aa"));
        resultado("string com espacos", mapa('a', 1, ' ', 2, 'b', 1, 'c', 1), contarFrequencia("a b c"));
        resultado("string numerica", mapa('1', 1, '2', 2, '3', 3), contarFrequencia("122333"));
        resultado("caracteres especiais", mapa('a', 2, '!', 2, '@', 1), contarFrequencia("a!@a!"));

        finalizar();
    }
}
