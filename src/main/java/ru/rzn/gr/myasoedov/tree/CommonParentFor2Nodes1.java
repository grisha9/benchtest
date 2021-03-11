package ru.rzn.gr.myasoedov.tree;

public class CommonParentFor2Nodes1 {

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
    public static void main(String[] args) {
        Node tree = Node.createTree();

        int a = 5;
        int b = 6;

        if (!covers(tree, a) || !covers(tree, b)) {
            return;
        }
        Node node = commonNode(tree, a, b);
        System.out.println(node);

        node = commonNode(tree, 5, 6);
        System.out.println(node.value == 5);

        node = commonNode(tree, 5, 20);
        System.out.println(node.value == 20);

        node = commonNode(tree, 35, 25);
        System.out.println(node.value == 30);

        node = commonNode(tree, 6, 25);
        System.out.println(node.value == 20);
    }


    private static boolean covers(Node node, int value) {
        if (node == null) return false;

        if (node.value == value) {
            return true;
        }
        return covers(node.left, value) || covers(node.right, value);
    }

    static Node commonNode(Node node, int a, int b) {
        if (node == null) return null;
        if (node.value == a && node.value == b) return node;

        boolean isA = covers(node.left, a);
        boolean isB = covers(node.left, b);
        if (isA != isB) {
            return node;
        }
        Node next = isA ? node.left : node.right;
        return commonNode(next, a, b);
    }
}
