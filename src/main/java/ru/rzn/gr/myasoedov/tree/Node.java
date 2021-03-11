package ru.rzn.gr.myasoedov.tree;

public class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    public Node(int value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    /*@Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", left=" + left +
                ", right=" + right +
                '}';
    }*/

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    /**
     *           20
     *        /     \
     *       10     30
     *      / \     /\
     *     5  15  25  35
     *    / \
     *   0  8
     *     / \
     *    6  9
     */
    public static Node createTree() {
        Node node6 = new Node(6);
        Node node9 = new Node(9);

        Node node0 = new Node(0);
        Node node8 = new Node(8, node6, node9);

        Node node5 = new Node(5, node0, node8);
        Node node15 = new Node(15);
        Node node25 = new Node(25);
        Node node35 = new Node(35);

        Node node10 = new Node(10, node5, node15);
        Node node30 = new Node(30, node25, node35);

        return new Node(20, node10, node30);
    }

    /**
     *           20
     *        /     \
     *       10     30
     *      / \     /\
     *     5  15  25  35
     *    / \
     *   0
     */
    public static Node createBSTree() {

        Node node0 = new Node(0);

        Node node5 = new Node(5, node0, null);
        Node node15 = new Node(15);
        Node node25 = new Node(25);
        Node node35 = new Node(35);

        Node node10 = new Node(10, node5, node15);
        Node node30 = new Node(30, node25, node35);

        return new Node(20, node10, node30);
    }
}
