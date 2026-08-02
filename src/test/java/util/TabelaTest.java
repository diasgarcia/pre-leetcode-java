package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class TabelaTest {

    @Test
    void construtorDeveRejeitarSaidaNula() {
        // Arrange
        Executable acao = () -> new Tabela(null);

        // Act
        NullPointerException excecao = assertThrows(NullPointerException.class, acao);

        // Assert
        assertEquals("saída não pode ser nula", excecao.getMessage());
    }

    @Test
    void imprimirDeveRejeitarListaNula() {
        // Arrange
        Tabela tabela = novaTabela(new ByteArrayOutputStream());
        Executable acao = () -> tabela.imprimir(null);

        // Act
        NullPointerException excecao = assertThrows(NullPointerException.class, acao);

        // Assert
        assertEquals("linhas não podem ser nulas", excecao.getMessage());
    }

    @Test
    void listaVaziaDeveImprimirSomenteCabecalho() {
        // Arrange
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Tabela tabela = novaTabela(buffer);

        // Act
        tabela.imprimir(List.of());

        // Assert
        String saida = buffer.toString(StandardCharsets.UTF_8);
        assertTrue(saida.contains("Caso / Metodo"));
        assertTrue(saida.contains("Detalhe"));
    }

    @Test
    void relatorioCompletoDeveFormatarLinhasSeparadorECamposDinamicos() {
        // Arrange
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Tabela tabela = novaTabela(buffer);
        List<Linha> linhas = List.of(
                Linha.teste(true, "caso com nome maior que vinte e cinco caracteres",
                        "valor-obtido-longo", "valor-esperado-longo"),
                new Linha(Linha.Tipo.TESTE, Linha.Status.SKIP,
                        "segundo caso", "-", "-", "-"),
                Linha.resumo(true, "2/2", "2/2", "todos passaram com detalhe longo"),
                Linha.complexidade(Linha.Status.OK, "metodo", "2", "<= 5", "baixa")
        );

        // Act
        tabela.imprimir(linhas);

        // Assert
        String saida = buffer.toString(StandardCharsets.UTF_8);
        assertTrue(saida.contains("caso com nome maior que vinte e cinco caracteres"));
        assertTrue(saida.contains("valor-obtido-longo"));
        assertTrue(saida.contains("-" + Cor.RESET));
        assertTrue(saida.contains("RESUMO"));
        assertTrue(saida.contains("CCN"));
        assertTrue(saida.contains("------  ------"));
    }

    private Tabela novaTabela(ByteArrayOutputStream buffer) {
        return new Tabela(new PrintStream(buffer, true, StandardCharsets.UTF_8));
    }
}
