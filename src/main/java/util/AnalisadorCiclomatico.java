package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class AnalisadorCiclomatico implements AnalisadorDeComplexidade {

    private static final Pattern PADRAO_LINHA = Pattern.compile("\\s*(\\d+)\\s+(\\d+)");
    private static final long TEMPO_LIMITE_PADRAO_MILISSEGUNDOS = 10_000;

    private final List<String> comandoLizard;
    private final long tempoLimiteMilissegundos;

    AnalisadorCiclomatico() {
        this(List.of("lizard"), TEMPO_LIMITE_PADRAO_MILISSEGUNDOS);
    }

    AnalisadorCiclomatico(List<String> comandoLizard, long tempoLimiteMilissegundos) {
        if (comandoLizard == null || comandoLizard.isEmpty()) {
            throw new IllegalArgumentException("comando do Lizard não pode estar vazio");
        }
        if (tempoLimiteMilissegundos <= 0) {
            throw new IllegalArgumentException("tempo limite deve ser maior que zero");
        }

        this.comandoLizard = List.copyOf(comandoLizard);
        this.tempoLimiteMilissegundos = tempoLimiteMilissegundos;
    }

    @Override
    public Analise analisar(Class<?> classe, List<String> metodosRegistrados) {
        Path fonte = resolverArquivoFonte(classe);
        if (!Files.isRegularFile(fonte)) {
            return Analise.indisponivel("arquivo fonte não encontrado");
        }

        Execucao execucao = executarLizard(fonte);
        if (!execucao.concluida()) {
            return Analise.indisponivel(execucao.detalhe());
        }

        return Analise.sucesso(extrairCCN(execucao.linhas(), metodosRegistrados));
    }

    private Path resolverArquivoFonte(Class<?> classe) {
        String pacote = classe.getPackageName();
        String nome = classe.getSimpleName();
        return Path.of("src", "main", "java")
                .resolve(pacote.replace('.', java.io.File.separatorChar))
                .resolve(nome + ".java");
    }

    private Execucao executarLizard(Path fonte) {
        List<String> comando = new ArrayList<>(comandoLizard);
        comando.add(fonte.toAbsolutePath().toString());

        try {
            Process processo = new ProcessBuilder(comando)
                    .redirectErrorStream(true)
                    .start();
            return aguardarResultado(processo);
        } catch (IOException e) {
            return Execucao.falha("não foi possível executar o Lizard");
        }
    }

    private Execucao aguardarResultado(Process processo) throws IOException {
        try {
            if (!processo.waitFor(tempoLimiteMilissegundos, TimeUnit.MILLISECONDS)) {
                processo.destroyForcibly();
                return Execucao.falha(
                        "análise do Lizard excedeu " + tempoLimiteMilissegundos + " milissegundos");
            }

            List<String> linhas = new ArrayList<>();
            try (BufferedReader leitor = new BufferedReader(new InputStreamReader(
                    processo.getInputStream(), StandardCharsets.UTF_8))) {
                String linha;
                while ((linha = leitor.readLine()) != null) {
                    linhas.add(linha);
                }
            }

            if (processo.exitValue() != 0) {
                return Execucao.falha("Lizard encerrou com código " + processo.exitValue());
            }

            return Execucao.sucesso(linhas);
        } catch (InterruptedException e) {
            processo.destroyForcibly();
            Thread.currentThread().interrupt();
            return Execucao.falha("análise interrompida");
        }
    }

    private Map<String, Integer> extrairCCN(List<String> saida, List<String> metodosRegistrados) {
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
                extrairMetodo(linha, todos);
            }
        }

        Map<String, Integer> resultado = new LinkedHashMap<>();
        for (String registrado : metodosRegistrados) {
            if (todos.containsKey(registrado)) {
                resultado.put(registrado, todos.get(registrado));
            }
        }
        return resultado;
    }

    private void extrairMetodo(String linha, Map<String, Integer> metodos) {
        Matcher matcher = PADRAO_LINHA.matcher(linha);
        if (!matcher.find()) return;

        String[] partes = linha.trim().split("\\s+");
        if (partes.length < 5) return;

        String localizacao = partes[partes.length - 1];
        int arroba = localizacao.indexOf('@');
        if (arroba <= 0) return;

        String nomeCompleto = localizacao.substring(0, arroba);
        int doisPontos = nomeCompleto.lastIndexOf("::");
        if (doisPontos < 0) return;

        String nomeMetodo = nomeCompleto.substring(doisPontos + 2);
        metodos.put(nomeMetodo, Integer.parseInt(matcher.group(2)));
    }

    record Analise(Map<String, Integer> complexidades, String detalheFalha) {

        static Analise sucesso(Map<String, Integer> complexidades) {
            return new Analise(Map.copyOf(complexidades), null);
        }

        static Analise indisponivel(String detalhe) {
            return new Analise(Map.of(), detalhe);
        }

        boolean disponivel() {
            return detalheFalha == null;
        }

        Integer complexidadeDe(String metodo) {
            return complexidades.get(metodo);
        }
    }

    private record Execucao(boolean concluida, List<String> linhas, String detalhe) {

        static Execucao sucesso(List<String> linhas) {
            return new Execucao(true, List.copyOf(linhas), null);
        }

        static Execucao falha(String detalhe) {
            return new Execucao(false, List.of(), detalhe);
        }
    }
}
