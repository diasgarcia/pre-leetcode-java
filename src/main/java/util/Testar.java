package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h2>Utilitário de testes</h2>
 *
 * <p>
 * Métodos estáticos para verificar saídas de exercícios com saída tabulada
 * e colorida. A primeira chamada imprime o cabeçalho automaticamente e,
 * ao final da execução (quando o programa termina), exibe a análise de
 * complexidade ciclomática de cada método usando Lizard.
 * </p>
 *
 * <p>
 * Use {@code Testar.resultado("nome do caso", esperado, obtido)}.
 * </p>
 */
public final class Testar {

    private static final String VERDE    = "\u001B[32m";
    private static final String VERMELHO = "\u001B[31m";
    private static final String CINZA    = "\u001B[90m";
    private static final String AMARELO  = "\u001B[33m";
    private static final String CIANO    = "\u001B[36m";
    private static final String RESET    = "\u001B[0m";

    private static boolean cabecalhoImpresso = false;
    private static int totalTestes = 0;
    private static int totalFalhas = 0;
    private static String arquivoFonte = null;
    private static boolean shutdownRegistrado = false;

    private Testar() {}

    private static void registrarShutdown() {
        if (shutdownRegistrado) return;
        shutdownRegistrado = true;

        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement e : stack) {
            String nome = e.getClassName();
            if (nome.startsWith("util.") || nome.startsWith("java.") || nome.startsWith("jdk.")) {
                continue;
            }
            arquivoFonte = "src/main/java/" + nome.replace('.', '/') + ".java";
            break;
        }

        Runtime.getRuntime().addShutdownHook(new Thread(Testar::exibirAnalise));
    }

    private static void imprimirCabecalho() {
        if (cabecalhoImpresso) return;
        cabecalhoImpresso = true;
        registrarShutdown();

        System.out.println();
        System.out.printf("  %-6s  %-25s  %8s  %8s%n", "Status", "Caso", "Obtido", "Esperado");
        System.out.printf("  %-6s  %-25s  %8s  %8s%n", "------", "----", "------", "--------");
    }

    private static void linha(boolean passou, String caso, String obtido, String esperado) {
        imprimirCabecalho();

        totalTestes++;
        if (!passou) totalFalhas++;

        String cor = passou ? VERDE : VERMELHO;
        String texto = passou ? "PASS" : "FAIL";

        System.out.print("  ");
        System.out.print(cor);
        System.out.printf("%-6s", texto);
        System.out.print(RESET);
        System.out.printf("  %-25s  %8s  ", caso, obtido);

        if (passou) {
            System.out.print(CINZA + "-" + RESET);
        } else {
            System.out.print(CINZA);
            System.out.printf("%8s", esperado);
            System.out.print(RESET);
        }
        System.out.println();
    }

    // ---- int ----

    public static void resultado(String caso, int esperado, int obtido) {
        linha(esperado == obtido, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    // ---- long ----

    public static void resultado(String caso, long esperado, long obtido) {
        linha(esperado == obtido, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    // ---- boolean ----

    public static void resultado(String caso, boolean esperado, boolean obtido) {
        linha(esperado == obtido, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    // ---- double (com delta) ----

    public static void resultado(String caso, double esperado, double obtido, double delta) {
        linha(Math.abs(esperado - obtido) <= delta, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    // ---- String ----

    public static void resultado(String caso, String esperado, String obtido) {
        boolean passou = esperado == null ? obtido == null : esperado.equals(obtido);
        linha(passou, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    // ---- int[] ----

    public static void resultado(String caso, int[] esperado, int[] obtido) {
        linha(Arrays.equals(esperado, obtido), caso, Arrays.toString(obtido), Arrays.toString(esperado));
    }

    // ---- Object genérico (fallback) ----

    public static void resultado(String caso, Object esperado, Object obtido) {
        linha(Objects.equals(esperado, obtido), caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    // ---- análise ciclomática ----

    private static void exibirAnalise() {
        if (totalTestes == 0 || arquivoFonte == null) return;

        File fonte = new File(arquivoFonte);
        if (!fonte.exists()) return;

        System.out.println();
        System.out.printf("  %-20s %s%n", "Complexidade ciclomatica", CIANO + fonte.getName() + RESET);

        List<String> saidaLizard = executarLizard(fonte);
        if (saidaLizard == null) {
            return;
        }

        List<String[]> funcoes = parseLizard(saidaLizard);
        if (funcoes.isEmpty()) return;

        System.out.printf("  %n  %-30s %5s%n", "Metodo", "CCN");
        System.out.printf("  %-30s %5s%n", "------", "---");

        for (String[] f : funcoes) {
            String nome = f[0];
            int ccn = Integer.parseInt(f[1]);
            String cor = ccn <= 5 ? VERDE : ccn <= 10 ? AMARELO : VERMELHO;
            String barra = "";
            for (int i = 0; i < ccn; i++) barra += "|";
            System.out.printf("  %-30s %s%2d %s%s%n", nome, cor, ccn, CINZA + barra, RESET);
        }
    }

    private static List<String> executarLizard(File fonte) {
        try {
            ProcessBuilder pb = new ProcessBuilder("lizard", "-C", "10", fonte.getAbsolutePath());
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

    private static final Pattern PADRAO_FUNCAO = Pattern.compile("\\s*(\\d+)\\s+(\\d+)");

    private static List<String[]> parseLizard(List<String> saida) {
        List<String[]> funcoes = new ArrayList<>();
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
                Matcher m = PADRAO_FUNCAO.matcher(linha);
                if (m.find()) {
                    String[] partes = linha.trim().split("\\s+");
                    if (partes.length >= 5) {
                        String loc = partes[partes.length - 1];
                        String nome = loc.substring(0, loc.indexOf('@'));
                        funcoes.add(new String[]{nome, partes[1]});
                    }
                }
            }
        }
        return funcoes;
    }
}
