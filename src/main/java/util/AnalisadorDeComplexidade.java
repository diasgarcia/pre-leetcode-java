package util;

import java.util.List;

@FunctionalInterface
interface AnalisadorDeComplexidade {

    AnalisadorCiclomatico.Analise analisar(Class<?> classe, List<String> metodosRegistrados);
}
