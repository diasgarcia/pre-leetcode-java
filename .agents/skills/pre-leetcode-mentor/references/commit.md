# Commit

Executar somente quando o aluno pedir explicitamente um commit.

1. Ler `PROGRESSO.md` e derivar a branch esperada do módulo atual.
2. Executar `git branch --show-current` e bloquear o fluxo se a branch não corresponder.
3. Verificar `git status`, `git diff` e `git diff --cached`.
4. Se houver código Java alterado, delegar ao agente `verificador` a validação necessária antes do commit.
5. Se a validação falhar, parar sem preparar o commit.
6. Delegar exclusivamente ao agente `guardiao_git` a seleção explícita dos arquivos e a criação de no máximo um commit.
7. Não trocar de branch, não usar `git add .`, não criar commits em `main`, `template` ou `backup/**` e não fazer push.
8. Informar branch, SHA, arquivos incluídos e alterações restantes.
