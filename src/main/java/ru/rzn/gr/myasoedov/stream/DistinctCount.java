package ru.rzn.gr.myasoedov.stream;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by grisha on 02.06.20.
 */
public class DistinctCount {
    private static <T> Predicate<T> distinct(int count) {
        ConcurrentHashMap<T, Long> map = new ConcurrentHashMap<>();
        return t -> map.merge(t, 1L, (a, b) -> a + b) == count;
    }

    private static <T, TT> Function<T, Stream<TT>> select(Class<TT> clazz) {
        return e -> clazz.isInstance(e) ? Stream.of(clazz.cast(e)) : Stream.empty();
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("a1", "a2", "a3", "a1", "a4", "a5", "a1", "a2", "a6");
        list.stream().filter(distinct(2)).forEach(System.out::println);

        List<? extends Number> numbers = Arrays.asList(1, 2.2, 5L, 1.1);
        List<Double> collect = numbers.stream()
                .flatMap(select(Double.class))
                .collect(Collectors.toList());
        System.out.println(collect);
    }
}
