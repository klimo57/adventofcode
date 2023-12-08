package at.klimo.aoc.y2023;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solution8Test {

    @ParameterizedTest
    @MethodSource("getInputsP1")
    void testP1(String[] input, long expected) {
        var s = new Solution8();
        var result = s.solveP1(s.mapInput(input));
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("getInputsP2")
    void testP2(String[] input, long expected) {
        var s = new Solution8();
        var result = s.solveP2(s.mapInput(input));
        assertEquals(expected, result);
    }

    private static Stream<Arguments> getInputsP1() {
        return Stream.of(
            Arguments.of(input1, 2L),
            Arguments.of(input2, 6L)
        );
    }

    private static Stream<Arguments> getInputsP2() {
        return Stream.of(
            Arguments.of(input3, 6L)
        );
    }

    static String[] input1 = new String[]{
        "RL",
        "",
        "AAA = (BBB, CCC)",
        "BBB = (DDD, EEE)",
        "CCC = (ZZZ, GGG)",
        "DDD = (DDD, DDD)",
        "EEE = (EEE, EEE)",
        "GGG = (GGG, GGG)",
        "ZZZ = (ZZZ, ZZZ)",
    };

    static String[] input2 = new String[]{
        "LLR",
        "",
        "AAA = (BBB, BBB)",
        "BBB = (AAA, ZZZ)",
        "ZZZ = (ZZZ, ZZZ)",
    };

    static String[] input3 = new String[]{
        "LR",
        "",
        "11A = (11B, XXX)",
        "11B = (XXX, 11Z)",
        "11Z = (11B, XXX)",
        "22A = (22B, XXX)",
        "22B = (22C, 22C)",
        "22C = (22Z, 22Z)",
        "22Z = (22B, 22B)",
        "XXX = (XXX, XXX)",
    };
}
