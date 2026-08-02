# Módulo 2 — Strings

## Objetivo

Aprender a manipular strings em Java: acessar caracteres, percorrer, comparar, extrair partes e entender a imutabilidade e o uso de {@code StringBuilder} para construção eficiente.

Este módulo constrói sobre a base de loops do Módulo 1, aplicando as mesmas operações (percorrer, contar, acumular, inverter) agora em sequências de caracteres.

---

## Explicação conceitual

### O que é uma String?

Uma {@code String} é uma sequência de caracteres. Em Java, {@code String} é uma classe imutável: uma vez criada, seu conteúdo nunca muda. Qualquer operação que "modifica" uma string na verdade cria uma nova.

```java
String nome = "Java";          // literal — mais comum
String outro = new String("Java"); // construtor — raramente necessário
```

### Por que imutabilidade importa?

Quando você faz:

```java
String s = "ola";
s = s + " mundo";
```

O que acontece de verdade:

1. `"ola"` é criado na memória.
2. `" mundo"` é criado na memória.
3. `"ola mundo"` é criado como uma terceira string.
4. `s` passa a apontar para `"ola mundo"`.
5. As strings `"ola"` e `" mundo"` continuam existindo (até o garbage collector removê-las).

Isso significa que concatenar strings dentro de um loop é **ineficiente**:

```java
// INEFICIENTE — O(n²) no tempo por causa das cópias
String resultado = "";
for (int i = 0; i < 1000; i++) {
    resultado = resultado + "x"; // cria uma nova string a cada iteração
}
```

### StringBuilder

Para construir strings incrementalmente, use {@code StringBuilder}:

```java
// EFICIENTE — O(n) no tempo
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append("x"); // modifica o buffer interno, sem criar novas strings
}
String resultado = sb.toString();
```

| Operação | String | StringBuilder |
|---|---|---|
| Concatenar no loop | `O(n²)` | `O(n)` |
| Acesso por índice | `charAt(i)` — `O(1)` | `charAt(i)` — `O(1)` |
| Modificar após criado | Não (imutável) | Sim (mutável) |

Quando usar {@code StringBuilder}:

- Construir uma string caractere por caractere (ex: inverter, filtrar).
- Concatenar dentro de um loop.
- Quando o número de concatenações for grande ou desconhecido.

Quando usar concatenação simples (`+`):

- Poucas concatenações fixas (ex: `"nome: " + nome`).
- Fora de loops.

---

## Métodos principais de String

### `length()`

Retorna o número de caracteres da string (inclui espaços).

```java
String s = "Java";
s.length(); // 4
```

**Atenção:** arrays usam `.length` (atributo), strings usam `.length()` (método).

### `charAt(int indice)`

Retorna o caractere na posição especificada. O índice começa em `0`.

```java
String s = "Java";
s.charAt(0); // 'J'
s.charAt(3); // 'a'
```

```
índices:  0    1    2    3
          J    a    v    a
```

Lança {@code StringIndexOutOfBoundsException} se o índice for negativo ou >= `length()`.

### Percorrer uma string caractere por caractere

```java
String s = "hello";
for (int i = 0; i < s.length(); i++) {
    char c = s.charAt(i);
    System.out.println(c);
}
```

### `equals(Object outro)`

Compara o conteúdo de duas strings. **Sempre use `equals()` para comparar strings, nunca `==`.**

```java
String a = "java";
String b = new String("java");

a.equals(b);  // true  — compara conteúdo
a == b;       // false — compara referências (objetos diferentes na memória)
```

### `equalsIgnoreCase(String outra)`

Compara ignorando maiúsculas/minúsculas.

```java
"Java".equalsIgnoreCase("jAVA"); // true
```

### `toLowerCase()` / `toUpperCase()`

Retorna uma nova string com todos os caracteres convertidos.

```java
"Java".toLowerCase(); // "java"
"Java".toUpperCase(); // "JAVA"
```

**Lembrete:** essas operações criam novas strings — a original não é alterada.

### `indexOf(String busca)`

Retorna o índice da primeira ocorrência da substring, ou `-1` se não encontrar.

```java
"banana".indexOf("na");  // 2
"banana".indexOf("xy");  // -1
```

Também existe `indexOf(char c)` para buscar um caractere.

### `substring(int inicio, int fim)`

Retorna uma nova string com os caracteres do índice `inicio` (inclusivo) até `fim` (exclusivo).

```java
"hello".substring(1, 4); // "ell"
"hello".substring(2);    // "llo" (do índice 2 até o final)
```

```
índices:  0    1    2    3    4
          h    e    l    l    o
substring(1, 4): [1, 4) = e, l, l → "ell"
```

### `trim()`

Remove espaços do início e do fim.

```java
"  ola  ".trim();      // "ola"
```

### `replace(char antigo, char novo)`

Substitui todas as ocorrências de um caractere por outro.

```java
"banana".replace('a', 'o'); // "bonono"
```

### `toCharArray()`

Converte a string em um array de caracteres.

```java
char[] letras = "abc".toCharArray(); // ['a', 'b', 'c']
```

Útil quando você precisa modificar caracteres (ex: inverter a string no próprio array).

---

## Comparando caracteres

Caracteres em Java (`char`) são tipos primitivos e podem ser comparados com `==`:

```java
char c = 'a';
if (c == 'a') { ... }        // OK — tipos primitivos
if (c == 'A') { ... }        // OK — compara valor numérico Unicode
```

Para ignorar maiúsculas/minúsculas na comparação de caracteres:

```java
// Opção 1: converter a string inteira antes
String texto = "Java";
String minusculo = texto.toLowerCase();
char c = minusculo.charAt(i);
if (c == 'a') { ... }

// Opção 2: usar Character.toLowerCase()
char c = texto.charAt(i);
if (Character.toLowerCase(c) == 'a') { ... }
```

---

## Representação passo a passo

### Exemplo: contar quantas vezes 'a' aparece em `"banana"`

```
índice 0: 'b' → 'b' == 'a'? não → contador = 0
índice 1: 'a' → 'a' == 'a'? sim → contador = 1
índice 2: 'n' → 'n' == 'a'? não → contador = 1
índice 3: 'a' → 'a' == 'a'? sim → contador = 2
índice 4: 'n' → 'n' == 'a'? não → contador = 2
índice 5: 'a' → 'a' == 'a'? sim → contador = 3

resultado: 3
```

### Exemplo: inverter `"abc"` com StringBuilder

```
início:      sb = ""
i = 2 (c):   sb = "c"
i = 1 (b):   sb = "cb"
i = 0 (a):   sb = "cba"
toString:    "cba"
```

---

## Erros comuns

| Erro | Consequência | Como evitar |
|---|---|---|
| Usar `==` para comparar strings | Resultado incorreto | Usar `equals()` |
| Esquecer que `length()` é método, não atributo | Erro de compilação | `s.length()` para strings, `array.length` para arrays |
| Acessar índice além do tamanho | `StringIndexOutOfBoundsException` | Condição: `i < s.length()` |
| Concatenar strings dentro de loop com `+` | Código lento (`O(n²)`) | Usar `StringBuilder` |
| Achar que `toLowerCase()` modifica a original | String original permanece igual | Atribuir o retorno: `s = s.toLowerCase()` |
| Esquecer de tratar string vazia | `charAt(0)` lança exceção | Verificar `if (s.isEmpty())` |
| Usar `substring` com índices trocados | Resultado inesperado ou exceção | `inicio` sempre < `fim` |

---

## Complexidade de tempo

| Operação | Complexidade | Por quê? |
|---|---|---|
| `charAt(i)` | `O(1)` | Acesso direto ao array interno de chars |
| `length()` | `O(1)` | O tamanho é armazenado como campo |
| `equals(s)` | `O(n)` | Compara caractere por caractere |
| `indexOf(c)` | `O(n)` | Percorre até encontrar |
| `substring(i, j)` | `O(n)` (Java 6-) / `O(1)` (Java 7+) | Depende da versão; na prática, trate como `O(k)` onde k = j-i |
| `toLowerCase()` | `O(n)` | Precisa percorrer todos os caracteres |
| Concatenar com `+` no loop | `O(n²)` | Cada concatenação copia tudo de novo |
| Concatenar com `StringBuilder` | `O(n)` | Buffer interno redimensiona sob demanda |

---

## Complexidade de espaço

| Operação | Complexidade |
|---|---|
| `toLowerCase()`, `substring()`, etc. | `O(n)` — criam nova string |
| `StringBuilder` | `O(n)` — armazena o conteúdo construído |
| Usar apenas `charAt()` para leitura | `O(1)` — nenhuma string nova é criada |

---

## Sinais de que um problema usa este padrão

- "Dada uma string..."
- "Conte quantas vogais/consoantes..."
- "Inverta a string..."
- "Verifique se é palíndromo..."
- "Encontre a primeira ocorrência de..."
- "Substitua todos os..."
- O enunciado envolve caracteres e texto.

---

## Relação com o Módulo 1

Todas as operações de loop que você aprendeu no Módulo 1 se aplicam aqui:

| Módulo 1 (arrays) | Módulo 2 (strings) |
|---|---|
| `array.length` | `s.length()` |
| `array[i]` | `s.charAt(i)` |
| Percorrer com `for` / `for-each` | Percorrer com `for` (+ `charAt`) |
| Acumular, contar, buscar | Mesmo padrão, aplicado a caracteres |

A diferença principal é que arrays são mutáveis (você pode alterar `array[i] = novoValor`), enquanto strings são imutáveis (você precisa criar uma nova string ou usar `StringBuilder`).

---

## Lista planejada de exercícios

| # | Título | Dificuldade | Foco |
|---|---|---|---|
| 01 | Contar vogais | Fundamento | `charAt()`, `toLowerCase()` |
| 02 | Inverter uma string | Fundamento | `StringBuilder`, percorrer de trás para frente |
| 03 | Verificar palíndromo | Fácil | Dois ponteiros, `equals()` |
| 04 | Contar ocorrências de um caractere | Fácil | Loop + condição, parâmetro adicional |
| 05 | Encontrar primeira ocorrência | Fácil | `indexOf()` manual, busca linear |
| 06 | Extrair substring entre índices | Fácil | `substring()`, validação de limites |
| 07 | Substituir caractere | Fácil | Construir nova string condicionalmente |
| 08 | Contar palavras | Intermediário | Identificar transições espaço/palavra |
| 09 | Remover caracteres duplicados consecutivos | Intermediário | `StringBuilder`, comparar com anterior |
| 10 | Maior prefixo comum | Intermediário | Índices, comparação caractere a caractere |
| 11 | Frequência de caracteres | Intermediário | `toCharArray()`, array de contagem (prepara HashMap) |
| 12 | `trim()` manual | Intermediário | Identificar índices de início e fim sem espaços |
| 13 | Valid Palindrome (LeetCode 125) | Desafio | Dois ponteiros, ignora não-alfanumérico, case-insensitive |

---

## Problemas do LeetCode relacionados

Após concluir este módulo, você estará preparado para enfrentar:

- **Valid Palindrome** (Easy) — palíndromo ignorando caracteres não alfanuméricos
- **Reverse String** (Easy) — inversão de array de caracteres
- **First Unique Character in a String** (Easy) — contagem de frequência (prepara para HashMap no Módulo 3)
- **Longest Common Prefix** (Easy) — prefixo comum
- **Length of Last Word** (Easy) — manipulação de palavras

---

## Critérios para considerar o módulo concluído

- [ ] Sabe percorrer uma string com `charAt()` e `length()`.
- [ ] Sabe usar `toLowerCase()` e `toUpperCase()`.
- [ ] Sabe comparar strings com `equals()` (e não `==`).
- [ ] Sabe usar `substring()` com índices corretos.
- [ ] Sabe construir strings eficientemente com `StringBuilder`.
- [ ] Entende que strings são imutáveis.
- [ ] Sabe quando concatenar com `+` e quando usar `StringBuilder`.
- [ ] Consegue resolver problemas de palíndromo, inversão e contagem de caracteres.
- [ ] Trata string vazia corretamente em cada exercício.

---

> **Aviso:** Este arquivo contém a teoria necessária para o módulo. Leia com atenção antes de começar os exercícios. Se algo não fizer sentido, revise os exemplos ou pergunte ao mentor.
