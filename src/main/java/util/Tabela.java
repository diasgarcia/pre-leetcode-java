package util;

import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

final class Tabela {

    private static final int LARGURA_MINIMA_NOME = 25;
    private static final int LARGURA_MINIMA_VALOR = 8;
    private static final int LARGURA_MINIMA_DETALHE = 8;

    private final PrintStream saida;

    Tabela(PrintStream saida) {
        this.saida = Objects.requireNonNull(saida, "saída não pode ser nula");
    }

    void imprimir(List<Linha> linhas) {
        Objects.requireNonNull(linhas, "linhas não podem ser nulas");
        Dimensoes dimensoes = calcularDimensoes(linhas);

        imprimirCabecalho(dimensoes);

        Linha.Tipo tipoAnterior = null;
        for (Linha linha : linhas) {
            if (tipoAnterior == Linha.Tipo.TESTE && linha.tipo() != Linha.Tipo.TESTE) {
                imprimirSeparador(dimensoes);
            }
            imprimirLinha(linha, dimensoes);
            tipoAnterior = linha.tipo();
        }
    }

    private Dimensoes calcularDimensoes(List<Linha> linhas) {
        int larguraNome = LARGURA_MINIMA_NOME;
        int larguraValor = LARGURA_MINIMA_VALOR;
        int larguraDetalhe = LARGURA_MINIMA_DETALHE;

        for (Linha linha : linhas) {
            larguraNome = Math.max(larguraNome, linha.nome().length());
            larguraValor = Math.max(larguraValor, linha.obtido().length());
            larguraValor = Math.max(larguraValor, linha.esperado().length());
            larguraDetalhe = Math.max(larguraDetalhe, linha.detalhe().length());
        }

        return new Dimensoes(larguraNome, larguraValor, larguraDetalhe);
    }

    private void imprimirCabecalho(Dimensoes d) {
        saida.println();
        saida.printf("  %-6s  %-6s  %-" + d.nome() + "s  %" + d.valor() + "s  %" + d.valor() + "s  %s%n",
                "Tipo", "Status", "Caso / Metodo", "Obtido", "Esperado", "Detalhe");
        saida.printf("  %-6s  %-6s  %-" + d.nome() + "s  %" + d.valor() + "s  %" + d.valor() + "s  %s%n",
                "------", "------", "-".repeat(d.nome()), "-".repeat(d.valor()),
                "-".repeat(d.valor()), "-".repeat(d.detalhe()));
    }

    private void imprimirSeparador(Dimensoes d) {
        String separador = "  ------  ------  " + "-".repeat(d.nome())
                + "  " + "-".repeat(d.valor())
                + "  " + "-".repeat(d.valor())
                + "  " + "-".repeat(d.detalhe());
        saida.println(Cor.CINZA.aplicar(separador));
    }

    private void imprimirLinha(Linha linha, Dimensoes d) {
        saida.printf("  %-6s  ", linha.tipo());
        saida.print(linha.status().colorir(String.format("%-6s", linha.status())));
        saida.printf("  %-" + d.nome() + "s  ", linha.nome());

        String obtido = String.format("%" + d.valor() + "s", linha.obtido());
        saida.print("-".equals(linha.obtido()) ? Cor.CINZA.aplicar(obtido) : obtido);
        saida.print("  ");

        String esperado = String.format("%" + d.valor() + "s", linha.esperado());
        saida.print(Cor.CINZA.aplicar(esperado));
        saida.print("  ");

        String detalhe = linha.detalhe();
        saida.println("-".equals(detalhe) ? Cor.CINZA.aplicar(detalhe) : detalhe);
    }

    private record Dimensoes(int nome, int valor, int detalhe) {}
}
