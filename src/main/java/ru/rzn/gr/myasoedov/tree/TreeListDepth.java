package ru.rzn.gr.myasoedov.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TreeListDepth {

    public static void main(String[] args) {
        Node tree = Node.createTree();

        printNodes(tree);
        List<List<Node>> result = new ArrayList<>();
        fillListNodes(tree, result, 0);
        System.out.println(result);
    }

    private static void printNodes(Node node) {
        if (node == null) return;
        if (node.left != null) {
            printNodes(node.left);
        }
        System.out.println(node.value);
        if (node.right != null) {
            printNodes(node.right);
        }
    }

    private static void fillListNodes(Node node, List<List<Node>> result, int depth) {
        if (node == null) return;
        if(result.size() <= depth) {
            result.add(depth, new LinkedList<>());
        }

        int nextDepth = depth + 1;
        if (node.left != null) {
            fillListNodes(node.left, result, nextDepth);
        }

        List<Node> list = result.get(depth);
        list.add(node);
        if (node.right != null) {
            fillListNodes(node.right, result, nextDepth);
        }
    }
}
