package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 *     Testar.iniciar(Exercicio01.class, "somar");
 *
 *     Testar.resultado("array comum", 6, somar(new int[]{1, 2, 3}));
 *     Testar.resultado("array vazio", 0, somar(new int[]{}));
 *
 *     Testar.finalizar();
 * }
 * }</pre>
 */
public final class Testar {

    private static final String VERDE    = "\u001B[32m";
    private static final String VERMELHO = "\u001B[31m";
    private static final String CINZA    = "\u001B[90m";
    private static final String AMARELO  = "\u001B[33m";
    private static final String CIANO    = "\u001B[36m";
    private static final String RESET    = "\u001B[0m";

    private static boolean iniciado = false;
    private static boolean encerrado = false;
    private static boolean cabecalhoImpresso = false;
    private static Class<?> classe;
    private static String[] metodosRegistrados;
    private static int totalTestes = 0;
    private static int totalFalhas = 0;

    private Testar() {}

    // ---- API pública ----

    public static void iniciar(Class<?> c, String... metodos) {
        classe = c;
        metodosRegistrados = (metodos.length == 0) ? new String[0] : metodos.clone();
        iniciado = true;
    }

    // ---- int ----

    public static void resultado(String caso, int esperado, int obtido) {
        verificarIniciado();
        totalTestes++;
        boolean passou = esperado == obtido;
        if (!passou) totalFalhas++;
        linhaTeste(passou, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    // ---- long ----

    public static void resultado(String caso, long esperado, long obtido) {
        verificarIniciado();
        totalTestes++;
        boolean passou = esperado == obtido;
        if (!passou) totalFalhas++;
        linhaTeste(passou, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    // ---- boolean ----

    public static void resultado(String caso, boolean esperado, boolean obtido) {
        verificarIniciado();
        totalTestes++;
        boolean passou = esperado == obtido;
        if (!passou) totalFalhas++;
        linhaTeste(passou, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    // ---- double (com delta) ----

    public static void resultado(String caso, double esperado, double obtido, double delta) {
        verificarIniciado();
        totalTestes++;
        boolean passou = Math.abs(esperado - obtido) <= delta;
        if (!passou) totalFalhas++;
        linhaTeste(passou, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    // ---- String ----

    public static void resultado(String caso, String esperado, String obtido) {
        verificarIniciado();
        totalTestes++;
        boolean passou = esperado == null ? obtido == null : esperado.equals(obtido);
        if (!passou) totalFalhas++;
        linhaTeste(passou, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    // ---- int[] ----

    public static void resultado(String caso, int[] esperado, int[] obtido) {
        verificarIniciado();
        totalTestes++;
        boolean passou = Arrays.equals(esperado, obtido);
        if (!passou) totalFalhas++;
        linhaTeste(passou, caso, Arrays.toString(obtido), Arrays.toString(esperado));
    }

    // ---- Object genérico (fallback) ----

    public static void resultado(String caso, Object esperado, Object obtido) {
        verificarIniciado();
        totalTestes++;
        boolean passou = Objects.equals(esperado, obtido);
        if (!passou) totalFalhas++;
        linhaTeste(passou, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    // ---- finalização ----

    public static void finalizar() {
        if (encerrado) return;
        encerrado = true;
        if (!iniciado) return;

        if (!cabecalhoImpresso) imprimirCabecalho();

        String sep = CINZA
                + "  ------  ------  -------------------------  --------  --------  ------------"
                + RESET;
        System.out.println(sep);

        boolean todosPassaram = totalFalhas == 0;
        String resumoObtido = (totalTestes - totalFalhas) + "/" + totalTestes;
        String resumoEsperado = totalTestes + "/" + totalTestes;
        String resumoDetalhe = todosPassaram ? "todos passaram" : totalFalhas + " falharam";

        imprimirLinha("RESUMO", todosPassaram ? "PASS" : "FAIL", todosPassaram,
                "testes", resumoObtido, resumoEsperado, resumoDetalhe,
                todosPassaram ? VERDE : VERMELHO);

        if (metodosRegistrados.length == 0) return;

        if (!todosPassaram) {
            for (String metodo : metodosRegistrados) {
                imprimirLinha("CCN", "SKIP", false,
                        metodo, "-", "-", "testes falharam",
                        CINZA);
            }
            return;
        }

        Map<String, Integer> ccnPorMetodo = analisarLizard();

        for (String metodo : metodosRegistrados) {
            if (ccnPorMetodo == null) {
                imprimirLinha("CCN", "INDISP", false,
                        metodo, "-", "-", "instale o Lizard: py -m pip install lizard",
                        AMARELO);
                continue;
            }

            Integer ccn = ccnPorMetodo.get(metodo);
            if (ccn == null) {
                imprimirLinha("CCN", "ERRO", false,
                        metodo, "-", "-", "metodo nao encontrado",
                        VERMELHO);
                continue;
            }

            int valor = ccn;
            String classificacao;
            if (valor <= 4) classificacao = "baixa";
            else if (valor <= 7) classificacao = "moderada";
            else if (valor <= 10) classificacao = "alta";
            else classificacao = "muito alta";

            boolean ccnOk = valor <= 10;
            String statusCCN = ccnOk ? "OK" : "ALERTA";
            String corCCN = ccnOk ? VERDE : AMARELO;

            imprimirLinha("CCN", statusCCN, ccnOk,
                    metodo,
                    String.valueOf(valor),
                    "<= 10",
                    classificacao,
                    corCCN);
        }
    }

    // ---- impressão ----

    private static void verificarIniciado() {
        if (!iniciado) {
            throw new IllegalStateException("Testar.iniciar(Class<?>, String...) deve ser chamado antes de Testar.resultado(...)");
        }
    }

    private static void imprimirCabecalho() {
        if (cabecalhoImpresso) return;
        cabecalhoImpresso = true;

        System.out.println();
        System.out.printf("  %-6s  %-6s  %-25s  %8s  %8s  %s%n",
                "Tipo", "Status", "Caso / Metodo", "Obtido", "Esperado", "Detalhe");
        System.out.printf("  %-6s  %-6s  %-25s  %8s  %8s  %s%n",
                "------", "------", "-------------------------", "--------", "--------", "--------");
    }

    private static void imprimirLinha(String tipo, String status, boolean statusOk,
                                       String nome, String obtido,
                                       String esperado, String detalhe,
                                       String corStatus) {
        imprimirCabecalho();

        System.out.print("  ");
        System.out.printf("%-6s", tipo);
        System.out.print("  ");

        System.out.print(corStatus);
        System.out.printf("%-6s", status);
        System.out.print(RESET);
        System.out.print("  ");

        System.out.printf("%-25s  ", nome);

        if ("-".equals(obtido)) {
            System.out.print(CINZA);
            System.out.printf("%8s", obtido);
            System.out.print(RESET);
        } else {
            System.out.printf("%8s", obtido);
        }
        System.out.print("  ");

        System.out.print(CINZA);
        System.out.printf("%8s", esperado);
        System.out.print(RESET);
        System.out.print("  ");

        if ("-".equals(detalhe)) {
            System.out.println(CINZA + detalhe + RESET);
        } else {
            System.out.println(detalhe);
        }
    }

    private static void linhaTeste(boolean passou, String caso, String obtido, String esperado) {
        String status = passou ? "PASS" : "FAIL";
        String cor = passou ? VERDE : VERMELHO;
        String detalhe = passou ? "-" : "valores diferentes";

        imprimirLinha("TESTE", status, passou, caso, obtido, esperado, detalhe, cor);
    }

    // ---- análise ciclomática ----

    private static Map<String, Integer> analisarLizard() {
        if (classe == null) return null;

        File fonte = resolverArquivoFonte();
        if (fonte == null || !fonte.exists()) return null;

        List<String> saida = executarLizard(fonte);
        if (saida == null) return null;

        return extrairCCN(saida);
    }

    private static File resolverArquivoFonte() {
        String pacote = classe.getPackageName();
        String nome = classe.getSimpleName();
        String caminho = "src/main/java/" + pacote.replace('.', '/') + "/" + nome + ".java";
        File f = new File(caminho);
        return f.exists() ? f : null;
    }

    private static List<String> executarLizard(File fonte) {
        try {
            ProcessBuilder pb = new ProcessBuilder("lizard", fonte.getAbsolutePath());
            pb.redirectErrorStream(true);
            Process p = pb.start();

            List<String> linhas = new ArrayList<>();
            try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String linha;
                while ((linha = r.readLine()) != null) {
                    linhas.add(linha);
                }
            }
            p.waitFor();
            return linhas;
        } catch (Exception e) {
            return null;
        }
    }

    private static final Pattern PADRAO_LINHA = Pattern.compile("\\s*(\\d+)\\s+(\\d+)");

    private static Map<String, Integer> extrairCCN(List<String> saida) {
        Map<String, Integer> todos = new LinkedHashMap<>();
        boolean naTabela = false;

        for (String linha : saida) {
            if (linha.matches("\\s*NLOC\\s+CCN.*")) {
                naTabela = true;
                continue;
            }
            if (linha.matches("\\d+ file analyzed.*")) {
                naTabela = false;
                continue;
            }
            if (naTabela) {
                Matcher m = PADRAO_LINHA.matcher(linha);
                if (m.find()) {
                    String[] partes = linha.trim().split("\\s+");
                    if (partes.length >= 5) {
                        String loc = partes[partes.length - 1];
                        String nomeCompleto = loc.substring(0, loc.indexOf('@'));
                        int doisPontos = nomeCompleto.lastIndexOf("::");
                        if (doisPontos >= 0) {
                            String nomeMetodo = nomeCompleto.substring(doisPontos + 2);
                            todos.put(nomeMetodo, Integer.parseInt(partes[1]));
                        }
                    }
                }
            }
        }

        Map<String, Integer> resultado = new LinkedHashMap<>();
        for (String registrado : metodosRegistrados) {
            resultado.put(registrado, todos.get(registrado));
        }
        return resultado;
    }
}
