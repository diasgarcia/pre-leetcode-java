---
description: Compila e executa o exercicio atual exibindo a analise ciclomatica na tabela unificada
subtask: true
---

# /analisar

1. Leia `PROGRESSO.md` e identifique o exercicio atual.
2. Compile e execute o exercicio. A analise ciclomatica aparece na mesma tabela dos testes.
3. Mostre a saida completa para o aluno.
4. Interprete as linhas `CCN`:
   - `OK`: CCN dentro do limite (≤ 10), mostrar classificacao (baixa/moderada/alta)
   - `ALERTA`: CCN acima de 10, sugerir simplificacao mas nao reprovar
   - `SKIP`: testes falharam, orientar corrigir primeiro
   - `INDISP`: Lizard nao instalado, orientar `py -m pip install lizard`
   - `ERRO`: metodo nao encontrado, verificar nome registrado
5. Nao edite arquivos.
