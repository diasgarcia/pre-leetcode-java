# Validar

1. Ler PROGRESSO.md e identificar o exercício atual.
2. Delegar ao agente erificador a compilação e a execução do método main do exercício atual.
3. Aguardar o relatório do agente.
4. Não revisar estilo, não editar arquivos, não atualizar PROGRESSO.md e não criar o próximo exercício.
5. Retornar o relatório padronizado com:
   - COMPILACAO: APROVADA ou COMPILACAO: REPROVADA;
   - TESTES: APROVADOS, TESTES: REPROVADOS ou TESTES: NAO EXECUTADOS;
   - classe executada;
   - totais de testes;
   - falhas relevantes;
   - linhas de complexidade ciclomática.
6. Após os resultados, SEMPRE fornecer um feedback rápido de Clean Code:
   - Legibilidade: nomes de variáveis são claros e em português?
   - Redundância: há código repetido ou chamadas duplicadas (ex: charAt(i) duas vezes)?
   - Simplificação: a lógica está direta ou tem atalhos desnecessários?
   - Formatação: segue o padrão de indentação e tem comentários onde útil?
   - Se tudo estiver correto, dizer "Nada a melhorar". Se houver algo, apontar de forma objetiva.
   - Este feedback é obrigatório em toda execução de /validar.