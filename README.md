# Pré-LeetCode Java

Trilha progressiva de algoritmos e estruturas de dados em Java, pensada para quem já conhece a sintaxe básica da linguagem mas ainda sente dificuldade com problemas de lógica, interpretação de enunciados e reconhecimento de padrões.

## Motivação

Plataformas como LeetCode, Codeforces e HackerRank expõem o programador diretamente a problemas que exigem raciocínio combinado de várias estruturas. Para quem está começando, isso pode ser frustrante.

Este projeto inverte a abordagem: primeiro ensina os fundamentos e os padrões de resolução. Depois, e só depois, apresenta problemas no estilo LeetCode. O objetivo deste projeto não é decorar soluções. A proposta é aprender a reconhecer padrões, construir a solução passo a passo e entender seus custos antes de enfrentar problemas reais.

## Público-alvo

- Já conhece sintaxe básica de Java (variáveis, loops, condicionais, métodos).
- Ainda não se sente confortável resolvendo problemas do LeetCode.
- Quer aprender algoritmos e estruturas de dados de forma progressiva.
- Prefere estudar no próprio ritmo, um exercício por vez.

## Metodologia

Cada módulo segue a mesma sequência:

1. **Teoria** — explicação conceitual no arquivo `.md` da pasta `teoria/`.
2. **Exercícios progressivos** — começam com fundamentos e sobem gradualmente até problemas estilo LeetCode.
3. **Implementação** — você escreve apenas o método principal. Os testes já estão prontos.
4. **Revisão** — um mentor (humano ou LLM) revisa sua solução antes de liberar o próximo exercício.
5. **Avanço** — você só passa para o próximo exercício quando o atual estiver correto.

## Estrutura de diretórios

```
pre-leetcode-java/
├── README.md              ← você está aqui
├── AGENTS.md              ← instruções para LLMs atuarem como mentores
├── PROGRESSO.md           ← estado atual da mentoria
├── .gitignore
├── teoria/
│   └── 01_arrays_e_loops.md
└── src/main/java/exercicios/
    ├── arrays_e_loops/
    ├── strings/
    ├── hashmap_e_hashset/
    ├── dois_ponteiros/
    ├── janela_deslizante/
    ├── pilha_e_fila/
    ├── busca_binaria/
    ├── recursao/
    ├── lista_encadeada/
    ├── arvores/
    └── grafos/
```

## Roadmap

| Módulo | Conteúdo principal |
|---|---|
| 1 — Arrays e loops | Percorrer, contar, acumular, buscar, complexidade O(n) e O(n²) |
| 2 — Strings | Caracteres, frequência, palíndromos, construção de strings |
| 3 — HashMap e HashSet | Chave/valor, contagem, eliminar duplicados, busca O(1) |
| 4 — Dois ponteiros | Início/fim, mover por condição, arrays ordenados |
| 5 — Janela deslizante | Faixa contínua, janela fixa e variável, evitar recomputação |
| 6 — Pilha e fila | LIFO/FIFO, validação de estruturas aninhadas |
| 7 — Busca binária | Dividir intervalo, evitar erros de limite, inserção |
| 8 — Recursão | Caso-base, pilha de chamadas, comparar com loops |
| 9 — Lista encadeada | Nós, referências, ponteiros lento/rápido |
| 10 — Árvores | DFS, BFS, altura, inversão, comparação |
| 11 — Grafos | Vértices, arestas, lista de adjacência, BFS/DFS |

## Níveis de dificuldade

| Nível | Descrição |
|---|---|
| Fundamento | Exercício mais simples do módulo. Foco na operação básica. |
| Fácil | Um passo além do fundamento. Exige combinar duas ideias simples. |
| Fácil intermediário | Exige atenção a detalhes ou casos extremos. |
| Desafio do módulo | Combina vários conceitos do módulo. |
| Estilo LeetCode | Problema equivalente a um Easy do LeetCode. |

## Como executar um exercício

### Windows (PowerShell)

```powershell
cd pre-leetcode-java
mvn compile exec:java -Dexec.mainClass="exercicios.arrays_e_loops.Exercicio01"
```

Ou manualmente:

```powershell
javac -d target/classes src/main/java/exercicios/arrays_e_loops/Exercicio01.java
java -cp target/classes exercicios.arrays_e_loops.Exercicio01
```

### Linux / macOS

```bash
cd pre-leetcode-java
mvn compile exec:java -Dexec.mainClass="exercicios.arrays_e_loops.Exercicio01"
```

Ou manualmente:

```bash
javac -d target/classes src/main/java/exercicios/arrays_e_loops/Exercicio01.java
java -cp target/classes exercicios.arrays_e_loops.Exercicio01
```

### Saída esperada

A saída é uma tabela unificada com testes, resumo e complexidade ciclomática:

```
  Tipo    Status  Caso / Metodo                Obtido  Esperado  Detalhe
  ------  ------  -------------------------  --------  --------  --------
  TESTE   PASS    array comum                       6         6  -
  TESTE   PASS    array vazio                       0         0  -
  RESUMO  PASS    testes                          2/2       2/2  todos passaram
  CCN     OK      somar                             2     <= 3  baixa
```

Se algum teste falhar, a linha de CCN mostrará `SKIP`:

## Regras de estudo

1. **Um exercício por vez.** Não pule etapas.
2. **Leia a teoria do módulo antes de começar.**
3. **Tente resolver sozinho antes de pedir ajuda.**
4. **Não use Streams, lambdas ou bibliotecas externas nos módulos iniciais** (a restrição estará indicada em cada exercício).
5. **Não copie soluções prontas.** O objetivo é aprender o raciocínio.
6. **Só avance quando o exercício estiver correto** e você entender a complexidade.
7. **Se travar, peça dicas progressivas** (veja a seção abaixo).

## Como usar uma LLM como mentora

O arquivo `AGENTS.md` contém instruções detalhadas para que qualquer LLM atue como mentor. O fluxo recomendado:

1. Abra o `PROGRESSO.md` para ver qual é o exercício atual.
2. Abra o arquivo do exercício em `src/main/java/exercicios/...`.
3. Implemente o método marcado com `TODO`.
4. Execute os testes.
5. Se todos passarem, informe ao mentor que o exercício está pronto para revisão.
6. Se algum falhar, revise seu código antes de pedir ajuda.
7. Se estiver travado, peça uma **dica** em vez da solução.

O mentor seguirá três princípios:

- **Não entregar a solução antes de você tentar.**
- **Começar com dicas pequenas e aumentar gradualmente.**
- **Explicar o raciocínio, não apenas o código.**

### Usando OpenCode

Este repositório foi criado e é mantido com [OpenCode](https://opencode.ai),
uma CLI que integra LLMs diretamente no terminal com contexto completo do
projeto.

A stack usada pelo autor:

- **[OpenCode](https://opencode.ai)** — orquestrador que gerencia agentes,
  comandos e contexto do projeto
- **[DeepSeek V4 Pro](https://deepseek.com)** — modelo de linguagem usado
  como mentor

**Funciona com qualquer LLM.** O arquivo `AGENTS.md` é o contrato que
qualquer modelo segue — seja via OpenCode, ChatGPT, Claude, Gemini ou
Copilot. Basta fornecer o `AGENTS.md` como instrução de sistema e o
`PROGRESSO.md` como contexto. Para melhores resultados, use modelos com
suporte a raciocínio (reasoning).

O projeto inclui agentes e comandos pré-configurados para OpenCode em
`.opencode/`:

| Comando | Ação |
|---|---|
| `/continuar` | Mostra o estado atual e o próximo passo |
| `/revisar` | Fluxo completo: validar, revisar e criar próximo exercício |
| `/validar` | Compila e executa os testes sem revisar nem avançar |
| `/dica [nível]` | Fornece dica progressiva (nível 1 a 5) |
| `/proximo` | Verifica aprovação e cria o próximo exercício |
| `/progresso` | Exibe o estado da trilha sem alterar nada |
| `/analisar` | Compila, executa e mostra a complexidade ciclomática |

## Tabela de progresso

| Módulo | Status |
|---|---|
| 1 — Arrays e loops | Em andamento |
| 2 — Strings | Pendente |
| 3 — HashMap e HashSet | Pendente |
| 4 — Dois ponteiros | Pendente |
| 5 — Janela deslizante | Pendente |
| 6 — Pilha e fila | Pendente |
| 7 — Busca binária | Pendente |
| 8 — Recursão | Pendente |
| 9 — Lista encadeada | Pendente |
| 10 — Árvores | Pendente |
| 11 — Grafos | Pendente |

## Convenções

- Nomes de classes, métodos, variáveis e pacotes em **português brasileiro**.
- Um arquivo `.java` por exercício.
- Classes nomeadas como `Exercicio01`, `Exercicio02`, etc.
- Testes usam `util.Testar.resultado(...)` com tabela colorida unificada.
- Todo `main` começa com `Testar.iniciar(...)` e termina com `Testar.finalizar()`.
- O método a ser implementado contém `// TODO: implemente sua solução`.

## Complexidade de tempo e espaço

Todo exercício indica a complexidade esperada (ex: `O(n)`, `O(1)`). Entender a complexidade é tão importante quanto fazer o código funcionar. Cada arquivo de teoria explica os fundamentos de análise para aquele módulo.

## LeetCode é consequência, não ponto de partida

Este projeto não é uma coleção de soluções do LeetCode traduzidas. A trilha foi desenhada para que, ao chegar nos exercícios "Estilo LeetCode", você já tenha praticado todas as peças necessárias para resolvê-los com confiança. O LeetCode aparece como verificação natural do seu aprendizado, e não como fonte de ansiedade.

---

**Uso educacional.** Este projeto é gratuito e destinado ao estudo individual.
