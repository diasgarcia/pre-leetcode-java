package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class TestarTest {

    @BeforeEach
    void preparar() {
        Testar.reset();
    }

    @AfterEach
    void limpar() {
        Testar.reset();
    }

    @Test
    void resultadoAntesDeIniciarDeveLancarExcecao() {
        // Arrange
        Executable acao = () -> Testar.resultado("caso", 1, 1);

        // Act
        IllegalStateException excecao = assertThrows(IllegalStateException.class, acao);

        // Assert
        assertTrue(excecao.getMessage().contains("Testar.iniciar"));
    }

    @Test
    void finalizarAntesDeIniciarDeveLancarExcecao() {
        // Arrange
        Executable acao = Testar::finalizar;

        // Act
        IllegalStateException excecao = assertThrows(IllegalStateException.class, acao);

        // Assert
        assertTrue(excecao.getMessage().contains("Testar.iniciar"));
    }

    @Test
    void iniciarNovaSessaoAntesDeFinalizarDeveLancarExcecao() {
        // Arrange
        Testar.iniciar(TestarTest.class);
        Executable acao = () -> Testar.iniciar(TestarTest.class);

        // Act
        IllegalStateException excecao = assertThrows(IllegalStateException.class, acao);

        // Assert
        assertTrue(excecao.getMessage().contains("Testar.finalizar"));
    }

    @Test
    void devePermitirNovaSessaoDepoisDaFinalizacao() {
        // Arrange
        Runnable duasSessoes = () -> {
            Testar.iniciar(TestarTest.class);
            Testar.resultado("primeira", 1, 1);
            Testar.finalizar();
            Testar.iniciar(TestarTest.class, 5);
            Testar.resultado("segunda", 2, 2);
            Testar.finalizar();
        };

        // Act
        String saida = executarCapturando(duasSessoes);

        // Assert
        assertEquals(2, contarOcorrencias(removerAnsi(saida), "RESUMO"));
    }

    @Test
    void finalizarDuasVezesDeveManterUmaUnicaSaida() {
        // Arrange
        Runnable acao = () -> {
            Testar.iniciar(TestarTest.class);
            Testar.resultado("caso", 1, 1);
            Testar.finalizar();
            Testar.finalizar();
        };

        // Act
        String saida = executarCapturando(acao);

        // Assert
        assertEquals(1, contarOcorrencias(removerAnsi(saida), "RESUMO"));
    }

    @Test
    void todasAsSobrecargasDevemRegistrarResultados() {
        // Arrange
        Object objeto = new Object();
        Runnable acao = () -> {
            Testar.iniciar(TestarTest.class);
            Testar.resultado("int", 1, 1);
            Testar.resultado("long", 2L, 2L);
            Testar.resultado("boolean", true, true);
            Testar.resultado("double", 3.0, 3.01, 0.1);
            Testar.resultado("string", "texto", "texto");
            Testar.resultado("int array", new int[]{1, 2}, new int[]{1, 2});
            Testar.resultado("object", objeto, objeto);
            Testar.finalizar();
        };

        // Act
        String saida = executarCapturando(acao);

        // Assert
        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("7/7"));
        assertTrue(limpa.contains("todos passaram"));
    }

    @Test
    void resultadoDiferenteDeveAparecerComoFalha() {
        // Arrange
        Runnable acao = () -> {
            Testar.iniciar(TestarTest.class);
            Testar.resultado("falha", 1, 2);
            Testar.finalizar();
        };

        // Act
        String saida = executarCapturando(acao);

        // Assert
        String limpa = removerAnsi(saida);
        assertTrue(limpa.contains("FAIL"));
        assertTrue(limpa.contains("valores diferentes"));
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

    private int contarOcorrencias(String texto, String trecho) {
        int total = 0;
        int indice = 0;
        while ((indice = texto.indexOf(trecho, indice)) >= 0) {
            total++;
            indice += trecho.length();
        }
        return total;
    }

    @Test
    void mapaComMultiplasEntradas() {
        // Arrange
        HashMap<Character, Integer> esperado = new HashMap<>();
        esperado.put('b', 1);
        esperado.put('a', 3);
        esperado.put('n', 2);

        // Act
        HashMap<Character, Integer> obtido = Testar.mapa('b', 1, 'a', 3, 'n', 2);

        // Assert
        assertEquals(esperado, obtido);
    }

    @Test
    void mapaComEntradaUnica() {
        // Arrange
        HashMap<Character, Integer> esperado = new HashMap<>();
        esperado.put('z', 1);

        // Act
        HashMap<Character, Integer> obtido = Testar.mapa('z', 1);

        // Assert
        assertEquals(esperado, obtido);
    }

    @Test
    void mapaVazio() {
        // Act
        HashMap<Character, Integer> obtido = Testar.mapa();

        // Assert
        assertTrue(obtido.isEmpty());
    }

    @Test
    void mapaComChavesRepetidasMantemUltimoValor() {
        // Arrange
        HashMap<Character, Integer> esperado = new HashMap<>();
        esperado.put('a', 2);

        // Act
        HashMap<Character, Integer> obtido = Testar.mapa('a', 1, 'a', 2);

        // Assert
        assertEquals(esperado, obtido);
    }
}
