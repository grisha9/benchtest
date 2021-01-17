package ru.rzn.gr.myasoedov;

public class Levenstane {

    public static int levenstain(String str1, String str2) {
        int[] Di_1 = new int[str2.length() + 1];
        int[] Di = new int[str2.length() + 1];

        for (int j = 0; j <= str2.length(); j++) {
            Di[j] = j; // (i == 0)
        }

        for (int i = 1; i <= str1.length(); i++) {
            System.arraycopy(Di, 0, Di_1, 0, Di_1.length);

            Di[0] = i; // (j == 0)
            char c1 = str1.charAt(i - 1);
            for (int j = 1; j <= str2.length(); j++) {
                char c2 = str2.charAt(j - 1);
                int cost = (c1 != c2) ? 1 : 0;
                int min = min(
                        Di_1[j] + 1,
                        Di_1[j - 1] + cost,
                        Di[j - 1] + 1
                );
                Di[j] = min;
            }
        }

        return Di[Di.length - 1];
    }

    private static int min(int n1, int n2, int n3) {
        return Math.min(Math.min(n1, n2), n3);
    }

    public static void main(String[] args) {
        System.out.println(levenstain("POLYNOM", "PORYN"));
    }
}
