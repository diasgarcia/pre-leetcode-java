package util;

record Linha(String tipo, String status, boolean passou, String nome,
             String obtido, String esperado, String detalhe, Cor corStatus) {

    static Linha teste(boolean passou, String caso, String obtido, String esperado) {
        String status = passou ? "PASS" : "FAIL";
        Cor cor = passou ? Cor.VERDE : Cor.VERMELHO;
        String detalhe = passou ? "-" : "valores diferentes";
        return new Linha("TESTE", status, passou, caso, obtido, esperado, detalhe, cor);
    }
}
