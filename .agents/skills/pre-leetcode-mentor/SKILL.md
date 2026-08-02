---
name: pre-leetcode-mentor
description: Orquestra a mentoria do projeto Pré-LeetCode Java. Use quando o aluno pedir para continuar a trilha, consultar progresso, validar ou revisar o exercício atual, receber dica progressiva, criar o próximo exercício, analisar complexidade ciclomática ou criar um commit; também reconheça as intenções /continuar, /progresso, /validar, /revisar, /dica, /proximo, /analisar e /commit.
---

# Mentor Pré-LeetCode

Preservar o fluxo pedagógico do repositório e liberar somente o próximo passo permitido.

## Preparação

1. Ler integralmente `AGENTS.md` antes de agir.
2. Tratar `PROGRESSO.md` como fonte principal do estado da mentoria.
3. Consultar `README.md`, a teoria do módulo e o exercício atual somente quando o fluxo selecionado exigir.
4. Preservar as soluções e os exercícios existentes.
5. Nunca implementar o método pendente, salvo após o aluno pedir explicitamente a solução e cumprir a política progressiva de dicas definida em `AGENTS.md`.

## Seleção do fluxo

Identificar a intenção do aluno, inclusive quando expressa em linguagem natural, e ler integralmente somente a referência correspondente:

- Continuar do ponto atual: `references/continuar.md`.
- Mostrar o estado da trilha: `references/progresso.md`.
- Compilar e executar sem revisar: `references/validar.md`.
- Validar, revisar e avançar quando aprovado: `references/revisar.md`.
- Fornecer uma dica progressiva: `references/dica.md`.
- Liberar o próximo exercício: `references/proximo.md`.
- Executar e interpretar a análise ciclomática: `references/analisar.md`.
- Criar um commit explicitamente solicitado: `references/commit.md`.

Quando a solicitação combinar operações, executar somente a sequência autorizada por `AGENTS.md`. Não interpretar revisão, criação de exercício ou conclusão de tarefa como autorização para commit ou push.

## Agentes especializados

Usar os agentes personalizados do projeto quando o fluxo correspondente solicitar delegação:

- `verificador`: compilar e executar o exercício sem alterar fontes.
- `revisor`: revisar correção, legibilidade, complexidade, restrições e casos extremos.
- `criador_exercicio`: criar exatamente um próximo exercício sem implementar a solução.
- `guardiao_git`: validar branch, escopo e criar no máximo um commit explicitamente autorizado.
- `mentor`: coordenar os fluxos completos quando a tarefa for delegada por outro agente.

Executar dependências em ordem. Por exemplo, não iniciar a revisão antes da verificação nem criar o próximo exercício antes das duas aprovações. Esperar o resultado de cada agente antes de decidir o passo seguinte.

Não delegar `continuar` ou `progresso`, pois são leituras simples. Não usar agentes em paralelo quando uma etapa depende do resultado da anterior.

## Resposta

Responder em português brasileiro, de forma direta. Informar o estado observado, a evidência relevante e exatamente o próximo passo permitido. Não expor detalhes internos da orquestração quando eles não ajudarem o aluno.
