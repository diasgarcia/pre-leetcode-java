package util;

import java.util.Arrays;
import java.util.Objects;

public final class Resultado {

    private Resultado() {}

    public static void resultado(String caso, int esperado, int obtido) {
        boolean passou = esperado == obtido;
        Testar.registrar(passou, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    public static void resultado(String caso, long esperado, long obtido) {
        boolean passou = esperado == obtido;
        Testar.registrar(passou, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    public static void resultado(String caso, boolean esperado, boolean obtido) {
        boolean passou = esperado == obtido;
        Testar.registrar(passou, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    public static void resultado(String caso, double esperado, double obtido, double delta) {
        boolean passou = Math.abs(esperado - obtido) <= delta;
        Testar.registrar(passou, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    public static void resultado(String caso, String esperado, String obtido) {
        boolean passou = esperado == null ? obtido == null : esperado.equals(obtido);
        Testar.registrar(passou, caso, String.valueOf(obtido), String.valueOf(esperado));
    }

    public static void resultado(String caso, int[] esperado, int[] obtido) {
        boolean passou = Arrays.equals(esperado, obtido);
        Testar.registrar(passou, caso, Arrays.toString(obtido), Arrays.toString(esperado));
    }

    public static void resultado(String caso, Object esperado, Object obtido) {
        boolean passou = Objects.equals(esperado, obtido);
        Testar.registrar(passou, caso, String.valueOf(obtido), String.valueOf(esperado));
    }
}
