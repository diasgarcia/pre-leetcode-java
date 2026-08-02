package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ComparacaoTest {

    @Test
    void construtorDeveRejeitarValorObtidoNulo() {
        // Arrange
        Executable acao = () -> new Comparacao(true, null, "esperado");

        // Act
        NullPointerException excecao = assertThrows(NullPointerException.class, acao);

        // Assert
        assertEquals("valor obtido não pode ser nulo", excecao.getMessage());
    }

    @Test
    void construtorDeveRejeitarValorEsperadoNulo() {
        // Arrange
        Executable acao = () -> new Comparacao(true, "obtido", null);

        // Act
        NullPointerException excecao = assertThrows(NullPointerException.class, acao);

        // Assert
        assertEquals("valor esperado não pode ser nulo", excecao.getMessage());
    }

    @ParameterizedTest
    @MethodSource("valoresFormatados")
    void deveCompararEFormatarValoresIguais(Object valor, String textoEsperado) {
        // Arrange
        Object esperado = valor;
        Object obtido = valor;

        // Act
        Comparacao comparacao = Comparacao.entre(esperado, obtido);

        // Assert
        assertTrue(comparacao.passou());
        assertEquals(textoEsperado, comparacao.esperado());
        assertEquals(textoEsperado, comparacao.obtido());
    }

    @Test
    void deveIdentificarObjetosDiferentes() {
        // Arrange
        Object esperado = "esperado";
        Object obtido = "obtido";

        // Act
        Comparacao comparacao = Comparacao.entre(esperado, obtido);

        // Assert
        assertFalse(comparacao.passou());
        assertEquals("esperado", comparacao.esperado());
        assertEquals("obtido", comparacao.obtido());
    }

    @Test
    void doubleExatamenteIgualDevePassar() {
        // Arrange
        double esperado = Double.NaN;
        double obtido = Double.NaN;

        // Act
        Comparacao comparacao = Comparacao.entre(esperado, obtido, 0.0);

        // Assert
        assertTrue(comparacao.passou());
    }

    @Test
    void doubleDentroDaToleranciaDevePassar() {
        // Arrange
        double esperado = 10.0;
        double obtido = 10.05;

        // Act
        Comparacao comparacao = Comparacao.entre(esperado, obtido, 0.1);

        // Assert
        assertTrue(comparacao.passou());
        assertEquals("10.0", comparacao.esperado());
        assertEquals("10.05", comparacao.obtido());
    }

    @Test
    void doubleForaDaToleranciaDeveFalhar() {
        // Arrange
        double esperado = 10.0;
        double obtido = 10.2;

        // Act
        Comparacao comparacao = Comparacao.entre(esperado, obtido, 0.1);

        // Assert
        assertFalse(comparacao.passou());
    }

    @Test
    void deltaNegativoDeveSerRejeitado() {
        // Arrange
        Executable acao = () -> Comparacao.entre(1.0, 1.0, -0.1);

        // Act
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, acao);

        // Assert
        assertTrue(excecao.getMessage().contains("maior ou igual a zero"));
    }

    @ParameterizedTest
    @MethodSource("deltasNaoFinitos")
    void deltaNaoFinitoDeveSerRejeitado(double delta) {
        // Arrange
        Executable acao = () -> Comparacao.entre(1.0, 1.0, delta);

        // Act
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, acao);

        // Assert
        assertTrue(excecao.getMessage().contains("número finito"));
    }

    private static Stream<Arguments> valoresFormatados() {
        return Stream.of(
                Arguments.of(null, "null"),
                Arguments.of(new Object[]{"a", new int[]{1, 2}}, "[a, [1, 2]]"),
                Arguments.of(new int[]{1, 2}, "[1, 2]"),
                Arguments.of(new long[]{1L, 2L}, "[1, 2]"),
                Arguments.of(new short[]{1, 2}, "[1, 2]"),
                Arguments.of(new byte[]{1, 2}, "[1, 2]"),
                Arguments.of(new char[]{'a', 'b'}, "[a, b]"),
                Arguments.of(new boolean[]{true, false}, "[true, false]"),
                Arguments.of(new float[]{1.0F, 2.0F}, "[1.0, 2.0]"),
                Arguments.of(new double[]{1.0, 2.0}, "[1.0, 2.0]"),
                Arguments.of("texto", "texto")
        );
    }

    private static Stream<Double> deltasNaoFinitos() {
        return Stream.of(Double.NaN, Double.POSITIVE_INFINITY);
    }
}
