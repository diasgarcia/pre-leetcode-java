package util;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * <h2>Utilitário de testes com análise ciclomática</h2>
 *
 * <p>
 * Exibe testes, resumo e complexidade ciclomática em uma única tabela
 * colorida. Use {@code Testar.iniciar(...)} antes dos testes e
 * {@code Testar.finalizar()} depois do último.
 * </p>
 *
 * <pre>{@code
 * public static void main(String[] args) {
 *     Testar.iniciar(Exercicio01.class, 3, "somar");
 *
 *     Testar.resultado("array comum", 6, somar(new int[]{1, 2, 3}));
 *     Testar.resultado("array vazio", 0, somar(new int[]{}));
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

    // ---- int ----

    public static void resultado(String caso, int esperado, int obtido) {
        verificarIniciado();
        totalTestes++;
        boolean passou = esperado == obtido;
        if (!passou) totalFalhas++;
        tabela.adicionar(Linha.teste(passou, caso, String.valueOf(obtido), String.valueOf(esperado)));
    }

    // ---- long ----

    public static void resultado(String caso, long esperado, long obtido) {
        verificarIniciado();
        totalTestes++;
        boolean passou = esperado == obtido;
        if (!passou) totalFalhas++;
        tabela.adicionar(Linha.teste(passou, caso, String.valueOf(obtido), String.valueOf(esperado)));
    }

    // ---- boolean ----

    public static void resultado(String caso, boolean esperado, boolean obtido) {
        verificarIniciado();
        totalTestes++;
        boolean passou = esperado == obtido;
        if (!passou) totalFalhas++;
        tabela.adicionar(Linha.teste(passou, caso, String.valueOf(obtido), String.valueOf(esperado)));
    }

    // ---- double (com delta) ----

    public static void resultado(String caso, double esperado, double obtido, double delta) {
        verificarIniciado();
        totalTestes++;
        boolean passou = Math.abs(esperado - obtido) <= delta;
        if (!passou) totalFalhas++;
        tabela.adicionar(Linha.teste(passou, caso, String.valueOf(obtido), String.valueOf(esperado)));
    }

    // ---- String ----

    public static void resultado(String caso, String esperado, String obtido) {
        verificarIniciado();
        totalTestes++;
        boolean passou = esperado == null ? obtido == null : esperado.equals(obtido);
        if (!passou) totalFalhas++;
        tabela.adicionar(Linha.teste(passou, caso, String.valueOf(obtido), String.valueOf(esperado)));
    }

    // ---- int[] ----

    public static void resultado(String caso, int[] esperado, int[] obtido) {
        verificarIniciado();
        totalTestes++;
        boolean passou = Arrays.equals(esperado, obtido);
        if (!passou) totalFalhas++;
        tabela.adicionar(Linha.teste(passou, caso, Arrays.toString(obtido), Arrays.toString(esperado)));
    }

    // ---- Object genérico (fallback) ----

    public static void resultado(String caso, Object esperado, Object obtido) {
        verificarIniciado();
        totalTestes++;
        boolean passou = Objects.equals(esperado, obtido);
        if (!passou) totalFalhas++;
        tabela.adicionar(Linha.teste(passou, caso, String.valueOf(obtido), String.valueOf(esperado)));
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
