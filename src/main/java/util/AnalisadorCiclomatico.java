package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class AnalisadorCiclomatico {

    private static final Pattern PADRAO_LINHA = Pattern.compile("\\s*(\\d+)\\s+(\\d+)");

    Map<String, Integer> analisar(Class<?> classe, String[] metodosRegistrados) {
        if (classe == null) return null;

        File fonte = resolverArquivoFonte(classe);
        if (fonte == null || !fonte.exists()) return null;

        List<String> saida = executarLizard(fonte);
        if (saida == null) return null;

        return extrairCCN(saida, metodosRegistrados);
    }

    private File resolverArquivoFonte(Class<?> classe) {
        String pacote = classe.getPackageName();
        String nome = classe.getSimpleName();
        String caminho = "src/main/java/" + pacote.replace('.', '/') + "/" + nome + ".java";
        File f = new File(caminho);
        return f.exists() ? f : null;
    }

    private List<String> executarLizard(File fonte) {
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

    private Map<String, Integer> extrairCCN(List<String> saida, String[] metodosRegistrados) {
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
