package ru.rzn.gr.myasoedov;

import java.util.HashMap;

public class ReduceString {
    static HashMap<String, String> dictionary;

    static {
        dictionary = new HashMap<>();
        dictionary.put("ab", "c");
        dictionary.put("ba", "c");
        dictionary.put("ac", "b");
        dictionary.put("ca", "b");
        dictionary.put("cb", "a");
        dictionary.put("bc", "a");
    }

    public static String StringReduction1(String str) {
        if (str == null) {
            return "0";
        }
        if (str.length() < 2) {
            return String.valueOf(str.length());
        }
        for (int i = 0; i < str.length() - 1; i++) {
            String s1 = String.valueOf(str.charAt(i));
            String s2 = String.valueOf(str.charAt(i + 1));
            String key = s1 + s2;
            String reduce = dictionary.get(key);
            if (reduce != null) {
                return StringReduction1(str.substring(0, i) + reduce + str.substring(i + 2));
            }
        }
        return String.valueOf(str.length());
    }

    /*public static String StringReduction2(String str) {
        if (str == null) {
            return "0";
        }
        if (str.length() < 2) {
            return String.valueOf(str.length());
        }
        Optional<String> reduce1 = str.codePoints()
                .mapToObj(c -> String.valueOf((char) c))
                .reduce((s1, s2) -> {
                    String key = s1 + s2;
                    String reduce = dictionary.get(key);
                    if (reduce != null) {
                        return reduce;
                    } else {
                        return key;
                    }
                });
        return String.valueOf(str.length());
    }*/

    public static void main(String[] args) {
        String cccc = StringReduction1("abcabc");
        System.out.println(cccc);
    }

    //abcabc - 6
    //ccabc - 5
    //cbbc - 4
    //abc - 3
    //cc - 2
}
