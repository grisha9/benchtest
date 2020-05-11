package ru.rzn.gr.myasoedov;

/**
 * Created by grisha on 25.04.20.
 */
public class TestScob {
    public static void generate(String cur, int open, int closed, int n) {
        if (cur.length() == 2 * n) {
            System.out.println(cur);
            return;
        }
        if (open < n) {
            generate(cur + '(', open + 1, closed, n);
        }
        if (closed < open) {
            generate(cur + ')', open, closed + 1, n);
        }
    }

    public static void main(String[] args) {
        generate("", 0, 0, 3);
    }
}
