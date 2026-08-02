package util;

public final class Testar {

    private static final int LIMITE_CCN_PADRAO = 10;

    private static SessaoDeTestes sessaoAtual;

    private Testar() {}

    public static synchronized void iniciar(Class<?> classe, int limiteCiclomatico,
                                             String... metodos) {
        if (sessaoAtual != null && !sessaoAtual.finalizada()) {
            throw new IllegalStateException(
                    "Testar.finalizar() deve ser chamado antes de iniciar uma nova sessão");
        }

        sessaoAtual = new SessaoDeTestes(
                classe,
                limiteCiclomatico,
                metodos,
                new Tabela(System.out),
                new AnalisadorCiclomatico()
        );
    }

    public static synchronized void iniciar(Class<?> classe, String... metodos) {
        iniciar(classe, LIMITE_CCN_PADRAO, metodos);
    }

    public static synchronized void resultado(String caso, int esperado, int obtido) {
        registrar(caso, Comparacao.entre(esperado, obtido));
    }

    public static synchronized void resultado(String caso, long esperado, long obtido) {
        registrar(caso, Comparacao.entre(esperado, obtido));
    }

    public static synchronized void resultado(String caso, boolean esperado, boolean obtido) {
        registrar(caso, Comparacao.entre(esperado, obtido));
    }

    public static synchronized void resultado(String caso, double esperado,
                                               double obtido, double delta) {
        registrar(caso, Comparacao.entre(esperado, obtido, delta));
    }

    public static synchronized void resultado(String caso, String esperado, String obtido) {
        registrar(caso, Comparacao.entre(esperado, obtido));
    }

    public static synchronized void resultado(String caso, int[] esperado, int[] obtido) {
        registrar(caso, Comparacao.entre(esperado, obtido));
    }

    /**
     * Compara objetos com suporte a arrays de objetos, arrays primitivos e arrays aninhados.
     */
    public static synchronized void resultado(String caso, Object esperado, Object obtido) {
        registrar(caso, Comparacao.entre(esperado, obtido));
    }

    public static synchronized void finalizar() {
        obterSessaoAtual().finalizar();
    }

    static synchronized void reset() {
        sessaoAtual = null;
    }

    private static void registrar(String caso, Comparacao comparacao) {
        obterSessaoAtual().registrar(caso, comparacao);
    }

    private static SessaoDeTestes obterSessaoAtual() {
        if (sessaoAtual == null) {
            throw new IllegalStateException(
                    "Testar.iniciar(Class<?>, String...) deve ser chamado antes dos resultados");
        }
        return sessaoAtual;
    }
}
