package dev.rueda.Searches.DataStructures;

public class NodeWithFlag<T> {
    public Node<T> node;
    public int flag;

    public NodeWithFlag(Node<T> node, int flag) {
        this.node = node;
        this.flag = flag;
    }
}
