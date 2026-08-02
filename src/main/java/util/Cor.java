package util;

enum Cor {

    VERDE("\u001B[32m"),
    VERMELHO("\u001B[31m"),
    CINZA("\u001B[90m"),
    AMARELO("\u001B[33m"),
    RESET("\u001B[0m");

    private final String codigo;

    Cor(String codigo) {
        this.codigo = codigo;
    }

    String aplicar(String texto) {
        return codigo + texto + RESET.codigo;
    }

    @Override
    public String toString() {
        return codigo;
    }
}
