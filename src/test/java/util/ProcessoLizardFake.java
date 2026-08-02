package util;

public final class ProcessoLizardFake {

    private ProcessoLizardFake() {}

    public static void main(String[] args) throws InterruptedException {
        String modo = args[0];

        if ("sucesso".equals(modo)) {
            imprimirSaidaValida();
            return;
        }
        if ("falha".equals(modo)) {
            System.exit(7);
        }
        if ("demora".equals(modo)) {
            Thread.sleep(5_000);
        }
    }

    private static void imprimirSaidaValida() {
        System.out.println("linha fora da tabela");
        System.out.println(" NLOC CCN token PARAM length location");
        System.out.println("linha sem numeros");
        System.out.println("1 2 somente tres");
        System.out.println("1 2 a b semArroba");
        System.out.println("1 2 a b Classe@1-2");
        System.out.println("1 2 a b Classe::metodoValido@1-2");
        System.out.println("1 file analyzed.");
        System.out.println("1 99 a b Classe::foraDaTabela@1-2");
    }
}
