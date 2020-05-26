package ru.rzn.gr.myasoedov;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by grisha on 26.05.20.
 */
public class MinTwoMultiply {
    public static void main(String[] args) {
        System.out.println(multiply(Arrays.asList(5, 4, 6, 2, 3))); //6
        System.out.println(multiply(Arrays.asList(5, 4, 6, 2, 0, -3)));//-18
        System.out.println(multiply(Arrays.asList(-5, -4, -6, -2, -3)));//6
        System.out.println(multiply(Arrays.asList(-50, -10, 0, 1, 2, 3)));//-150
        System.out.println(multiply(Arrays.asList(-50, - 10, 1, 2, 3)));//-150
        System.out.println(multiply(Arrays.asList(-50, -10, 1)));//-50
        System.out.println(multiply(Arrays.asList(-50, -10, -5)));//50
        System.out.println(multiply(Arrays.asList(-50, 1, 2, 3)));//-150
    }

    private static int multiply(List<Integer> data) {
        Collections.sort(data);
        boolean positiveExist = false;
        boolean negativeExist = false;
        for (Integer aData : data) {
            if (aData < 0 && !negativeExist) {
                negativeExist = true;
            }
            if (aData >= 0 && !positiveExist) {
                positiveExist = true;
            }
        }

        if (!negativeExist && positiveExist) {
            return data.get(0) * data.get(1);
        } else if (negativeExist && !positiveExist) {
            return data.get(data.size() - 2) * data.get(data.size() - 1);
        } else {
            return data.get(0) * data.get(data.size() - 1);
        }

    }
}
