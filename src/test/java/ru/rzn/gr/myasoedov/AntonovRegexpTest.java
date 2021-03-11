package ru.rzn.gr.myasoedov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.rzn.gr.myasoedov.AntonovRegexp.parseRegexp;

class AntonovRegexpTest {

    @Test
    void parseRegexpTest1() {
        List<AntonovRegexp.Lexema> lexemas = parseRegexp("a*b*b*c*cccc*c");
        Assertions.assertEquals("a*bb*cccccc*", lexemasToString(lexemas));
    }

    @Test
    void parseRegexpTest2() {
        List<AntonovRegexp.Lexema> lexemas = parseRegexp("a*.*.*c*");
        Assertions.assertEquals("a*..*c*", lexemasToString(lexemas));
    }

    @Test
    void parseRegexpTestAndDelimiter1() {
        List<AntonovRegexp.Lexema> lexemas = parseRegexp("a*.*.*c*");
        lexemas = AntonovRegexp.greedyLexemas(lexemas);
        Assertions.assertEquals("a*..*(c)c*", lexemasToString(lexemas));
    }

    @Test
    void parseRegexpTestAndDelimiter2() {
        List<AntonovRegexp.Lexema> lexemas = parseRegexp("a*b*b*c*");
        lexemas = AntonovRegexp.greedyLexemas(lexemas);
        Assertions.assertEquals("a*bb*c*", lexemasToString(lexemas));
    }

    @Test
    void parse1() {
        boolean result = AntonovRegexp.test("aabbc", "a*b*b*c*");
        Assertions.assertTrue(result);
    }

    @Test
    void parse2() {
        boolean result = AntonovRegexp.test("zxcvab", ".*ab");
        Assertions.assertTrue(result);
    }

    @Test
    void parse3() {
        boolean result = AntonovRegexp.test("zxcvaab", ".*ab");
        Assertions.assertTrue(result);
    }

    @Test
    void parse4() {
        boolean result = AntonovRegexp.test("zab", ".*ab");
        Assertions.assertTrue(result);
    }

    @Test
    void parse5() {
        boolean result = AntonovRegexp.test("zaczaxzavzatab", ".*ab");
        Assertions.assertTrue(result);
    }

    @Test
    void parse6() {
        boolean result = AntonovRegexp.test("zacab", ".*ab");
        Assertions.assertTrue(result);
    }

    @Test
    void parseFail1() {
        boolean result = AntonovRegexp.test("aabc", "a*b*b*c*");
        Assertions.assertFalse(result);
    }

    @Test
    void parseFail2() {
        boolean result = AntonovRegexp.test("zxcvac", ".*ab");
        Assertions.assertFalse(result);
    }

    private static String lexemasToString(Collection<AntonovRegexp.Lexema> lexemas) {
        return lexemas.stream()
                .map(AntonovRegexpTest::getRepeatString)
                .collect(Collectors.joining());
    }

    private static String getRepeatString(AntonovRegexp.Lexema l) {
        if (l.type == AntonovRegexp.LexemaType.REPEAT) {
            return "*";
        } else if (l.type == AntonovRegexp.LexemaType.GREEDY) {
            return String.format("*(%s)", l.symbol);
        } else {
            return String.valueOf(l.symbol);
        }
    }
}