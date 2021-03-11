package ru.rzn.gr.myasoedov;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AntonovRegexp {
    public static boolean test(String string, String regexp) {
        List<Lexema> lexemas = getRegexp(regexp);
        PositionState positionState = new PositionState();
        Stack<PositionState> savePoint = new Stack<>();

        Lexema lexema = getNext(lexemas, positionState.lexema++);
        Character next;

        while (lexema != null) {
            switch (lexema.type) {
                case GREEDY:
                    next = getNext(string, positionState.source++);
                    if (next == null) {
                        return false;
                    }
                    if (next == lexema.symbol) {
                        savePoint.push(new PositionState(positionState.source, positionState.lexema - 1));
                        positionState.source--;
                        lexema = getNext(lexemas, positionState.lexema++);
                    }
                    break;
                case REPEAT:
                    next = getNext(string, positionState.source++);
                    if (next == null || next != lexema.symbol) {
                        positionState.source--;
                        lexema = getNext(lexemas, positionState.lexema++);
                    }
                    break;
                case ONE:
                    next = getNext(string, positionState.source++);
                    if (next == null || next != lexema.symbol) {
                        if (!savePoint.isEmpty()) {
                            positionState = savePoint.pop();
                        } else {
                            return false;
                        }
                    }
                    lexema = getNext(lexemas, positionState.lexema++);
                    break;
                default:
                    next = getNext(string, positionState.source++);
                    if (next == null) {
                        if (!savePoint.isEmpty()) {
                            positionState = savePoint.pop();
                        } else {
                            return false;
                        }
                    }
                    lexema = getNext(lexemas, positionState.lexema++);
                    break;
            }
        }
        return getNext(string, positionState.source) == null;
    }

    private static Character getNext(String string, int position) {
        if (position < string.length()) {
            return string.charAt(position);
        }
        return null;
    }

    private static Lexema getNext(List<Lexema> lexemas, int position) {
        if (position < lexemas.size()) {
            return lexemas.get(position);
        }
        return null;
    }

    private static List<Lexema> getRegexp(String regexp) {
        return greedyLexemas(parseRegexp(regexp));
    }

    static List<Lexema> parseRegexp(String regexp) {
        List<Lexema> lexemas = new ArrayList<>();
        Lexema prev;
        Lexema current;
        for (int i = 0; i < regexp.length(); i++) {
            char symbol = regexp.charAt(i);
            prev = lexemas.isEmpty() ? null : lexemas.get(lexemas.size() - 1);

            if (symbol == '*' && prev != null) {
                current = new Lexema(prev.symbol, LexemaType.REPEAT);
            } else if (symbol == '.') {
                current = new Lexema(symbol, LexemaType.ANY);
            } else {
                current = new Lexema(symbol, LexemaType.ONE);
            }
            if (prev != null && prev.type == LexemaType.REPEAT && current.type != LexemaType.REPEAT
                    && prev.symbol == current.symbol) {
                prev.type = current.type;
                current.type = LexemaType.REPEAT;
            }
            if (prev == null || !(prev.type == LexemaType.REPEAT && current.type == LexemaType.REPEAT)) {
                lexemas.add(current);
            }

        }
        return lexemas;
    }

    static List<Lexema> greedyLexemas(List<Lexema> lexemas) {
        Lexema prev = null;
        for (Lexema lexema : lexemas) {
            if (prev != null) {
                if (lexema.type == LexemaType.ONE && prev.type == LexemaType.REPEAT && prev.symbol == '.') {
                    prev.type = LexemaType.GREEDY;
                    prev.symbol = lexema.symbol;
                }
            }
            prev = lexema;
        }
        return lexemas;
    }

    static class Lexema {
        char symbol;
        LexemaType type;


        Lexema(char symbol, LexemaType type) {
            this.symbol = symbol;
            this.type = type;
        }

        @Override
        public String toString() {
            return "Lexema{" +
                    "symbol=" + symbol +
                    ", type=" + type +
                    '}';
        }
    }

    static class PositionState {
        int source;
        int lexema;

        PositionState() {}

        PositionState(PositionState positionState) {
            source = positionState.source;
            lexema = positionState.lexema;
        }

        public PositionState(int source, int lexema) {
            this.source = source;
            this.lexema = lexema;
        }

        @Override
        public String toString() {
            return "PositionState{" +
                    "source=" + source +
                    ", lexema=" + lexema +
                    '}';
        }
    }

    enum LexemaType {
        ONE, ANY, REPEAT, GREEDY
    }

    public static void main(String[] args) {
        Stack<Integer> list = new Stack<>();
        list.push(1);
        list.push(2);
        System.out.println(list.pop());
        System.out.println(list.pop());
    }
}
