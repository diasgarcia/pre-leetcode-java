package util;

/**
 * Fachada alternativa para quem prefere importar {@code resultado} estaticamente.
 * O ciclo de vida da execução continua sendo controlado por {@link Testar}.
 */
public final class Resultado {

    private Resultado() {}

    public static void resultado(String caso, int esperado, int obtido) {
        Testar.resultado(caso, esperado, obtido);
    }

    public static void resultado(String caso, long esperado, long obtido) {
        Testar.resultado(caso, esperado, obtido);
    }

    public static void resultado(String caso, boolean esperado, boolean obtido) {
        Testar.resultado(caso, esperado, obtido);
    }

    public static void resultado(String caso, double esperado, double obtido, double delta) {
        Testar.resultado(caso, esperado, obtido, delta);
    }

    public static void resultado(String caso, String esperado, String obtido) {
        Testar.resultado(caso, esperado, obtido);
    }

    public static void resultado(String caso, int[] esperado, int[] obtido) {
        Testar.resultado(caso, esperado, obtido);
    }

    public static void resultado(String caso, Object esperado, Object obtido) {
        Testar.resultado(caso, esperado, obtido);
    }
}
