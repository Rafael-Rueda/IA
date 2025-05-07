package dev.rueda.Searches.BestFirstSearch.Agents;

import java.util.*;

class Node {
    int position;
    int h; // heurística: distância até a sujeira

    Node(int position, int goal) {
        this.position = position;
        this.h = Math.abs(goal - position);
    }
}

class NodeComparator implements Comparator<Node> {
    public int compare(Node a, Node b) {
        return Integer.compare(a.h, b.h);
    }
}

public class SimpleVacuum {
    public static void main(String[] args) {
        int start = 0;   // posição do robô (S)
        int goal = 2;    // posição da sujeira (D)
        int[] world = {0, 1, 2}; // posições 0 a 2

        bestFirstSearch(start, goal, world);
    }

    public static void bestFirstSearch(int start, int goal, int[] world) {
        boolean[] visited = new boolean[world.length];
        PriorityQueue<Node> queue = new PriorityQueue<>(new NodeComparator());

        queue.add(new Node(start, goal));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (visited[current.position]) continue;
            visited[current.position] = true;

            System.out.println("Visitando posição: " + current.position);

            if (current.position == goal) {
                System.out.println("Sujeira encontrada!");
                return;
            }

            // mover para a esquerda
            if (current.position - 1 >= 0)
                queue.add(new Node(current.position - 1, goal));

            // mover para a direita
            if (current.position + 1 < world.length)
                queue.add(new Node(current.position + 1, goal));
        }

        System.out.println("Sujeira não encontrada.");
    }
}

