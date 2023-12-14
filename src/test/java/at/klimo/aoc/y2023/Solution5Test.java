package at.klimo.aoc.y2023;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solution5Test {

    final String[] input = new String[]{
        "seeds: 79 14 55 13",
        "",
        "seed-to-soil map:",
        "50 98 2",
        "52 50 48",
        "",
        "soil-to-fertilizer map:",
        "0 15 37",
        "37 52 2",
        "39 0 15",
        "",
        "fertilizer-to-water map:",
        "49 53 8",
        "0 11 42",
        "42 0 7",
        "57 7 4",
        "",
        "water-to-light map:",
        "88 18 7",
        "18 25 70",
        "",
        "light-to-temperature map:",
        "45 77 23",
        "81 45 19",
        "68 64 13",
        "",
        "temperature-to-humidity map:",
        "0 69 1",
        "1 0 69",
        "",
        "humidity-to-location map:",
        "60 56 37",
        "56 93 4",
    };

    @Test
    @Order(1)
    void testP1() {
        var s = new Solution5();
        var result = s.solveP1(s.mapInput(input));
        assertEquals(35L, result);
    }

    @Test
    @Order(2)
    void testP2() {
        var s = new Solution5();
        var result = s.solveP2(s.mapInput(input));
        assertEquals(46L, result);
    }

//    @Test
//    @Order(3)
//    void testCrazySolution() {
//        var s = new Solution5();
//        var crazyResult = s.crazyEasySolutionForP2(s.mapInput(input));
//        assertEquals(46L, crazyResult);
//    }

//    @ParameterizedTest(name = "crazy solution")
//    @MethodSource("readInputAsStringArray")
//    @Order(4)
//    void testCrazySolutionWithRealInput(String[] input) {
//        var s = new Solution5();
//        var crazyResult = s.crazyEasySolutionForP2(s.mapInput(input));
//        assertEquals(104070862L, crazyResult);
//    }

    private static Stream<Arguments> readInputAsStringArray() throws IOException {
        try (var in = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("input.in")))) {
            return Stream.of(Arguments.of(new String[][]{in.lines().toArray(String[]::new)}));
        }
    }
}
