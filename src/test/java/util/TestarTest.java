package util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class TestarTest {

    @AfterEach
    void limpar() {
        Testar.reset();
    }

    // ---- contrato da API ----

    @Test
    void resultadoAntesDeIniciarDeveLancarExcecao() {
        // Arrange
        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> Testar.resultado("x", 1, 1));
    }

    @Test
    void finalizarSemIniciarDeveSerNoop() {
        // Arrange
        // Act & Assert
        assertDoesNotThrow(Testar::finalizar);
    }

    @Test
    void finalizarDuasVezesDeveSerIdempotente() {
        // Arrange
        Testar.iniciar(TestarTest.class, "metodo");
        executarCapturando(() -> {
            Testar.resultado("x", 1, 1);
            Testar.finalizar();
        });

        // Act & Assert
        assertDoesNotThrow(Testar::finalizar);
    }

    // ---- contagem ----

    @Test
    void semTestesDeveMostrarResumoZero() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        String saida = executarCapturando(Testar::finalizar);

        // Assert
        assertTrue(removerAnsi(saida).contains("0/0"));
    }

    @Test
    void contagemDeveSerCorreta() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        String saida = executarCapturando(() -> {
            Testar.resultado("a", 1, 1);
            Testar.resultado("b", 2, 2);
            Testar.resultado("c", 3, 3);
            Testar.finalizar();
        });

        // Assert
        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("RESUMO"));
        assertTrue(limpa.contains("3/3"));
        assertTrue(limpa.contains("PASS"));
    }

    @Test
    void comFalhasDeveMostrarResumoFail() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        String saida = executarCapturando(() -> {
            Testar.resultado("a", 1, 1);
            Testar.resultado("b", 2, 99);
            Testar.resultado("c", 3, 3);
            Testar.finalizar();
        });

        // Assert
        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("2/3"));
        assertTrue(limpa.contains("FAIL"));
    }

    // ---- todas as sobrecargas ----

    @Test
    void sobrecargaInt() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        executarCapturando(() -> {
            // Assert
            assertDoesNotThrow(() -> Testar.resultado("int", 10, 10));
        });
    }

    @Test
    void sobrecargaLong() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        executarCapturando(() -> {
            // Assert
            assertDoesNotThrow(() -> Testar.resultado("long", 100L, 100L));
        });
    }

    @Test
    void sobrecargaBoolean() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        executarCapturando(() -> {
            // Assert
            assertDoesNotThrow(() -> Testar.resultado("bool", true, true));
        });
    }

    @Test
    void sobrecargaDoubleComDelta() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        executarCapturando(() -> {
            // Assert
            assertDoesNotThrow(() -> Testar.resultado("double", 3.14, 3.1401, 0.01));
        });
    }

    @Test
    void sobrecargaString() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        executarCapturando(() -> {
            // Assert
            assertDoesNotThrow(() -> Testar.resultado("str", "abc", "abc"));
        });
    }

    @Test
    void sobrecargaStringNull() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        executarCapturando(() -> {
            // Assert
            assertDoesNotThrow(() -> Testar.resultado("str null", (String) null, (String) null));
        });
    }

    @Test
    void sobrecargaIntArray() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        executarCapturando(() -> {
            // Assert
            assertDoesNotThrow(() -> Testar.resultado("arr", new int[]{1, 2}, new int[]{1, 2}));
        });
    }

    @Test
    void sobrecargaObject() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        executarCapturando(() -> {
            // Assert
            assertDoesNotThrow(() -> Testar.resultado("obj", new Object(), new Object()));
        });
    }

    // ---- formato da tabela ----

    @Test
    void tabelaDeveConterHeader() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        String saida = executarCapturando(() -> {
            Testar.resultado("x", 1, 1);
            Testar.finalizar();
        });

        // Assert
        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("Tipo"));
        assertTrue(limpa.contains("Status"));
        assertTrue(limpa.contains("Caso / Metodo"));
        assertTrue(limpa.contains("Obtido"));
        assertTrue(limpa.contains("Esperado"));
        assertTrue(limpa.contains("Detalhe"));
    }

    @Test
    void tabelaDeveConterLinhasTeste() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        String saida = executarCapturando(() -> {
            Testar.resultado("abc", 1, 1);
            Testar.finalizar();
        });

        // Assert
        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("TESTE"));
        assertTrue(limpa.contains("abc"));
        assertTrue(limpa.contains("PASS"));
    }

    @Test
    void linhaTesteFalhaDeveMostrarEsperado() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        String saida = executarCapturando(() -> {
            Testar.resultado("falha", 5, 3);
            Testar.finalizar();
        });

        // Assert
        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("valores diferentes"));
        assertTrue(limpa.contains("5"));
    }

    @Test
    void separadorDeveAparecerAntesDoResumo() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        String saida = executarCapturando(() -> {
            Testar.resultado("x", 1, 1);
            Testar.finalizar();
        });

        // Assert
        String limpa = removerAnsi(saida);
        int idxSeparador = limpa.indexOf("------  ------");
        int idxResumo = limpa.indexOf("RESUMO");
        assertTrue(idxSeparador > 0, "separador deve existir");
        assertTrue(idxResumo > 0, "RESUMO deve existir");
        assertTrue(idxSeparador < idxResumo, "separador deve vir antes de RESUMO");
    }

    // ---- CCN ----

    @Test
    void ccnSkipQuandoTestesFalham() {
        // Arrange
        Testar.iniciar(TestarTest.class, "metodo");

        // Act
        String saida = executarCapturando(() -> {
            Testar.resultado("x", 1, 99);
            Testar.finalizar();
        });

        // Assert
        assertTrue(removerAnsi(saida).contains("SKIP"));
    }

    @Test
    void ccnNaoApareceQuandoNenhumMetodoRegistrado() {
        // Arrange
        Testar.iniciar(TestarTest.class);

        // Act
        String saida = executarCapturando(() -> {
            Testar.resultado("x", 1, 1);
            Testar.finalizar();
        });

        // Assert
        assertFalse(removerAnsi(saida).contains("CCN"));
    }

    @Test
    void ccnApareceQuandoMetodoRegistradoEArquivoNaoEncontrado() {
        // Arrange
        Testar.iniciar(TestarTest.class, "metodo");

        // Act
        String saida = executarCapturando(() -> {
            Testar.resultado("x", 1, 1);
            Testar.finalizar();
        });

        // Assert
        assertTrue(removerAnsi(saida).contains("CCN"));
    }

    @Test
    void multiplosMetodosRegistradosDevemGerarMultiplasLinhasCCN() {
        // Arrange
        Testar.iniciar(TestarTest.class, "metodoA", "metodoB");

        // Act
        String saida = executarCapturando(() -> {
            Testar.resultado("x", 1, 1);
            Testar.finalizar();
        });

        // Assert
        long count = saida.lines().filter(l -> l.contains("CCN")).count();
        assertEquals(2, count);
    }

    // ---- utilitários ----

    private String executarCapturando(Runnable acao) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true));
        try {
            acao.run();
        } finally {
            System.setOut(original);
        }
        return buffer.toString();
    }

    private String removerAnsi(String s) {
        return s.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
