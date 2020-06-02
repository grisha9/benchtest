package ru.rzn.gr.myasoedov.stream;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by grisha on 02.06.20.
 */
public class FastFindAny {
    private static class FoundException extends RuntimeException {
        private final Object value;

        private FoundException(Object value) {
            super("", null, false, false);
            this.value = value;
        }

        public Object getValue() {
            return value;
        }
    }

    private static <T> Optional<T> fastFindAny(Stream<T> stream) {
        try {
            stream.forEach(x -> {
                throw new FoundException(x);
            });
            return Optional.empty();
        } catch (FoundException e) {
            @SuppressWarnings({"uncheked"})
            T value = (T) e.getValue();
            return Optional.of(value);
        }
    }

    public static void main(String[] args) {
        Optional<Integer> integer = fastFindAny(IntStream.rangeClosed(0, 1_000_000)
                .boxed()
                .filter(x -> x == 1_000_000));

        System.out.println(integer);

        Optional<Integer> any = IntStream.of(5)
                .flatMap(x -> IntStream.range(0, x))
                .boxed()
                .filter(x -> {
                    System.out.println(x);
                    return x == 0;
                })
                .findAny();
        any = IntStream.range(0, 5)
                .boxed()
                .filter(x -> {
                    System.out.println(x);
                    return x == 0;
                })
                .findAny();

        integer = fastFindAny(IntStream.of(1_000_000).flatMap(x -> IntStream.range(0, x))
                .boxed()
                .filter(x -> x == 1));
        System.out.println(integer);
    }

}
