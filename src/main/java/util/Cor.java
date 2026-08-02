package util;

enum Cor {

    VERDE("\u001B[32m"),
    VERMELHO("\u001B[31m"),
    CINZA("\u001B[90m"),
    AMARELO("\u001B[33m"),
    RESET("\u001B[0m");

    final String codigo;

    Cor(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return codigo;
    }
}
