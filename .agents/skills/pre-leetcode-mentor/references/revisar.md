# Revisar

Executar as etapas sequencialmente:

1. Ler `PROGRESSO.md` e identificar o exercício atual, seu método e suas restrições.
2. Delegar ao agente `verificador` a compilação e a execução do exercício.
3. Se a compilação ou os testes falharem:
   - não chamar o revisor nem o criador;
   - não alterar `PROGRESSO.md`;
   - explicar a falha;
   - oferecer no máximo uma dica de nível 1;
   - parar.
4. Se a verificação for aprovada, delegar ao agente `revisor` a análise completa da solução.
5. Se a revisão exigir ajustes:
   - explicar o problema, a entrada que o demonstra e uma dica de nível 1;
   - não alterar `PROGRESSO.md`;
   - não criar outro exercício;
   - parar.
6. Somente quando compilação, testes e revisão forem aprovados, delegar ao agente `criador_exercicio` a criação de exatamente um próximo exercício e a atualização de `PROGRESSO.md`.
7. Conferir as alterações do criador, informar o novo arquivo e parar.

Nunca criar commit ou fazer push como consequência desse fluxo.
