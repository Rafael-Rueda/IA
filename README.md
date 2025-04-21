# 🧠 Inteligência Artificial

Este repositório é dedicado aos meus estudos e experimentos em Inteligência Artificial, organizados por linguagem, abordagem e tema. Aqui será centralizado todo o conhecimento adquirido ao longo do tempo, desde algoritmos clássicos até projetos mais avançados com aprendizado de máquina.

---

## 📁 Estrutura do Projeto

---

## 🧩 Módulos

### 📦 `Agents - Java`
Implementações em Java de agentes inteligentes, começando por **Agentes Reflexivos Simples**, como o clássico **Agente do Aspirador de Pó**.

- `Agents/`: Lógica de decisão dos agentes.
- `Environments/`: Ambientes onde os agentes operam.
- `Main.java`: Arquivo principal para executar e testar os agentes.

> Inspirado em conceitos do livro *"Artificial Intelligence: A Modern Approach"*.

---

### 📦 `Search Algorithms - Java`
Este diretório reunirá implementações dos principais **algoritmos de busca**, utilizados para tomada de decisões em ambientes com ou sem conhecimento completo.

Embora **não sejam agentes completos**, esses algoritmos são **componentes fundamentais da IA clássica**, sendo usados por agentes que precisam planejar ações.

Algoritmos previstos:

- Busca em Largura (BFS)
- Busca em Profundidade (DFS)
- Busca de Custo Uniforme (UCS)
- Busca A*
- Busca Gulosa
- Busca Bidirecional

> Todos são algoritmos **determinísticos**, **não estocásticos**, e **sem aprendizado** — ou seja, **não são machine learning**.

---

### 📦 `Machine Learning - Python`
Este diretório será dedicado a algoritmos de aprendizado de máquina, incluindo:

- Regressão
- Classificação
- Clustering
- Redes Neurais
- Projetos com PyTorch

## 🧪 Objetivo

Construir uma base sólida em IA, com foco em:

- Entendimento de conceitos fundamentais
- Implementações práticas
- Preparação para projetos reais e estudos mais avançados

---

## 🚀 Execução

Para rodar os agentes em Java:

```bash
# Compile
javac dev/rueda/Agents/SimpleReflexAgents/**/*.java

# Execute
java dev.rueda.Agents.SimpleReflexAgents.Main
```