package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LinhaTest {

    @ParameterizedTest
    @MethodSource("linhasInvalidas")
    void construtorDeveRejeitarCamposNulos(Executable acao, String mensagem) {
        // Arrange
        Executable construcaoInvalida = acao;

        // Act
        NullPointerException excecao = assertThrows(NullPointerException.class, construcaoInvalida);

        // Assert
        assertEquals(mensagem, excecao.getMessage());
    }

    @Test
    void fabricaTesteDeveCriarLinhaAprovada() {
        // Arrange
        boolean passou = true;

        // Act
        Linha linha = Linha.teste(passou, "caso", "1", "1");

        // Assert
        assertEquals(Linha.Tipo.TESTE, linha.tipo());
        assertEquals(Linha.Status.PASS, linha.status());
        assertEquals("-", linha.detalhe());
    }

    @Test
    void fabricaTesteDeveCriarLinhaReprovada() {
        // Arrange
        boolean passou = false;

        // Act
        Linha linha = Linha.teste(passou, "caso", "1", "2");

        // Assert
        assertEquals(Linha.Status.FAIL, linha.status());
        assertEquals("valores diferentes", linha.detalhe());
    }

    @Test
    void fabricaResumoDeveCriarResumoAprovado() {
        // Arrange
        boolean todosPassaram = true;

        // Act
        Linha linha = Linha.resumo(todosPassaram, "2/2", "2/2", "todos passaram");

        // Assert
        assertEquals(Linha.Tipo.RESUMO, linha.tipo());
        assertEquals(Linha.Status.PASS, linha.status());
        assertEquals("testes", linha.nome());
    }

    @Test
    void fabricaResumoDeveCriarResumoReprovado() {
        // Arrange
        boolean todosPassaram = false;

        // Act
        Linha linha = Linha.resumo(todosPassaram, "1/2", "2/2", "1 falhou");

        // Assert
        assertEquals(Linha.Status.FAIL, linha.status());
    }

    @Test
    void fabricaComplexidadeDeveCriarLinhaCcn() {
        // Arrange
        Linha.Status status = Linha.Status.OK;

        // Act
        Linha linha = Linha.complexidade(status, "metodo", "2", "<= 5", "baixa");

        // Assert
        assertEquals(Linha.Tipo.CCN, linha.tipo());
        assertEquals(status, linha.status());
        assertEquals("metodo", linha.nome());
    }

    @ParameterizedTest
    @MethodSource("statusDisponiveis")
    void todoStatusDeveColorirSeuTexto(Linha.Status status) {
        // Arrange
        String texto = status.name();

        // Act
        String colorido = status.colorir(texto);

        // Assert
        assertTrue(colorido.contains(texto));
        assertTrue(colorido.endsWith(Cor.RESET.toString()));
    }

    private static Stream<Arguments> linhasInvalidas() {
        return Stream.of(
                Arguments.of((Executable) () -> new Linha(
                        null, Linha.Status.PASS, "n", "o", "e", "d"),
                        "tipo não pode ser nulo"),
                Arguments.of((Executable) () -> new Linha(
                        Linha.Tipo.TESTE, null, "n", "o", "e", "d"),
                        "status não pode ser nulo"),
                Arguments.of((Executable) () -> new Linha(
                        Linha.Tipo.TESTE, Linha.Status.PASS, null, "o", "e", "d"),
                        "nome não pode ser nulo"),
                Arguments.of((Executable) () -> new Linha(
                        Linha.Tipo.TESTE, Linha.Status.PASS, "n", null, "e", "d"),
                        "obtido não pode ser nulo"),
                Arguments.of((Executable) () -> new Linha(
                        Linha.Tipo.TESTE, Linha.Status.PASS, "n", "o", null, "d"),
                        "esperado não pode ser nulo"),
                Arguments.of((Executable) () -> new Linha(
                        Linha.Tipo.TESTE, Linha.Status.PASS, "n", "o", "e", null),
                        "detalhe não pode ser nulo")
        );
    }

    private static Stream<Linha.Status> statusDisponiveis() {
        return Stream.of(Linha.Status.values());
    }
}
