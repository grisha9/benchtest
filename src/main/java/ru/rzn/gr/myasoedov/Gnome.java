package ru.rzn.gr.myasoedov;

import java.util.Arrays;
import java.util.List;

/**
 * Created by grisha on 11.05.20.
 */
public class Gnome {
    public static void main(String[] args) {
        List<Integer> gnome = Arrays.asList(1, 0, 1, 0, 1, 0, 1, 0, 1, 1);
        boolean isOneEven = true;
        for (int i = 0; i < gnome.size(); i++) {
            boolean currentOneEven = isOneEven(gnome.subList(i + 1, gnome.size()));
            boolean result = isOneEven ^ currentOneEven;
            isOneEven = currentOneEven;
            System.out.println(result + " " + (result == (gnome.get(i) == 1)));
        }
    }

    private static boolean isOneEven(List<Integer> gnome) {
        int reduce = gnome.stream().mapToInt(i -> i).reduce(0, (x, y) -> x ^ y);
        return reduce == 0;
    }
}
