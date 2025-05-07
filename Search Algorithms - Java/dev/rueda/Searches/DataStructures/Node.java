package dev.rueda.Searches.DataStructures;

public class Node<T> {
    T value;
    Node<T> left;
    Node<T> right;

    Node(T value) {
        this.value = value;
        this.left = this.right = null;
    }
}
