package ru.rzn.gr.myasoedov;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SymbolCount {
    private static class SymbolCounter {
        final char symbol;
        int count = 0;

        private SymbolCounter(char symbol) {
            this.symbol = symbol;
            count = 1;
        }

        void inc() {
            count++;
        }

        @Override
        public String toString() {
            return count > 1 ? String.valueOf(symbol) + count : String.valueOf(symbol);
        }
    }

    public static void main(String[] args) {
        String data = "AAAAAAAAASSDFFFRRETTYYYYU";
        if (data.length() < 2) {
            System.out.println(data);
            return;
        }
        char[] chars = data.toCharArray();
        SymbolCounter counter = null;
        List<SymbolCounter> intervals = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            if (counter == null || chars[i] != counter.symbol) {
                counter = new SymbolCounter(chars[i]);
                intervals.add(counter);
            } else {
                counter.inc();
            }
        }
        System.out.println(intervals.stream().map(SymbolCounter::toString).collect(Collectors.joining()));
    }
}
