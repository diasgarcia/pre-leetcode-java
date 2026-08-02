package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class AnalisadorCiclomaticoTest {

    @Test
    void construtorPadraoDeveCriarAnalisador() {
        // Arrange
        AnalisadorCiclomatico analisador;

        // Act
        analisador = new AnalisadorCiclomatico();

        // Assert
        assertTrue(analisador instanceof AnalisadorDeComplexidade);
    }

    @Test
    void construtorDeveRejeitarComandoNulo() {
        // Arrange
        Executable acao = () -> new AnalisadorCiclomatico(null, 100);

        // Act
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, acao);

        // Assert
        assertTrue(excecao.getMessage().contains("comando"));
    }

    @Test
    void construtorDeveRejeitarComandoVazio() {
        // Arrange
        Executable acao = () -> new AnalisadorCiclomatico(List.of(), 100);

        // Act
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, acao);

        // Assert
        assertTrue(excecao.getMessage().contains("comando"));
    }

    @Test
    void construtorDeveRejeitarTempoNaoPositivo() {
        // Arrange
        Executable acao = () -> new AnalisadorCiclomatico(List.of("lizard"), 0);

        // Act
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, acao);

        // Assert
        assertTrue(excecao.getMessage().contains("tempo limite"));
    }

    @Test
    void analisarDeveInformarQuandoFonteNaoExiste() {
        // Arrange
        AnalisadorCiclomatico analisador = novoAnalisador("sucesso", 1_000);

        // Act
        AnalisadorCiclomatico.Analise analise = analisador.analisar(
                AnalisadorCiclomaticoTest.class, List.of("metodo"));

        // Assert
        assertFalse(analise.disponivel());
        assertEquals("arquivo fonte não encontrado", analise.detalheFalha());
        assertNull(analise.complexidadeDe("metodo"));
    }

    @Test
    void analisarDeveExecutarLizardEExtrairSomenteMetodosRegistrados() {
        // Arrange
        AnalisadorCiclomatico analisador = novoAnalisador("sucesso", 2_000);
        List<String> metodos = List.of("metodoValido", "ausente");

        // Act
        AnalisadorCiclomatico.Analise analise = analisador.analisar(Testar.class, metodos);

        // Assert
        assertTrue(analise.disponivel());
        assertNull(analise.detalheFalha());
        assertEquals(2, analise.complexidadeDe("metodoValido"));
        assertNull(analise.complexidadeDe("ausente"));
        assertNull(analise.complexidadeDe("foraDaTabela"));
    }

    @Test
    void analisarDeveInformarCodigoDeSaidaDoLizard() {
        // Arrange
        AnalisadorCiclomatico analisador = novoAnalisador("falha", 2_000);

        // Act
        AnalisadorCiclomatico.Analise analise =
                analisador.analisar(Testar.class, List.of("metodo"));

        // Assert
        assertFalse(analise.disponivel());
        assertEquals("Lizard encerrou com código 7", analise.detalheFalha());
    }

    @Test
    void analisarDeveInterromperProcessoAoExcederTempoLimite() {
        // Arrange
        AnalisadorCiclomatico analisador = novoAnalisador("demora", 50);

        // Act
        AnalisadorCiclomatico.Analise analise =
                analisador.analisar(Testar.class, List.of("metodo"));

        // Assert
        assertFalse(analise.disponivel());
        assertEquals("análise do Lizard excedeu 50 milissegundos", analise.detalheFalha());
    }

    @Test
    void analisarDeveInformarQuandoComandoNaoPodeSerExecutado() {
        // Arrange
        AnalisadorCiclomatico analisador = new AnalisadorCiclomatico(
                List.of("comando-que-nao-existe-para-o-teste"), 100);

        // Act
        AnalisadorCiclomatico.Analise analise =
                analisador.analisar(Testar.class, List.of("metodo"));

        // Assert
        assertFalse(analise.disponivel());
        assertEquals("não foi possível executar o Lizard", analise.detalheFalha());
    }

    @Test
    void analisarDevePreservarInterrupcaoDaThread() {
        // Arrange
        AnalisadorCiclomatico analisador = novoAnalisador("demora", 2_000);
        Thread.currentThread().interrupt();

        try {
            // Act
            AnalisadorCiclomatico.Analise analise =
                    analisador.analisar(Testar.class, List.of("metodo"));

            // Assert
            assertFalse(analise.disponivel());
            assertEquals("análise interrompida", analise.detalheFalha());
            assertTrue(Thread.currentThread().isInterrupted());
        } finally {
            Thread.interrupted();
        }
    }

    private AnalisadorCiclomatico novoAnalisador(String modo, long tempoLimite) {
        return new AnalisadorCiclomatico(comandoProcessoFake(modo), tempoLimite);
    }

    private List<String> comandoProcessoFake(String modo) {
        String executavelJava = Path.of(
                System.getProperty("java.home"),
                "bin",
                sistemaWindows() ? "java.exe" : "java"
        ).toString();

        return List.of(
                executavelJava,
                "-cp",
                diretorioDasClassesDeTeste(),
                ProcessoLizardFake.class.getName(),
                modo
        );
    }

    private String diretorioDasClassesDeTeste() {
        try {
            return Path.of(ProcessoLizardFake.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI()).toString();
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    private boolean sistemaWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
