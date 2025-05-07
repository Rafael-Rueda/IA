package dev.rueda.Searches.DataStructures;

import java.util.LinkedList;
import java.util.Comparator;
import java.util.Queue;

public class BinarySearchTree<T> {
    Node<T> root;
    Node<T> delimiter = null;

    LinkedList<Node<T>> nodes;
    Comparator<T> comparator;

    public BinarySearchTree(Comparator<T> comparator) {
        this.root = null;
        this.comparator = comparator;
    }

    public void insert(T value) {
        insertRecursive(this.root, value);
    }

    private void insertRecursive(Node<T> current, T value) {
        if (current == null) {
            this.root = new Node<>(value);
            return;
        }

        int cmp = comparator.compare(value, current.value);

        if (cmp < 0) {
            if (current.left == null) {
                current.left = new Node<>(value);
            } else {
                insertRecursive(current.left, value);
            }
        } else if (cmp > 0) {
            if (current.right == null) {
                current.right = new Node<>(value);
            } else {
                insertRecursive(current.right, value);
            }
        }
    }

    public void printTree() {
        printRecursive(this.root);
        System.out.println();
        printStructure(this.root);
    }

    private void printRecursive(Node<T> node) {
        if (node != null) {
            printRecursive(node.left);
            System.out.print(node.value + " - ");
            printRecursive(node.right);
        }
    }

    private void printStructure(Node<T> node) {
        if (node == null) return;

        // 0 = Left
        // 1 = Right
        // -1 = root
        Queue<NodeWithFlag<T>> qu = new LinkedList<>();

        qu.add(new NodeWithFlag<>(node, -1));
        qu.add(new NodeWithFlag<>(this.delimiter, -1));


        while (true) {
            NodeWithFlag<T> curr = qu.poll();
            if (curr.node != this.delimiter) {
                if (qu.peek().node == this.delimiter) {
                    System.out.print(" ".repeat(this.root.value.toString().length() * getNodeLevel(this.root, this.root, 0)));
                }
                System.out.print(curr.node.value);
                if (curr.node.left != null) qu.add(new NodeWithFlag<>(curr.node.left, 0));
                if (curr.node.right != null) qu.add(new NodeWithFlag<>(curr.node.right, 1));
            } else {
                System.out.println();
                if (qu.peek() != null) qu.add(new NodeWithFlag<>(this.delimiter, -1));
                else break;
            }
        }
    }

    private int getHeight(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private int getNodeLevel(Node<T> current, Node<T> target, int level) {
        if (current == null) return 0;
        if (current == target) {
            return getSubtreeHeight(this.root) - level + 1;
        }

        int leftLevel = getNodeLevel(current.left, target, level + 1);
        if (leftLevel != 0) return leftLevel;

        return getNodeLevel(current.right, target, level + 1);
    }

    private int getSubtreeHeight(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(getSubtreeHeight(node.left), getSubtreeHeight(node.right));
    }
}

