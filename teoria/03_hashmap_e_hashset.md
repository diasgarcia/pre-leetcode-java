# Módulo 3 — HashMap e HashSet

## Objetivo

Aprender a usar `HashMap` (mapeamento chave → valor) e `HashSet` (conjunto sem duplicatas) para resolver problemas de contagem, verificação de existência, agrupamento e busca eficiente em tempo `O(1)` médio.

Este módulo evolui os padrões de contagem do Módulo 2 (array de 26 posições) e de busca do Módulo 1 (loop linear) para estruturas que funcionam com qualquer conjunto de chaves, não apenas letras minúsculas.

---

## Explicação conceitual

### Por que precisamos de HashMap e HashSet?

Até agora, você resolveu problemas de contagem com arrays de tamanho fixo:

```java
// Módulo 2 — array de frequência (só funciona para 'a'..'z')
int[] freq = new int[26];
for (char c : texto.toCharArray()) {
    if (c >= 'a' && c <= 'z') {
        freq[c - 'a']++;
    }
}
```

Isso funciona bem quando o domínio é pequeno e conhecido (26 letras). Mas e se você precisar:

- Contar palavras em um texto?
- Contar a frequência de **qualquer** caractere Unicode?
- Mapear IDs para nomes?
- Verificar se um array contém duplicatas sem ordenar?

Aí entram `HashMap` e `HashSet`.

---

### O que é um HashSet?

Um `HashSet` é uma coleção que **não permite elementos duplicados**. Ele armazena cada elemento no máximo uma vez.

```java
import java.util.HashSet;

HashSet<Integer> conjunto = new HashSet<>();
conjunto.add(10);     // true  — elemento foi adicionado
conjunto.add(20);     // true  — elemento foi adicionado
conjunto.add(10);     // false — elemento JÁ existia, não foi adicionado
```

O método `add()` retorna `true` se o elemento foi adicionado (não existia) e `false` se o elemento já estava no conjunto.

| Método | Descrição | Complexidade |
|---|---|---|
| `add(elemento)` | Adiciona o elemento se ele não existir. Retorna `true` se adicionou. | `O(1)` médio |
| `contains(elemento)` | Retorna `true` se o elemento existe no conjunto. | `O(1)` médio |
| `remove(elemento)` | Remove o elemento. Retorna `true` se ele existia. | `O(1)` médio |
| `size()` | Retorna a quantidade de elementos. | `O(1)` |
| `isEmpty()` | Retorna `true` se o conjunto está vazio. | `O(1)` |
| `clear()` | Remove todos os elementos. | `O(n)` |

#### Quando usar HashSet

- Verificar se um elemento já apareceu (detecção de duplicatas).
- Eliminar duplicatas de uma lista.
- Armazenar valores que já foram visitados (ex: em grafos).
- Verificar pertinência: "o valor X está neste conjunto?"

#### Iteração em HashSet

```java
HashSet<String> nomes = new HashSet<>();
nomes.add("Ana");
nomes.add("Bruno");
nomes.add("Carla");

for (String nome : nomes) {
    System.out.println(nome);
}
```

**Atenção:** a ordem da iteração **não é garantida**. O HashSet não mantém a ordem de inserção. Se você precisa de ordem de inserção, use `LinkedHashSet`. Se precisa de ordem natural (crescente), use `TreeSet`.

---

### O que é um HashMap?

Um `HashMap` armazena pares **chave → valor**. Cada chave é única e mapeia para exatamente um valor.

```java
import java.util.HashMap;

HashMap<String, Integer> idades = new HashMap<>();
idades.put("Ana", 25);     // mapeia "Ana" → 25
idades.put("Bruno", 30);   // mapeia "Bruno" → 30
idades.put("Ana", 26);     // sobrescreve: "Ana" → 26
```

| Método | Descrição | Complexidade |
|---|---|---|
| `put(chave, valor)` | Associa a chave ao valor. Retorna o valor anterior (ou `null`). | `O(1)` médio |
| `get(chave)` | Retorna o valor associado à chave, ou `null` se não existir. | `O(1)` médio |
| `getOrDefault(chave, padrao)` | Retorna o valor ou um valor padrão se a chave não existir. | `O(1)` médio |
| `containsKey(chave)` | Retorna `true` se a chave existe no mapa. | `O(1)` médio |
| `containsValue(valor)` | Retorna `true` se o valor existe. | `O(n)` |
| `remove(chave)` | Remove a chave e retorna o valor associado. | `O(1)` médio |
| `size()` | Retorna a quantidade de pares chave/valor. | `O(1)` |
| `isEmpty()` | Retorna `true` se o mapa está vazio. | `O(1)` |
| `keySet()` | Retorna um `Set` com todas as chaves. | `O(1)` para obter o set |
| `values()` | Retorna uma `Collection` com todos os valores. | `O(1)` para obter a collection |
| `entrySet()` | Retorna um `Set` de pares chave/valor (`Map.Entry`). | `O(1)` para obter o set |

#### Exemplo: `getOrDefault`

```java
HashMap<String, Integer> contagem = new HashMap<>();
String palavra = "java";
contagem.put(palavra, contagem.getOrDefault(palavra, 0) + 1);
```

Isso é equivalente a:

```java
if (contagem.containsKey(palavra)) {
    contagem.put(palavra, contagem.get(palavra) + 1);
} else {
    contagem.put(palavra, 1);
}
```

#### Quando usar HashMap

- Contar frequência de itens (palavras, caracteres, números).
- Mapear um identificador para um objeto (ex: ID → nome).
- Armazenar resultados intermediários (memoização).
- Agrupar itens por categoria.
- Implementar "two-sum" em `O(n)`.

---

### HashMap vs HashSet — como escolher?

| Situação | Estrutura |
|---|---|
| Só preciso saber se algo existe ou já apareceu | `HashSet` |
| Preciso associar uma chave a um valor (ex: nome → idade) | `HashMap` |
| Preciso contar quantas vezes cada item aparece | `HashMap<Item, Integer>` |
| Preciso agrupar itens (ex: anagramas, categorias) | `HashMap<Chave, List<Item>>` |
| Preciso eliminar duplicatas de uma lista | `HashSet` |

Regra simples: se você só precisa de "sim/não", use `HashSet`. Se precisa armazenar ou acumular algo junto com a chave, use `HashMap`.

---

### Iteração em HashMap

Há três formas principais de iterar:

#### 1. Pelas chaves — `keySet()`

```java
HashMap<String, Integer> mapa = new HashMap<>();
// ... preencher o mapa ...

for (String chave : mapa.keySet()) {
    System.out.println(chave + " -> " + mapa.get(chave));
}
```

#### 2. Pelos valores — `values()`

```java
int soma = 0;
for (int valor : mapa.values()) {
    soma += valor;
}
```

#### 3. Por pares chave/valor — `entrySet()` (mais eficiente)

```java
for (Map.Entry<String, Integer> entrada : mapa.entrySet()) {
    String chave = entrada.getKey();
    int valor = entrada.getValue();
    System.out.println(chave + " -> " + valor);
}
```

| Forma | Quando usar |
|---|---|
| `keySet()` + `get()` | Quando precisa da chave e ocasionalmente do valor |
| `values()` | Quando só precisa dos valores (ex: soma, média) |
| `entrySet()` | Quando precisa de chave e valor em toda iteração — evita `get()` extra |

---

## Representação passo a passo

### Exemplo: contar frequência de caracteres com HashMap

Texto: `"banana"`

```
início:         mapa = {}
caractere 'b':  mapa.getOrDefault('b', 0) = 0 → put('b', 1)   → mapa = {'b'=1}
caractere 'a':  mapa.getOrDefault('a', 0) = 0 → put('a', 1)   → mapa = {'b'=1, 'a'=1}
caractere 'n':  mapa.getOrDefault('n', 0) = 0 → put('n', 1)   → mapa = {'b'=1, 'a'=1, 'n'=1}
caractere 'a':  mapa.getOrDefault('a', 0) = 1 → put('a', 2)   → mapa = {'b'=1, 'a'=2, 'n'=1}
caractere 'n':  mapa.getOrDefault('n', 0) = 1 → put('n', 2)   → mapa = {'b'=1, 'a'=2, 'n'=2}
caractere 'a':  mapa.getOrDefault('a', 0) = 2 → put('a', 3)   → mapa = {'b'=1, 'a'=3, 'n'=2}

resultado: {'a'=3, 'b'=1, 'n'=2}
```

### Exemplo: verificar duplicatas com HashSet

Array: `[1, 3, 5, 3]`

```
início:      conjunto = {}
elemento 1:  add(1)  → true  → conjunto = {1}
elemento 3:  add(3)  → true  → conjunto = {1, 3}
elemento 5:  add(5)  → true  → conjunto = {1, 3, 5}
elemento 3:  add(3)  → false → DUPLICATA! retorna true
```

---

## Padrões comuns de resolução

### Padrão 1: Contagem de frequência

```java
HashMap<T, Integer> freq = new HashMap<>();
for (T item : colecao) {
    freq.put(item, freq.getOrDefault(item, 0) + 1);
}
```

### Padrão 2: Verificar duplicatas

```java
HashSet<T> visto = new HashSet<>();
for (T item : colecao) {
    if (visto.contains(item)) {
        return true; // achou duplicata
    }
    visto.add(item);
}
return false;
```

Ou, mais compacto, usando o retorno de `add()`:

```java
HashSet<T> visto = new HashSet<>();
for (T item : colecao) {
    if (!visto.add(item)) {
        return true; // add retornou false → já existia
    }
}
return false;
```

### Padrão 3: Two‑sum com HashMap (busca complementar)

Dado um array e um alvo, encontrar dois números que somam o alvo:

```java
HashMap<Integer, Integer> indice = new HashMap<>();
for (int i = 0; i < numeros.length; i++) {
    int complemento = alvo - numeros[i];
    if (indice.containsKey(complemento)) {
        return new int[]{indice.get(complemento), i};
    }
    indice.put(numeros[i], i);
}
```

### Padrão 4: Agrupamento

Agrupar strings que são anagramas (mesmas letras em ordem diferente):

```java
HashMap<String, List<String>> grupos = new HashMap<>();
for (String palavra : lista) {
    char[] chars = palavra.toCharArray();
    Arrays.sort(chars);
    String chave = new String(chars);
    grupos.computeIfAbsent(chave, k -> new ArrayList<>()).add(palavra);
}
```

---

## Complexidade

| Operação | Complexidade média | Pior caso |
|---|---|---|
| `put()` / `get()` / `containsKey()` / `add()` / `contains()` / `remove()` | `O(1)` | `O(n)` |
| `containsValue()` | `O(n)` | `O(n)` |
| Iterar `keySet()`, `values()`, `entrySet()` | `O(n)` | `O(n)` |
| `size()`, `isEmpty()` | `O(1)` | `O(1)` |

Na prática, o caso médio `O(1)` é o que realmente importa para a maioria dos problemas. O pior caso `O(n)` acontece quando muitas chaves diferentes produzem o mesmo hash (colisões), o que é raro com uma boa função hash — como as fornecidas pelas classes padrão do Java (`String`, `Integer`, etc.).

---

## Erros comuns

| Erro | Consequência | Como evitar |
|---|---|---|
| Usar `HashMap` quando `HashSet` basta | Código mais verboso e confuso | Se o valor é sempre 1 ou boolean, use `HashSet` |
| Usar `get()` sem verificar `null` | `NullPointerException` ao operar sobre o valor | Use `getOrDefault()` ou verifique com `containsKey()` |
| Modificar o mapa/enquanto itera com for-each | `ConcurrentModificationException` | Use `Iterator.remove()` ou colete chaves em uma lista separada |
| Usar `containsValue()` dentro de um loop | Complexidade `O(n²)` em vez de `O(n)` | Estruture para usar `containsKey()` |
| Achar que HashMap mantém ordem | Resultado inesperado na iteração | Use `LinkedHashMap` se precisar de ordem de inserção |
| Usar objetos mutáveis como chave | O mapa "perde" a entrada após modificação | Use apenas objetos imutáveis como chave (`String`, `Integer`, etc.) |
| Não importar `java.util.HashMap` / `java.util.HashSet` | Erro de compilação | Sempre verifique os imports |

---

## Sinais de que um problema usa este padrão

- "Conte quantas vezes cada..." → `HashMap` de frequência.
- "Verifique se há duplicatas..." → `HashSet`.
- "Encontre dois números que somam..." → `HashMap` (two‑sum).
- "Agrupe por..." → `HashMap` com listas.
- "Qual é o primeiro caractere que não se repete?" → `HashMap` de frequência + busca.
- "Interseção de dois arrays..." → `HashSet`.
- "Verifique se duas strings são anagramas..." → `HashMap` de frequência.

---

## Relação com os módulos anteriores

| Módulo 1 (Arrays) | Módulo 2 (Strings) | Módulo 3 (HashMap / HashSet) |
|---|---|---|
| Buscar valor: loop `O(n)` | Buscar caractere: loop `O(n)` | Buscar chave: `O(1)` médio |
| Array de contagem: tamanho fixo | `int[26]` para 'a'..'z' | `HashMap` para domínio ilimitado |
| Dois loops aninhados: `O(n²)` | — | Two‑sum: `O(n)` com `HashMap` |

---

## Lista planejada de exercícios

| # | Título | Dificuldade | Foco |
|---|---|---|---|
| 01 | Verificar duplicatas em um array | Fundamento | `HashSet`, `add()` |
| 02 | Contar frequência de caracteres | Fundamento | `HashMap`, `getOrDefault()`, `entrySet()` |
| 03 | Dois números que somam ao alvo (two‑sum) | Fácil | `HashMap`, busca por complemento |
| 04 | Primeiro caractere único em uma string | Fácil | `HashMap` de frequência, busca linear |
| 05 | Interseção de dois arrays | Fácil | `HashSet`, `contains()` |
| 06 | Verificar anagramas com HashMap | Intermediário | `HashMap` de frequência, comparação |
| 07 | Agrupar anagramas | Intermediário | `HashMap` com listas, chave composta |
| 08 | Elemento majoritário (Boyer–Moore vs HashMap) | Intermediário | `HashMap` vs algoritmo O(1) de espaço |
| 09 | Encontrar todos os duplicados em um array | Intermediário | `HashMap`, iteração |
| 10 | Valid Sudoku (LeetCode 36) | Desafio | `HashSet` de strings compostas, três regras |

---

## Problemas do LeetCode relacionados

Após concluir este módulo, você estará preparado para enfrentar:

- **Contains Duplicate** (Easy) — verificar duplicatas
- **Two Sum** (Easy) — par com soma alvo
- **First Unique Character in a String** (Easy) — primeiro caractere único
- **Intersection of Two Arrays** (Easy) — interseção com `HashSet`
- **Valid Anagram** (Easy) — anagramas com `HashMap`
- **Group Anagrams** (Medium) — agrupamento com `HashMap`
- **Valid Sudoku** (Medium) — validação com `HashSet`

---

## Critérios para considerar o módulo concluído

- [ ] Sabe usar `HashSet` para verificar existência e duplicatas.
- [ ] Sabe usar `HashMap` para contagem de frequência com `getOrDefault()`.
- [ ] Sabe iterar um `HashMap` com `keySet()`, `values()` e `entrySet()`.
- [ ] Entende a diferença entre `HashMap` e `HashSet` e escolhe a estrutura certa.
- [ ] Sabe aplicar o padrão two‑sum com `HashMap`.
- [ ] Consegue agrupar itens usando `HashMap` com listas.
- [ ] Entende por que as operações são `O(1)` no caso médio.
- [ ] Sabe evitar os erros comuns (`NullPointerException`, modificar durante iteração).
- [ ] Trata entradas vazias corretamente em cada exercício.

---

> **Aviso:** Este arquivo contém a teoria necessária para o módulo. Leia com atenção antes de começar os exercícios. Se algo não fizer sentido, revise os exemplos ou pergunte ao mentor.
