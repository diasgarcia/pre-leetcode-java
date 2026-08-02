package util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResultadoTest {

    @BeforeEach
    void preparar() {
        Testar.reset();
    }

    @AfterEach
    void limpar() {
        Testar.reset();
    }

    @Test
    void fachadaDeveDelegarTodasAsSobrecargasParaTestar() {
        // Arrange
        Object objeto = new Object();
        Runnable acao = () -> {
            Testar.iniciar(ResultadoTest.class);
            Resultado.resultado("int", 1, 1);
            Resultado.resultado("long", 2L, 2L);
            Resultado.resultado("boolean", true, true);
            Resultado.resultado("double", 3.0, 3.01, 0.1);
            Resultado.resultado("string", "texto", "texto");
            Resultado.resultado("int array", new int[]{1, 2}, new int[]{1, 2});
            Resultado.resultado("object", objeto, objeto);
            Testar.finalizar();
        };

        // Act
        String saida = executarCapturando(acao);

        // Assert
        assertTrue(removerAnsi(saida).contains("7/7"));
    }

    private String executarCapturando(Runnable acao) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        try {
            acao.run();
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }

    private String removerAnsi(String texto) {
        return texto.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
