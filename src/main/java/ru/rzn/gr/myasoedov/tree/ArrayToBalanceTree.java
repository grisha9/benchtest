package ru.rzn.gr.myasoedov.tree;

import java.util.Arrays;

public class ArrayToBalanceTree {
    public static void main(String[] args) {
        int[] a1 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] a2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Node bst = createBST(a1);
        System.out.println(bst);
        bst = createBST(a2);
        System.out.println(bst);

        bst = createBSTBook(a1, 0, a1.length - 1);
        System.out.println(bst);
        bst = createBSTBook(a2, 0, a1.length);
        System.out.println(bst);
    }

    private static Node createBST(int[] array) {
        if (array.length == 0) {
            return null;
        }
        if (array.length == 1) {
            int value = array[0];
            return new Node(value);
        }

        int medium = array.length / 2;
        Node mediumNode = new Node(array[medium]);
        int[] left = Arrays.copyOfRange(array, 0, medium);
        int[] right = Arrays.copyOfRange(array, medium + 1, array.length);
        mediumNode.left = createBST(left);
        mediumNode.right = createBST(right);
        return mediumNode;
    }

    private static Node createBSTBook(int[] array, int start, int end) {
        if (end < start) {
            return null;
        }

        int medium = (start + end) / 2;
        Node mediumNode = new Node(array[medium]);
        mediumNode.left = createBSTBook(array, start, medium - 1);
        mediumNode.right = createBSTBook(array, medium + 1, end);
        return mediumNode;
    }
}
