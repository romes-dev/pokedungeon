# 🎮 PokéDungeon — Gabarito do Professor

RPG de batalha por turnos no terminal, desenvolvido como gabarito do exercício de
**Programação Orientada a Objetos em Java (nível iniciante)**.

---

## 📁 Estrutura do Projeto

```
src/
├── Main.java                       # Ponto de entrada
├── personagens/
│   ├── Personagem.java             # Classe abstrata base
│   ├── Heroi.java                  # Estende Personagem
│   ├── Guerreiro.java              # Estende Heroi — Golpe Devastador
│   ├── Mago.java                   # Estende Heroi — Bola de Fogo
│   ├── Arqueiro.java               # Estende Heroi — Tiro Certeiro
│   └── Monstro.java                # Estende Personagem — fábrica estática
├── batalha/
│   └── Batalha.java                # Loop de batalha por turnos
├── dungeon/
│   └── Dungeon.java                # Progressão de salas + boss
└── util/
    └── EntradaUsuario.java         # Scanner encapsulado + validação
```

---

## ⚙️ Como Compilar e Executar

Requisito: **Java 11+** instalado.

### 1. Compilar

```bash
# Na pasta raiz do projeto (onde está a pasta src/)
javac -d out -sourcepath src src/Main.java
```

### 2. Executar

```bash
java -cp out Main
```

### Script rápido (Linux/macOS)
```bash
mkdir -p out && javac -d out -sourcepath src src/Main.java && java -cp out Main
```

### Script rápido (Windows)
```bat
mkdir out
javac -d out -sourcepath src src/Main.java
java -cp out Main
```

---

## 🎯 Conceitos OOP Demonstrados

| Conceito          | Onde aparece                                         |
|-------------------|------------------------------------------------------|
| Abstração         | `Personagem` (classe abstrata + método `atacar()`)   |
| Encapsulamento    | Todos os atributos `private` com getters/setters     |
| Herança           | `Heroi extends Personagem`, subclasses extends `Heroi` |
| Polimorfismo      | `atacar()` e `usarHabilidade()` sobrescritos por cada classe |
| Composição        | `Batalha` usa `Heroi` + `Monstro`; `Dungeon` usa `Batalha` |
| Fábrica estática  | `Monstro.criarAleatorio()`, `criarBoss()` etc.       |

---

## 🎁 Bônus Implementados

- **B01** — Sistema de XP e nível: herói sobe de nível ao acumular 50 XP (+5 ATK, +10 HP máx)
- **B02** — Itens de cura: 50% de chance de encontrar poção após cada batalha (+30 HP)
- **B03** — Salvar placar: resultado gravado em `placar.txt` via `FileWriter`
- **B04** — IA adaptativa: monstros com < 30% de HP tentam fugir (40% de chance)

---

## 🔑 Decisões de Design

- **`Personagem` abstrata**: força que toda subclasse implemente `atacar()`,
  garantindo o contrato polimórfico.
- **`EntradaUsuario` utilitária**: encapsula `Scanner` e validação num único lugar,
  evitando repetição e facilitando testes.
- **`Monstro.criarAleatorio()`**: padrão Factory Method simplificado, adequado
  ao nível iniciante, sem exigir interfaces.
- **Queimadura em `Batalha.java`**: efeito de estado do Mago processado no
  motor de batalha, não no personagem, evitando dependência circular entre pacotes.
