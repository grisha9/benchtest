package ru.rzn.gr.myasoedov;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NumberRangeCount {
    private static class NumberInterval {
        final int start;
        int end = 0;

        private NumberInterval(int number) {
            this.start = this.end = number;
        }

        void inc() {
            end++;
        }

        @Override
        public String toString() {
            String string = start == end ? String.valueOf(start) : start + "-" + end;
            return "[" + string + "]";
        }
    }

    public static void main(String[] args) {
        String data = "1246799";
        if (data.length() < 2) {
            System.out.println(data);
            return;
        }
        String result = "";
        NumberInterval range = null;
        List<NumberInterval> intervals = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            int symbol = Integer.parseInt(String.valueOf(data.charAt(i)));
            if (range == null || symbol > range.end + 1) {
                range = new NumberInterval(symbol);
                intervals.add(range);
            } else {
                range.end = symbol;
            }
        }
        System.out.println(intervals.stream().map(NumberInterval::toString).collect(Collectors.joining()));
    }
}
