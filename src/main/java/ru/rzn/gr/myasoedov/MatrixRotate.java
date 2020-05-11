package ru.rzn.gr.myasoedov;

import java.util.Arrays;

/**
 * Created by grisha on 19.01.20.
 */
public class MatrixRotate {
    public static void main(String[] args) {

        int[][] matrix = {
                {0, -1, 0, 6, 0, 1},
                {0, -2, 0, 7, 0, 2},
                {0, -3, 0, 8, 0, 3},
                {0, -4, 0, 9, 0, 4},
                {0, -5, 0, 10, 0, 5},
                {0, -6, 0, 11, 0, 6},
        };
//
//        int[][] matrix = {
//                {1, 1, 1, 1},
//                {0, 0, 0, 0},
//                {1, 1, 1, 1},
//                {0, 0, 0, 0},
//        };


//        int[][] matrix = {
//                {1, 2, 3, 4},
//                {0, 0, 0, 0},
//                {5, 6, 7, 8},
//                {0, 0, 0, 0},
//        };

//              {0, 5, 0, 1},
//              {0, 6, 0, 2},
//              {0, 7, 0, 3},
//              {0, 8, 0, 4},

        /*
        1,1 -> 1,4
        1,4 -> 4,4
        4,4 -> 4,1
        4,1 -> 1,1

        1,2 -> 2,4
        -2,4 -> 4,3
        -4,3 -> 3,1
        -3,1 -> 1,2
         */
        int cycleCount = matrix.length / 2;
        for (int i = 0; i < cycleCount; i++) {
            int end = matrix.length - i - 1;
            for (int j = i; j < end; j++) {
                swap(matrix, i, end, j - i);
            }
        }
        for (int[] ints : matrix) {
            System.out.println(Arrays.toString(ints));
        }
    }

    private static void swap(int[][] matrix, int start, int end, int offset) {
        int temp = matrix[start][start + offset];
        matrix[start][start + offset] = matrix[end - offset][start];
        matrix[end - offset][start] = matrix[end][end - offset];
        matrix[end][end - offset] = matrix[start + offset][end];
        matrix[start + offset][end] = temp;
    }
}
