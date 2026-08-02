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

    // ---- sobrecargas com falha ----

    @Test
    void sobrecargaLongComFalha() {
        Testar.iniciar(TestarTest.class);

        String saida = executarCapturando(() -> {
            Testar.resultado("long fail", 100L, 200L);
            Testar.finalizar();
        });

        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("FAIL"));
        assertTrue(limpa.contains("100"));
        assertTrue(limpa.contains("200"));
        assertTrue(limpa.contains("valores diferentes"));
    }

    @Test
    void sobrecargaBooleanComFalha() {
        Testar.iniciar(TestarTest.class);

        String saida = executarCapturando(() -> {
            Testar.resultado("bool fail", true, false);
            Testar.finalizar();
        });

        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("FAIL"));
        assertTrue(limpa.contains("true"));
        assertTrue(limpa.contains("false"));
    }

    @Test
    void sobrecargaDoubleComFalha() {
        Testar.iniciar(TestarTest.class);

        String saida = executarCapturando(() -> {
            Testar.resultado("double fail", 3.14, 3.15, 0.001);
            Testar.finalizar();
        });

        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("FAIL"));
        assertTrue(limpa.contains("valores diferentes"));
    }

    @Test
    void sobrecargaDoubleNoLimiteDoDelta() {
        Testar.iniciar(TestarTest.class);

        String saida = executarCapturando(() -> {
            Testar.resultado("double edge", 3.14, 3.15, 0.01);
            Testar.finalizar();
        });

        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("PASS"));
        assertTrue(limpa.contains("1/1"));
    }

    @Test
    void sobrecargaStringComFalha() {
        Testar.iniciar(TestarTest.class);

        String saida = executarCapturando(() -> {
            Testar.resultado("str fail", "abc", "xyz");
            Testar.finalizar();
        });

        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("FAIL"));
        assertTrue(limpa.contains("abc"));
        assertTrue(limpa.contains("xyz"));
    }

    @Test
    void sobrecargaStringEsperadoNullObtidoNaoNull() {
        Testar.iniciar(TestarTest.class);

        String saida = executarCapturando(() -> {
            Testar.resultado("str null/not", (String) null, "abc");
            Testar.finalizar();
        });

        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("FAIL"));
        assertTrue(limpa.contains("null"));
        assertTrue(limpa.contains("abc"));
    }

    @Test
    void sobrecargaStringObtidoNullEsperadoNaoNull() {
        Testar.iniciar(TestarTest.class);

        String saida = executarCapturando(() -> {
            Testar.resultado("str not/null", "abc", (String) null);
            Testar.finalizar();
        });

        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("FAIL"));
        assertTrue(limpa.contains("null"));
        assertTrue(limpa.contains("abc"));
    }

    @Test
    void sobrecargaIntArrayComFalha() {
        Testar.iniciar(TestarTest.class);

        String saida = executarCapturando(() -> {
            Testar.resultado("arr fail", new int[]{1, 2}, new int[]{3, 4});
            Testar.finalizar();
        });

        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("FAIL"));
        assertTrue(limpa.contains("valores diferentes"));
    }

    @Test
    void sobrecargaIntArrayNull() {
        Testar.iniciar(TestarTest.class);

        String saida = executarCapturando(() -> {
            Testar.resultado("arr null", (int[]) null, (int[]) null);
            Testar.finalizar();
        });

        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("PASS"));
        assertTrue(limpa.contains("null"));
    }

    @Test
    void sobrecargaObjectComFalha() {
        Testar.iniciar(TestarTest.class);

        String saida = executarCapturando(() -> {
            Testar.resultado("obj fail", "abc", "xyz");
            Testar.finalizar();
        });

        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("FAIL"));
        assertTrue(limpa.contains("valores diferentes"));
    }

    @Test
    void sobrecargaObjectNull() {
        Testar.iniciar(TestarTest.class);

        String saida = executarCapturando(() -> {
            Testar.resultado("obj null", (Object) null, (Object) null);
            Testar.finalizar();
        });

        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("PASS"));
        assertTrue(limpa.contains("null"));
    }

    @Test
    void limiteCiclomaticoZeroNaoDeveDividirPorZero() {
        Testar.iniciar(TestarTest.class, 0, "metodo");

        String saida = executarCapturando(() -> {
            Testar.resultado("x", 1, 1);
            Testar.finalizar();
        });

        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("CCN"));
    }

    // ---- colunas dinâmicas ----

    @Test
    void colunasDevemExpandirComNomesLongos() {
        Testar.iniciar(TestarTest.class);

        String saida = executarCapturando(() -> {
            Testar.resultado("nome de caso bem longo com mais de 40 caracteres", 1, 1);
            Testar.finalizar();
        });

        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("PASS"));
    }

    @Test
    void colunasDevemExpandirComValoresLongos() {
        Testar.iniciar(TestarTest.class);

        String saida = executarCapturando(() -> {
            Testar.resultado("arr grande", new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                    new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
            Testar.finalizar();
        });

        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("PASS"));
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
