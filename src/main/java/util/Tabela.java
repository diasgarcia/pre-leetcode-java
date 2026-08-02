package util;

import java.util.ArrayList;
import java.util.List;

class Tabela {

    private final List<Linha> linhas = new ArrayList<>();
    private boolean cabecalhoImpresso;
    private int larguraNome = 25;
    private int larguraValor = 8;

    void adicionar(Linha linha) {
        linhas.add(linha);
    }

    void calcularLarguras() {
        for (Linha l : linhas) {
            int t = l.nome().length();
            if (t > larguraNome) larguraNome = t;
            t = l.obtido().length();
            if (t > larguraValor) larguraValor = t;
            t = l.esperado().length();
            if (t > larguraValor) larguraValor = t;
        }
    }

    void imprimirTodas() {
        imprimirCabecalho();
        for (Linha l : linhas) {
            imprimirLinha(l);
        }
    }

    void imprimirSeparador() {
        System.out.println(Cor.CINZA
                + "  ------  ------  " + "-".repeat(larguraNome)
                + "  " + "-".repeat(larguraValor)
                + "  " + "-".repeat(larguraValor)
                + "  ------------"
                + Cor.RESET);
    }

    void imprimirLinha(Linha l) {
        imprimirCabecalho();

        System.out.print("  ");
        System.out.printf("%-6s", l.tipo());
        System.out.print("  ");

        System.out.print(l.corStatus());
        System.out.printf("%-6s", l.status());
        System.out.print(Cor.RESET);
        System.out.print("  ");

        System.out.printf("%-" + larguraNome + "s  ", l.nome());

        if ("-".equals(l.obtido())) {
            System.out.print(Cor.CINZA);
            System.out.printf("%" + larguraValor + "s", l.obtido());
            System.out.print(Cor.RESET);
        } else {
            System.out.printf("%" + larguraValor + "s", l.obtido());
        }
        System.out.print("  ");

        System.out.print(Cor.CINZA);
        System.out.printf("%" + larguraValor + "s", l.esperado());
        System.out.print(Cor.RESET);
        System.out.print("  ");

        if ("-".equals(l.detalhe())) {
            System.out.println(Cor.CINZA + l.detalhe() + Cor.RESET);
        } else {
            System.out.println(l.detalhe());
        }
    }

    void reset() {
        linhas.clear();
        cabecalhoImpresso = false;
        larguraNome = 25;
        larguraValor = 8;
    }

    private void imprimirCabecalho() {
        if (cabecalhoImpresso) return;
        cabecalhoImpresso = true;

        System.out.println();
        System.out.printf("  %-6s  %-6s  %-" + larguraNome + "s  %" + larguraValor + "s  %" + larguraValor + "s  %s%n",
                "Tipo", "Status", "Caso / Metodo", "Obtido", "Esperado", "Detalhe");
        System.out.printf("  %-6s  %-6s  %-" + larguraNome + "s  %" + larguraValor + "s  %" + larguraValor + "s  %s%n",
                "------", "------", "-".repeat(larguraNome), "-".repeat(larguraValor), "-".repeat(larguraValor), "--------");
    }
}
