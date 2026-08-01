# Módulo 1 — Arrays e loops

## Objetivo

Aprender a percorrer arrays, acessar índices, acumular valores, contar elementos e entender as complexidades `O(n)` e `O(n²)`.

Este é o módulo fundamental. Todos os módulos seguintes dependem de loops e manipulação de arrays. Se você dominar este módulo, o restante da trilha será muito mais fluido.

---

## Explicação conceitual

### Arrays

Um array é uma sequência de elementos do mesmo tipo armazenados em posições consecutivas de memória. Cada posição é acessada por um índice numérico.

```
índices:  0    1    2    3    4
valores: [10,  20,  30,  40,  50]
```

- O primeiro elemento está no índice `0`.
- O último elemento está no índice `tamanho - 1`.
- O tamanho de um array em Java é fixo e acessado por `.length`.

### Loops

Um loop (ou laço) permite repetir um bloco de código várias vezes. Os mais comuns em Java:

#### `for` tradicional

```java
for (int i = 0; i < array.length; i++) {
    System.out.println(array[i]);
}
```

Use quando precisar do índice (ex: comparar posições, modificar elementos).

#### `for-each` (enhanced for)

```java
for (int valor : array) {
    System.out.println(valor);
}
```

Use quando precisar apenas do valor, sem se importar com o índice.

### Percorrer um array

É a operação mais básica: visitar cada elemento uma vez. Custa `O(n)`.

```java
int[] numeros = {4, 7, 2, 9};

for (int i = 0; i < numeros.length; i++) {
    // faz algo com numeros[i]
}
```

### Acumular

Significa manter uma variável que agrega valores conforme você percorre o array.

```java
int soma = 0;
for (int n : numeros) {
    soma = soma + n;
}
```

### Contar

Similar a acumular, mas você soma `1` quando uma condição é satisfeita.

```java
int contador = 0;
for (int n : numeros) {
    if (n > 10) {
        contador++; // contador = contador + 1
    }
}
```

### Buscar

Significa percorrer o array até encontrar um elemento. Pode custar `O(n)` no pior caso.

```java
for (int i = 0; i < numeros.length; i++) {
    if (numeros[i] == alvo) {
        return i; // encontrou
    }
}
return -1; // não encontrou
```

### Dois loops aninhados

Quando colocamos um loop dentro do outro, o custo é `O(n²)`. É comum em problemas de força bruta que comparam pares de elementos.

```java
for (int i = 0; i < numeros.length; i++) {
    for (int j = 0; j < numeros.length; j++) {
        // compara numeros[i] com numeros[j]
    }
}
```

### Inverter um array

Significa trocar a posição dos elementos, de modo que o primeiro vá para o final, o segundo para a penúltima posição, e assim por diante.

Entrada: `[1, 2, 3, 4]`
Saída:  `[4, 3, 2, 1]`

### Verificar se um array está ordenado

Percorra comparando cada elemento com o próximo. Se em algum momento `array[i] > array[i+1]`, o array não está ordenado.

### Encontrar maior e menor

Inicialize uma variável com o primeiro elemento do array. Compare com todos os outros. Atualize quando encontrar um valor maior (ou menor).

---

## Representação passo a passo

### Exemplo: somar os elementos de `[3, 7, 2]`

```
início:  total = 0
índice 0: valor 3  →  total = 0 + 3 = 3
índice 1: valor 7  →  total = 3 + 7 = 10
índice 2: valor 2  →  total = 10 + 2 = 12
fim:      total = 12
```

### Exemplo: encontrar o maior em `[3, 7, 2, 9, 4]`

```
maior = 3   (assume o primeiro)
3 vs 7  →  maior = 7
7 vs 2  →  maior = 7
7 vs 9  →  maior = 9
9 vs 4  →  maior = 9
resultado: 9
```

---

## Erros comuns

| Erro | Consequência | Como evitar |
|---|---|---|
| Esquecer que índice começa em `0` | Acessar posição errada | Sempre começar `i = 0` |
| Usar `<=` no lugar de `<` | `ArrayIndexOutOfBoundsException` | Condição: `i < array.length` |
| Confundir `.length` com `.length()` | Erro de compilação | Arrays usam `.length`, strings usam `.length()` |
| Esquecer de inicializar o acumulador | Valor indefinido ou 0 errado | Sempre inicializar: `int soma = 0` |
| Modificar o array sem querer | Efeitos colaterais inesperados | Criar um novo array se precisar manter o original |
| Não tratar array vazio | `ArrayIndexOutOfBoundsException` ao acessar índice `0` | Verificar `if (array.length == 0)` |
| Usar `return` dentro do loop cedo demais | O loop só executa uma vez | Colocar `return` fora do loop, após percorrer tudo |

---

## Complexidade de tempo

| Operação | Complexidade |
|---|---|
| Acessar por índice (`array[i]`) | `O(1)` |
| Percorrer todo o array | `O(n)` |
| Dois loops aninhados (todos contra todos) | `O(n²)` |
| Busca linear | `O(n)` pior caso, `O(1)` melhor caso |

### O que significa O(n)?

Significa que o tempo de execução cresce proporcionalmente ao tamanho da entrada. Se você tem 10 elementos, faz ~10 operações. Se tem 1000 elementos, faz ~1000 operações.

### O que significa O(n²)?

Significa que o tempo cresce quadraticamente. Para 10 elementos, ~100 operações. Para 1000 elementos, ~1.000.000 operações.

### O que significa O(1)?

Significa que o tempo é constante, independente do tamanho da entrada. Acessar `array[5]` é `O(1)` — não importa se o array tem 10 ou 10 milhões de elementos.

---

## Complexidade de espaço

| Operação | Complexidade |
|---|---|
| Variáveis auxiliares (`int soma = 0`) | `O(1)` |
| Criar um novo array do mesmo tamanho | `O(n)` |

A maioria dos exercícios deste módulo espera `O(1)` de espaço extra (apenas variáveis auxiliares).

---

## Sinais de que um problema usa este padrão

- "Dado um array..."
- "Percorra..."
- "Encontre o maior/menor..."
- "Conte quantos..."
- "Retorne a soma..."
- "Verifique se o array..."
- O enunciado não menciona estruturas mais avançadas.

---

## Lista planejada de exercícios

| # | Título | Dificuldade | Foco |
|---|---|---|---|
| 01 | Somar todos os elementos | Fundamento | Acumular com loop |
| 02 | Contar números pares | Fundamento | Acumular com condição |
| 03 | Encontrar o maior número | Fundamento | Comparar durante o loop |
| 04 | Encontrar o menor número | Fundamento | Similar ao 03, inverte condição |
| 05 | Verificar se um valor existe | Fácil | Busca linear |
| 06 | Retornar o índice de um valor | Fácil | Busca com retorno de posição |
| 07 | Inverter um array | Fácil | Manipular ordem dos elementos |
| 08 | Contar ocorrências de um número | Fácil | Contagem condicional |
| 09 | Verificar se o array está ordenado | Fácil intermediário | Comparar elementos consecutivos |
| 10 | Encontrar dois números com soma-alvo (força bruta) | Desafio do módulo | Dois loops, raciocínio de pares |

---

## Problemas do LeetCode relacionados

Após concluir este módulo, você estará preparado para enfrentar:

- **Two Sum** (Easy) — usando força bruta (a versão com HashMap virá no módulo 3)
- **Find the Index of the First Occurrence in a String** (Easy) — usa busca linear
- **Remove Element** (Easy) — manipulação de índices
- **Remove Duplicates from Sorted Array** (Easy) — comparação de elementos consecutivos

Mas não se precipite: resolva os exercícios preparatórios primeiro.

---

## Critérios para considerar o módulo concluído

- [ ] Sabe percorrer um array com `for` e `for-each`.
- [ ] Sabe acumular valores (somar, contar).
- [ ] Sabe encontrar maior e menor elemento.
- [ ] Sabe inverter um array.
- [ ] Sabe verificar se um valor existe e retornar seu índice.
- [ ] Sabe identificar se um array está ordenado.
- [ ] Consegue escrever dois loops aninhados para comparar pares.
- [ ] Entende a diferença entre `O(n)`, `O(n²)` e `O(1)`.
- [ ] Trata array vazio corretamente em cada exercício.

---

> **Aviso:** Este arquivo contém a teoria necessária para o módulo. Leia com atenção antes de começar os exercícios. Se algo não fizer sentido, revise os exemplos ou pergunte ao mentor.
