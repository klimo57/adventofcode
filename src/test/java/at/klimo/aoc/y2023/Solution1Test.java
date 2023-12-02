package at.klimo.aoc.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Solution1Test {

    @Test
    void testForExampleInput() {
        String result = new Solution1().puzzle1(new String[]{
            "1abc2",
            "pqr3stu8vwx",
            "a1b2c3d4e5f",
            "treb7uchet"
        });
        assertEquals("142", result);
    }

    @Test
    void testForSecondPuzzle() {
        String result = new Solution1().puzzle1(new String[]{
            "two1nine",
            "eightwothree",
            "abcone2threexyz",
            "xtwone3four",
            "4nineeightseven2",
            "zoneight234",
            "7pqrstsixteen"
        });
        assertEquals("281", result);
    }

    @ParameterizedTest
    @MethodSource("provideStringsForCalibration")
    void testSingleLines(String input, int expected) {
        assertEquals(expected, new Solution1.UncalibratedLine(input).calibrate(input, 0, input.length() - 1));
    }

    private static Stream<Arguments> provideStringsForCalibration() {
        return Stream.of(
            Arguments.of("two1nine", 29),
            Arguments.of("eightwothree", 83),
            Arguments.of("abcone2threexyz", 13),
            Arguments.of("xtwone3four", 24),
            Arguments.of("4nineeightseven2", 42),
            Arguments.of("zoneight234", 14),
            Arguments.of("7pqrstsixteen", 76)
        );
    }
}
