package ru.rzn.gr.myasoedov;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Created by grisha on 26.05.20.
 */
public class MinTwoMultiply {
    public static void main(String[] args) {
        System.out.println(multiply(Arrays.asList(5, 4, 6, 2, 3))); //6
        System.out.println(multiply(Arrays.asList(5, 4, 6, 2, 1, -3)));//-3
        System.out.println(multiply(Arrays.asList(-5, -4, -6, -2, -3, -7)));//6
        System.out.println(multiply(Arrays.asList(-50, -10, 0, 1, 2, 3)));//0
        System.out.println(multiply(Arrays.asList(-50, -10, 1, 2, 3)));//-10
        System.out.println(multiply(Arrays.asList(-50, -10, 1)));//-10
        System.out.println(multiply(Arrays.asList(-50, -10, -5)));//50
        System.out.println(multiply(Arrays.asList(-50, 1, 2, 3)));//-50
    }

    private static int multiply(List<Integer> data) {
        Collections.sort(data);
        Integer positiveMin = null;
        Integer negativeMax = null;
        for (Integer number : data) {
            if (number < 0 && (negativeMax == null || negativeMax < number)) {
                negativeMax = number;
            }
            if (number >= 0 && (positiveMin == null || positiveMin > number)) {
                positiveMin = number;
            }
        }

        if (isNull(negativeMax) && !isNull(positiveMin)) {
            return data.get(0) * data.get(1);
        } else if (!isNull(negativeMax) && isNull(positiveMin)) {
            return data.get(data.size() - 2) * data.get(data.size() - 1);
        } else if (isNull(negativeMax) && isNull(positiveMin)) {
            throw new UnsupportedOperationException();
        } else{
            return negativeMax * positiveMin;
        }

    }
}
