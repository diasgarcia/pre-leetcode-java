# Analisar complexidade

1. Ler `PROGRESSO.md` e identificar o exercício atual.
2. Delegar ao agente `verificador` a compilação e a execução do exercício, solicitando a saída completa da tabela unificada.
3. Não editar arquivos.
4. Mostrar a saída e interpretar as linhas `CCN`:
   - `OK`: dentro do limite; informar a classificação;
   - `ALERTA`: acima do limite; sugerir simplificação sem reprovar automaticamente;
   - `SKIP`: testes falharam; orientar a correção antes da análise;
   - `INDISP`: Lizard ausente; informar que é opcional e sugerir `py -m pip install lizard`;
   - `ERRO`: método não encontrado; conferir o nome registrado no exercício.
