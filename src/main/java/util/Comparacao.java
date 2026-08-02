package util;

import java.util.Arrays;
import java.util.Objects;

record Comparacao(boolean passou, String obtido, String esperado) {

    Comparacao {
        Objects.requireNonNull(obtido, "valor obtido não pode ser nulo");
        Objects.requireNonNull(esperado, "valor esperado não pode ser nulo");
    }

    static Comparacao entre(Object esperado, Object obtido) {
        return new Comparacao(
                Objects.deepEquals(esperado, obtido),
                formatar(obtido),
                formatar(esperado)
        );
    }

    static Comparacao entre(double esperado, double obtido, double delta) {
        if (!Double.isFinite(delta) || delta < 0) {
            throw new IllegalArgumentException("delta deve ser um número finito maior ou igual a zero");
        }

        boolean valoresIguais = Double.compare(esperado, obtido) == 0;
        boolean dentroDaTolerancia = Math.abs(esperado - obtido) <= delta;

        return new Comparacao(
                valoresIguais || dentroDaTolerancia,
                String.valueOf(obtido),
                String.valueOf(esperado)
        );
    }

    private static String formatar(Object valor) {
        if (valor == null) return "null";
        if (valor instanceof Object[] array) return Arrays.deepToString(array);
        if (valor instanceof int[] array) return Arrays.toString(array);
        if (valor instanceof long[] array) return Arrays.toString(array);
        if (valor instanceof short[] array) return Arrays.toString(array);
        if (valor instanceof byte[] array) return Arrays.toString(array);
        if (valor instanceof char[] array) return Arrays.toString(array);
        if (valor instanceof boolean[] array) return Arrays.toString(array);
        if (valor instanceof float[] array) return Arrays.toString(array);
        if (valor instanceof double[] array) return Arrays.toString(array);
        return String.valueOf(valor);
    }
}
