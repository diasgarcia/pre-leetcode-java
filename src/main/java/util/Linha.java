package util;

import java.util.Objects;

record Linha(Tipo tipo, Status status, String nome,
             String obtido, String esperado, String detalhe) {

    Linha {
        Objects.requireNonNull(tipo, "tipo não pode ser nulo");
        Objects.requireNonNull(status, "status não pode ser nulo");
        Objects.requireNonNull(nome, "nome não pode ser nulo");
        Objects.requireNonNull(obtido, "obtido não pode ser nulo");
        Objects.requireNonNull(esperado, "esperado não pode ser nulo");
        Objects.requireNonNull(detalhe, "detalhe não pode ser nulo");
    }

    static Linha teste(boolean passou, String caso, String obtido, String esperado) {
        String detalhe = passou ? "-" : "valores diferentes";
        return new Linha(Tipo.TESTE, passou ? Status.PASS : Status.FAIL,
                caso, obtido, esperado, detalhe);
    }

    static Linha resumo(boolean todosPassaram, String obtido, String esperado, String detalhe) {
        return new Linha(Tipo.RESUMO, todosPassaram ? Status.PASS : Status.FAIL,
                "testes", obtido, esperado, detalhe);
    }

    static Linha complexidade(Status status, String metodo,
                              String obtido, String esperado, String detalhe) {
        return new Linha(Tipo.CCN, status, metodo, obtido, esperado, detalhe);
    }

    enum Tipo {
        TESTE,
        RESUMO,
        CCN
    }

    enum Status {
        PASS(Cor.VERDE),
        FAIL(Cor.VERMELHO),
        OK(Cor.VERDE),
        ALERTA(Cor.AMARELO),
        SKIP(Cor.CINZA),
        INDISP(Cor.AMARELO),
        ERRO(Cor.VERMELHO);

        private final Cor cor;

        Status(Cor cor) {
            this.cor = cor;
        }

        String colorir(String texto) {
            return cor.aplicar(texto);
        }
    }
}
