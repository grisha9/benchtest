package ru.rzn.gr.myasoedov.tree;

public class IsBinaryTree {
    private static int prevMin = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Node tree = Node.createTree();

        boolean result = checkBST(tree);
        System.out.println(result);
    }

    private static boolean checkBST(Node node) {
        if (node == null) {
            return true;
        }
        if (!checkBST(node.left)) {
            return false;
        }
        if (node.value > prevMin) {
            prevMin = node.value;
        } else {
            return false;
        }
        if (!checkBST(node.right)) {
            return false;
        }
        return true;
    }
}
