package ru.rzn.gr.myasoedov.tree;

public class IsBalanceTree {
    private static int prevMin = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Node tree = Node.createTree();

        ResultHolder resultHolder = new ResultHolder();
        boolean result = walkOnTree(tree, 0, resultHolder);
        System.out.println(result);
        Result2Holder result2Holder = new Result2Holder();
        walkOnTree2(tree, 0, result2Holder);
        System.out.println(result2Holder.isBalance());

        tree = Node.createBSTree();

        resultHolder = new ResultHolder();
        result = walkOnTree(tree, 0, resultHolder);
        System.out.println(result);
        result2Holder = new Result2Holder();
        walkOnTree2(tree, 0, result2Holder);
        System.out.println(result2Holder.isBalance());
    }

    private static boolean walkOnTree(Node node, int index, ResultHolder result) {
        int nextIndex = index + 1;
        if (node.left != null) {
            boolean b = walkOnTree(node.left, nextIndex, result);
            if (!b) {
                return false;
            }
        }
        if (node.left == null && node.right == null) {
            boolean add = result.add(index);
            if (add && !result.isPartial() && !result.isBalance()) {
                return false;
            } else if (!add && !result.isBalance(index)) {
                return false;
            }
        }
        if (node.right != null) {
            boolean b =walkOnTree(node.right, nextIndex, result);
            if (!b) {
                return false;
            }
        }
        return true;
    }

    private static void walkOnTree2(Node node, int index, Result2Holder result) {
        int nextIndex = index + 1;
        if (node.left != null) {
            walkOnTree2(node.left, nextIndex, result);
        }
        if (node.left == null && node.right == null) {
            result.min = Math.min(result.min, index);
            result.max = Math.max(result.max, index);
        }
        if (node.right != null) {
            walkOnTree2(node.right, nextIndex, result);
        }
    }

    private static class ResultHolder {
        Integer first;
        Integer second;

        private boolean isPartial() {
            return first == null || second == null;
        }

        private boolean add(int value) {
            if (first == null) {
                first = value;
                return true;
            }
            if (second == null) {
                second = value;
                return true;
            }
            return false;
        }

        private boolean isBalance() {
            return Math.abs(first - second) < 2;
        }

        private boolean isBalance(int value) {
            return first == value || second == value;
        }

        @Override
        public String toString() {
            return "ResultHolder{" +
                    "first=" + first +
                    ", second=" + second +
                    '}';
        }
    }

    private static class Result2Holder {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        private boolean isBalance() {
            return Math.abs(min - max) < 2;
        }

        @Override
        public String toString() {
            return "Result2Holder{" +
                    "min=" + min +
                    ", max=" + max +
                    '}';
        }
    }
}
