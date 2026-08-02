package util;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * <h2>Utilitário de testes com análise ciclomática</h2>
 *
 * <p>
 * Exibe testes, resumo e complexidade ciclomática em uma única tabela
 * colorida. Use {@code Testar.iniciar(...)} antes dos testes,
 * {@code Resultado.resultado(...)} para cada caso e
 * {@code Testar.finalizar()} depois do último.
 * </p>
 *
 * <pre>{@code
 * import static util.Resultado.resultado;
 *
 * public static void main(String[] args) {
 *     Testar.iniciar(Exercicio01.class, 3, "somar");
 *
 *     resultado("array comum", 6, somar(new int[]{1, 2, 3}));
 *     resultado("array vazio", 0, somar(new int[]{}));
 *
 *     Testar.finalizar();
 * }
 * }</pre>
 */
public final class Testar {

    private static final Tabela tabela = new Tabela();
    private static final AnalisadorCiclomatico analisadorCiclomatico = new AnalisadorCiclomatico();

    private static boolean iniciado;
    private static boolean encerrado;
    private static Class<?> classe;
    private static String[] metodosRegistrados;
    private static int totalTestes;
    private static int totalFalhas;
    private static int limiteCCN = 10;

    private Testar() {}

    static void reset() {
        iniciado = false;
        encerrado = false;
        classe = null;
        metodosRegistrados = null;
        limiteCCN = 10;
        totalTestes = 0;
        totalFalhas = 0;
        tabela.reset();
    }

    public static void iniciar(Class<?> c, int limiteCiclomatico, String... metodos) {
        classe = c;
        limiteCCN = limiteCiclomatico;
        metodosRegistrados = (metodos.length == 0) ? new String[0] : metodos.clone();
        iniciado = true;
    }

    public static void iniciar(Class<?> c, String... metodos) {
        iniciar(c, 10, metodos);
    }

    static void registrar(boolean passou, String caso, String obtido, String esperado) {
        verificarIniciado();
        totalTestes++;
        if (!passou) totalFalhas++;
        tabela.adicionar(Linha.teste(passou, caso, obtido, esperado));
    }

    // ---- delegates para compatibilidade ----

    public static void resultado(String caso, int esperado, int obtido) {
        Resultado.resultado(caso, esperado, obtido);
    }

    public static void resultado(String caso, long esperado, long obtido) {
        Resultado.resultado(caso, esperado, obtido);
    }

    public static void resultado(String caso, boolean esperado, boolean obtido) {
        Resultado.resultado(caso, esperado, obtido);
    }

    public static void resultado(String caso, double esperado, double obtido, double delta) {
        Resultado.resultado(caso, esperado, obtido, delta);
    }

    public static void resultado(String caso, String esperado, String obtido) {
        Resultado.resultado(caso, esperado, obtido);
    }

    public static void resultado(String caso, int[] esperado, int[] obtido) {
        Resultado.resultado(caso, esperado, obtido);
    }

    public static void resultado(String caso, Object esperado, Object obtido) {
        Resultado.resultado(caso, esperado, obtido);
    }

    // ---- finalização ----

    public static void finalizar() {
        if (encerrado) return;
        encerrado = true;
        if (!iniciado) return;

        tabela.calcularLarguras();
        tabela.imprimirTodas();
        tabela.imprimirSeparador();

        boolean todosPassaram = totalFalhas == 0;
        String resumoObtido = (totalTestes - totalFalhas) + "/" + totalTestes;
        String resumoEsperado = totalTestes + "/" + totalTestes;
        String resumoDetalhe = todosPassaram ? "todos passaram" : totalFalhas + " falharam";

        tabela.imprimirLinha(new Linha("RESUMO", todosPassaram ? "PASS" : "FAIL", todosPassaram,
                "testes", resumoObtido, resumoEsperado, resumoDetalhe,
                todosPassaram ? Cor.VERDE : Cor.VERMELHO));

        if (metodosRegistrados.length == 0) return;

        if (!todosPassaram) {
            for (String metodo : metodosRegistrados) {
                tabela.imprimirLinha(new Linha("CCN", "SKIP", false,
                        metodo, "-", "-", "testes falharam", Cor.CINZA));
            }
            return;
        }

        Map<String, Integer> ccnPorMetodo = analisadorCiclomatico.analisar(classe, metodosRegistrados);

        for (String metodo : metodosRegistrados) {
            if (ccnPorMetodo == null) {
                tabela.imprimirLinha(new Linha("CCN", "INDISP", false,
                        metodo, "-", "-", "instale o Lizard: py -m pip install lizard", Cor.AMARELO));
                continue;
            }

            Integer ccn = ccnPorMetodo.get(metodo);
            if (ccn == null) {
                tabela.imprimirLinha(new Linha("CCN", "ERRO", false,
                        metodo, "-", "-", "metodo nao encontrado", Cor.VERMELHO));
                continue;
            }

            int valor = ccn;
            double proporcao = (double) valor / limiteCCN;
            String classificacao;
            if (proporcao <= 0.4) classificacao = "baixa";
            else if (proporcao <= 0.7) classificacao = "moderada";
            else classificacao = "alta";

            boolean ccnOk = valor <= limiteCCN;
            String statusCCN = ccnOk ? "OK" : "ALERTA";
            Cor corCCN = ccnOk ? Cor.VERDE : Cor.AMARELO;
            String limiteStr = String.valueOf(limiteCCN);

            tabela.imprimirLinha(new Linha("CCN", statusCCN, ccnOk,
                    metodo,
                    String.valueOf(valor),
                    "<= " + limiteStr,
                    classificacao,
                    corCCN));
        }
    }

    private static void verificarIniciado() {
        if (!iniciado) {
            throw new IllegalStateException("Testar.iniciar(Class<?>, String...) deve ser chamado antes de Testar.resultado(...)");
        }
    }
}
