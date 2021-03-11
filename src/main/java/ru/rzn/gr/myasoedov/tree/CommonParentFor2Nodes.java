package ru.rzn.gr.myasoedov.tree;

public class CommonParentFor2Nodes {

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
        int b = 25;
        Node node = commonNode(tree, 5, 6);
        System.out.println(node.value == 5);

        node = commonNode(tree, 5, 10);
        System.out.println(node.value == 10);

        node = commonNode(tree, 5, 20);
        System.out.println(node.value == 20);

        node = commonNode(tree, 35, 25);
        System.out.println(node.value == 30);

        node = commonNode(tree, 6, 25);
        System.out.println(node.value == 20);

    }

    public static Node commonNode(Node node, int a, int b) {
        FindResult findResult = find(node, new FindResult(), a, b);
        if (findResult.isNotAll()) {
            System.out.println("no solution");
            return null;
        }
        while (true) {
            findResult = new FindResult();
            findResult.hasA = a == node.value;
            findResult.hasB = b == node.value;
            boolean rootValue = findResult.isPartial();
            findResult = find(node.left, findResult, a, b);
            if (findResult.isPartial() || (rootValue && findResult.isAll())) {
                return node;
            }

            if (findResult.hasA) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
    }


    private static FindResult find(Node node, FindResult result, int a, int b) {
        if (node == null) return result;

        if (node.left != null) {
            find(node.left, result, a, b);
        }

        if (node.value == a) {
            result.hasA = true;
        }
        if (node.value == b) {
            result.hasB = true;
        }
        if (result.hasA && result.hasB) {
            return result;
        }

        if (node.right != null) {
            find(node.right, result, a, b);
        }
        return result;
    }

    static class FindResult {
        boolean hasA;
        boolean hasB;

        boolean isNotAll() {
            return !hasA && !hasB;
        }
        boolean isAll() {
            return hasA && hasB;
        }
        boolean isPartial() {
            return hasA ^ hasB;
        }

        @Override
        public String toString() {
            return "FindResult{" +
                    "hasA=" + hasA +
                    ", hasB=" + hasB +
                    '}';
        }
    }
}
