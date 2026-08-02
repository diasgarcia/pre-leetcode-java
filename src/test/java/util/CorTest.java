package util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CorTest {

    @Test
    void aplicarDeveEnvolverTextoComCorEReset() {
        // Arrange
        String texto = "PASS";

        // Act
        String colorido = Cor.VERDE.aplicar(texto);

        // Assert
        assertEquals("\u001B[32mPASS\u001B[0m", colorido);
    }

    @Test
    void toStringDeveRetornarCodigoAnsi() {
        // Arrange
        Cor cor = Cor.AMARELO;

        // Act
        String codigo = cor.toString();

        // Assert
        assertEquals("\u001B[33m", codigo);
    }
}
