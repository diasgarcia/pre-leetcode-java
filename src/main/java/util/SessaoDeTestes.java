package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

final class SessaoDeTestes {

    private final Class<?> classe;
    private final int limiteCCN;
    private final List<String> metodosRegistrados;
    private final Tabela tabela;
    private final AnalisadorDeComplexidade analisadorCiclomatico;
    private final List<Linha> testes = new ArrayList<>();

    private int totalFalhas;
    private boolean finalizada;

    SessaoDeTestes(Class<?> classe, int limiteCCN, String[] metodosRegistrados,
                   Tabela tabela, AnalisadorDeComplexidade analisadorCiclomatico) {
        this.classe = Objects.requireNonNull(classe, "classe não pode ser nula");
        if (limiteCCN <= 0) {
            throw new IllegalArgumentException("limite ciclomático deve ser maior que zero");
        }

        this.limiteCCN = limiteCCN;
        this.metodosRegistrados = validarMetodos(metodosRegistrados);
        this.tabela = Objects.requireNonNull(tabela, "tabela não pode ser nula");
        this.analisadorCiclomatico = Objects.requireNonNull(
                analisadorCiclomatico, "analisador ciclomático não pode ser nulo");
    }

    void registrar(String caso, Comparacao comparacao) {
        garantirAberta();
        Objects.requireNonNull(caso, "caso não pode ser nulo");
        if (caso.isBlank()) {
            throw new IllegalArgumentException("caso não pode estar vazio");
        }
        Objects.requireNonNull(comparacao, "comparação não pode ser nula");

        if (!comparacao.passou()) {
            totalFalhas++;
        }
        testes.add(Linha.teste(
                comparacao.passou(), caso, comparacao.obtido(), comparacao.esperado()));
    }

    void finalizar() {
        if (finalizada) return;

        List<Linha> relatorio = new ArrayList<>(testes);
        boolean possuiTestes = !testes.isEmpty();
        boolean todosPassaram = possuiTestes && totalFalhas == 0;

        relatorio.add(criarResumo(todosPassaram));
        relatorio.addAll(criarLinhasDeComplexidade(todosPassaram));
        tabela.imprimir(List.copyOf(relatorio));
        finalizada = true;
    }

    boolean finalizada() {
        return finalizada;
    }

    private Linha criarResumo(boolean todosPassaram) {
        int totalTestes = testes.size();
        String obtido = (totalTestes - totalFalhas) + "/" + totalTestes;
        String esperado = totalTestes + "/" + totalTestes;

        String detalhe;
        if (totalTestes == 0) {
            detalhe = "nenhum teste registrado";
        } else if (todosPassaram) {
            detalhe = "todos passaram";
        } else {
            detalhe = totalFalhas + (totalFalhas == 1 ? " falhou" : " falharam");
        }

        return Linha.resumo(todosPassaram, obtido, esperado, detalhe);
    }

    private List<Linha> criarLinhasDeComplexidade(boolean todosPassaram) {
        if (metodosRegistrados.isEmpty()) return List.of();

        if (!todosPassaram) {
            String detalhe = testes.isEmpty() ? "nenhum teste registrado" : "testes falharam";
            List<Linha> linhas = new ArrayList<>();
            for (String metodo : metodosRegistrados) {
                linhas.add(Linha.complexidade(
                        Linha.Status.SKIP, metodo, "-", "-", detalhe));
            }
            return List.copyOf(linhas);
        }

        AnalisadorCiclomatico.Analise analise =
                analisadorCiclomatico.analisar(classe, metodosRegistrados);

        if (!analise.disponivel()) {
            List<Linha> linhas = new ArrayList<>();
            for (String metodo : metodosRegistrados) {
                linhas.add(Linha.complexidade(
                        Linha.Status.INDISP, metodo, "-", "-", analise.detalheFalha()));
            }
            return List.copyOf(linhas);
        }

        List<Linha> linhas = new ArrayList<>();
        for (String metodo : metodosRegistrados) {
            Integer complexidade = analise.complexidadeDe(metodo);
            if (complexidade == null) {
                linhas.add(Linha.complexidade(
                        Linha.Status.ERRO, metodo, "-", "-", "método não encontrado"));
            } else {
                linhas.add(criarLinhaDeComplexidade(metodo, complexidade));
            }
        }
        return List.copyOf(linhas);
    }

    private Linha criarLinhaDeComplexidade(String metodo, int complexidade) {
        double proporcao = (double) complexidade / limiteCCN;
        String classificacao;
        if (proporcao <= 0.4) classificacao = "baixa";
        else if (proporcao <= 0.7) classificacao = "moderada";
        else classificacao = "alta";

        boolean dentroDoLimite = complexidade <= limiteCCN;
        Linha.Status status = dentroDoLimite ? Linha.Status.OK : Linha.Status.ALERTA;

        return Linha.complexidade(status, metodo,
                String.valueOf(complexidade), "<= " + limiteCCN, classificacao);
    }

    private void garantirAberta() {
        if (finalizada) {
            throw new IllegalStateException("não é possível registrar resultados após Testar.finalizar()");
        }
    }

    private static List<String> validarMetodos(String[] metodos) {
        Objects.requireNonNull(metodos, "métodos não podem ser nulos");

        List<String> validados = new ArrayList<>(metodos.length);
        Set<String> nomesUnicos = new HashSet<>();

        for (String metodo : metodos) {
            Objects.requireNonNull(metodo, "nome de método não pode ser nulo");
            if (metodo.isBlank()) {
                throw new IllegalArgumentException("nome de método não pode estar vazio");
            }
            if (!nomesUnicos.add(metodo)) {
                throw new IllegalArgumentException("método duplicado: " + metodo);
            }
            validados.add(metodo);
        }

        return List.copyOf(validados);
    }
}
