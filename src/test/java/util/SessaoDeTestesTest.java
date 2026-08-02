package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class SessaoDeTestesTest {

    @Test
    void construtorDeveRejeitarClasseNula() {
        // Arrange
        Executable acao = () -> new SessaoDeTestes(
                null, 5, new String[0], novaTabela(), analisadorIndisponivel());

        // Act
        NullPointerException excecao = assertThrows(NullPointerException.class, acao);

        // Assert
        assertEquals("classe não pode ser nula", excecao.getMessage());
    }

    @Test
    void construtorDeveRejeitarLimiteNaoPositivo() {
        // Arrange
        Executable acao = () -> new SessaoDeTestes(
                SessaoDeTestesTest.class, -1, new String[0],
                novaTabela(), analisadorIndisponivel());

        // Act
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, acao);

        // Assert
        assertTrue(excecao.getMessage().contains("maior que zero"));
    }

    @Test
    void construtorDeveRejeitarArrayDeMetodosNulo() {
        // Arrange
        Executable acao = () -> new SessaoDeTestes(
                SessaoDeTestesTest.class, 5, null, novaTabela(), analisadorIndisponivel());

        // Act
        NullPointerException excecao = assertThrows(NullPointerException.class, acao);

        // Assert
        assertEquals("métodos não podem ser nulos", excecao.getMessage());
    }

    @Test
    void construtorDeveRejeitarNomeDeMetodoNulo() {
        // Arrange
        Executable acao = () -> new SessaoDeTestes(
                SessaoDeTestesTest.class, 5, new String[]{null},
                novaTabela(), analisadorIndisponivel());

        // Act
        NullPointerException excecao = assertThrows(NullPointerException.class, acao);

        // Assert
        assertEquals("nome de método não pode ser nulo", excecao.getMessage());
    }

    @Test
    void construtorDeveRejeitarNomeDeMetodoVazio() {
        // Arrange
        Executable acao = () -> new SessaoDeTestes(
                SessaoDeTestesTest.class, 5, new String[]{"  "},
                novaTabela(), analisadorIndisponivel());

        // Act
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, acao);

        // Assert
        assertEquals("nome de método não pode estar vazio", excecao.getMessage());
    }

    @Test
    void construtorDeveRejeitarMetodoDuplicado() {
        // Arrange
        Executable acao = () -> new SessaoDeTestes(
                SessaoDeTestesTest.class, 5, new String[]{"metodo", "metodo"},
                novaTabela(), analisadorIndisponivel());

        // Act
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, acao);

        // Assert
        assertEquals("método duplicado: metodo", excecao.getMessage());
    }

    @Test
    void construtorDeveRejeitarTabelaNula() {
        // Arrange
        Executable acao = () -> new SessaoDeTestes(
                SessaoDeTestesTest.class, 5, new String[0], null, analisadorIndisponivel());

        // Act
        NullPointerException excecao = assertThrows(NullPointerException.class, acao);

        // Assert
        assertEquals("tabela não pode ser nula", excecao.getMessage());
    }

    @Test
    void construtorDeveRejeitarAnalisadorNulo() {
        // Arrange
        Executable acao = () -> new SessaoDeTestes(
                SessaoDeTestesTest.class, 5, new String[0], novaTabela(), null);

        // Act
        NullPointerException excecao = assertThrows(NullPointerException.class, acao);

        // Assert
        assertEquals("analisador ciclomático não pode ser nulo", excecao.getMessage());
    }

    @Test
    void registrarDeveRejeitarCasoNulo() {
        // Arrange
        Contexto contexto = novaSessao(5, new String[0], analisadorIndisponivel());
        Executable acao = () -> contexto.sessao().registrar(null, Comparacao.entre(1, 1));

        // Act
        NullPointerException excecao = assertThrows(NullPointerException.class, acao);

        // Assert
        assertEquals("caso não pode ser nulo", excecao.getMessage());
    }

    @Test
    void registrarDeveRejeitarCasoVazio() {
        // Arrange
        Contexto contexto = novaSessao(5, new String[0], analisadorIndisponivel());
        Executable acao = () -> contexto.sessao().registrar(" ", Comparacao.entre(1, 1));

        // Act
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, acao);

        // Assert
        assertEquals("caso não pode estar vazio", excecao.getMessage());
    }

    @Test
    void registrarDeveRejeitarComparacaoNula() {
        // Arrange
        Contexto contexto = novaSessao(5, new String[0], analisadorIndisponivel());
        Executable acao = () -> contexto.sessao().registrar("caso", null);

        // Act
        NullPointerException excecao = assertThrows(NullPointerException.class, acao);

        // Assert
        assertEquals("comparação não pode ser nula", excecao.getMessage());
    }

    @Test
    void semTestesDeveGerarResumoReprovadoSemCcn() {
        // Arrange
        Contexto contexto = novaSessao(5, new String[0], analisadorIndisponivel());
        assertFalse(contexto.sessao().finalizada());

        // Act
        contexto.sessao().finalizar();

        // Assert
        String saida = contexto.saidaLimpa();
        assertTrue(contexto.sessao().finalizada());
        assertTrue(saida.contains("FAIL"));
        assertTrue(saida.contains("0/0"));
        assertTrue(saida.contains("nenhum teste registrado"));
        assertFalse(saida.contains("CCN"));
    }

    @Test
    void semTestesComMetodoDevePularCcn() {
        // Arrange
        Contexto contexto = novaSessao(
                5, new String[]{"metodo"}, analisadorIndisponivel());

        // Act
        contexto.sessao().finalizar();

        // Assert
        String saida = contexto.saidaLimpa();
        assertTrue(saida.contains("SKIP"));
        assertTrue(saida.contains("nenhum teste registrado"));
    }

    @Test
    void todosOsTestesAprovadosDevemGerarResumoPass() {
        // Arrange
        Contexto contexto = novaSessao(5, new String[0], analisadorIndisponivel());
        contexto.sessao().registrar("caso", Comparacao.entre(1, 1));

        // Act
        contexto.sessao().finalizar();

        // Assert
        String saida = contexto.saidaLimpa();
        assertTrue(saida.contains("PASS"));
        assertTrue(saida.contains("1/1"));
        assertTrue(saida.contains("todos passaram"));
    }

    @Test
    void umaFalhaDeveUsarResumoNoSingularEPularCcn() {
        // Arrange
        Contexto contexto = novaSessao(
                5, new String[]{"metodo"}, analisadorIndisponivel());
        contexto.sessao().registrar("falha", Comparacao.entre(1, 2));

        // Act
        contexto.sessao().finalizar();

        // Assert
        String saida = contexto.saidaLimpa();
        assertTrue(saida.contains("1 falhou"));
        assertTrue(saida.contains("SKIP"));
        assertTrue(saida.contains("testes falharam"));
    }

    @Test
    void multiplasFalhasDevemUsarResumoNoPlural() {
        // Arrange
        Contexto contexto = novaSessao(5, new String[0], analisadorIndisponivel());
        contexto.sessao().registrar("falha 1", Comparacao.entre(1, 2));
        contexto.sessao().registrar("falha 2", Comparacao.entre(1, 3));

        // Act
        contexto.sessao().finalizar();

        // Assert
        assertTrue(contexto.saidaLimpa().contains("2 falharam"));
    }

    @Test
    void finalizarDuasVezesDeveSerIdempotente() {
        // Arrange
        Contexto contexto = novaSessao(5, new String[0], analisadorIndisponivel());
        contexto.sessao().registrar("caso", Comparacao.entre(1, 1));
        contexto.sessao().finalizar();
        int tamanhoAposPrimeiraFinalizacao = contexto.buffer().size();

        // Act
        contexto.sessao().finalizar();

        // Assert
        assertEquals(tamanhoAposPrimeiraFinalizacao, contexto.buffer().size());
    }

    @Test
    void registrarDepoisDeFinalizarDeveFalhar() {
        // Arrange
        Contexto contexto = novaSessao(5, new String[0], analisadorIndisponivel());
        contexto.sessao().finalizar();
        Executable acao = () -> contexto.sessao().registrar("caso", Comparacao.entre(1, 1));

        // Act
        IllegalStateException excecao = assertThrows(IllegalStateException.class, acao);

        // Assert
        assertTrue(excecao.getMessage().contains("após Testar.finalizar"));
    }

    @Test
    void analisadorIndisponivelDeveGerarUmaLinhaPorMetodo() {
        // Arrange
        Contexto contexto = novaSessao(
                5, new String[]{"metodoA", "metodoB"}, analisadorIndisponivel());
        contexto.sessao().registrar("caso", Comparacao.entre(1, 1));

        // Act
        contexto.sessao().finalizar();

        // Assert
        String saida = contexto.saidaLimpa();
        assertEquals(2, contarOcorrencias(saida, "CCN"));
        assertEquals(2, contarOcorrencias(saida, "INDISP"));
        assertTrue(saida.contains("indisponível para teste"));
    }

    @Test
    void complexidadesDevemSerClassificadasEValidarMetodoAusente() {
        // Arrange
        Map<String, Integer> complexidades = Map.of(
                "baixa", 4,
                "moderada", 7,
                "alta", 8,
                "alerta", 11
        );
        AnalisadorDeComplexidade analisador = (classe, metodos) ->
                AnalisadorCiclomatico.Analise.sucesso(complexidades);
        Contexto contexto = novaSessao(
                10,
                new String[]{"baixa", "moderada", "alta", "alerta", "ausente"},
                analisador
        );
        contexto.sessao().registrar("caso", Comparacao.entre(1, 1));

        // Act
        contexto.sessao().finalizar();

        // Assert
        String saida = contexto.saidaLimpa();
        assertTrue(saida.contains("baixa"));
        assertTrue(saida.contains("moderada"));
        assertTrue(saida.contains("alta"));
        assertTrue(saida.contains("ALERTA"));
        assertTrue(saida.contains("ERRO"));
        assertTrue(saida.contains("método não encontrado"));
    }

    private Contexto novaSessao(int limite, String[] metodos,
                                AnalisadorDeComplexidade analisador) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Tabela tabela = new Tabela(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        SessaoDeTestes sessao = new SessaoDeTestes(
                SessaoDeTestesTest.class, limite, metodos, tabela, analisador);
        return new Contexto(sessao, buffer);
    }

    private Tabela novaTabela() {
        return new Tabela(new PrintStream(
                new ByteArrayOutputStream(), true, StandardCharsets.UTF_8));
    }

    private AnalisadorDeComplexidade analisadorIndisponivel() {
        return (classe, metodos) ->
                AnalisadorCiclomatico.Analise.indisponivel("indisponível para teste");
    }

    private int contarOcorrencias(String texto, String trecho) {
        int total = 0;
        int indice = 0;
        while ((indice = texto.indexOf(trecho, indice)) >= 0) {
            total++;
            indice += trecho.length();
        }
        return total;
    }

    private record Contexto(SessaoDeTestes sessao, ByteArrayOutputStream buffer) {

        String saidaLimpa() {
            String saida = buffer.toString(StandardCharsets.UTF_8);
            return saida.replaceAll("\u001B\\[[;\\d]*m", "");
        }
    }
}
