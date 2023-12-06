package at.klimo.aoc.y2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solution6Test {

    final String[] input = new String[]{
        "Time:      7  15   30",
        "Distance:  9  40  200"
    };

    @Test
    void testP1() {
        var s = new Solution6();
        var result = s.solveP1(s.mapInput(input));
        assertEquals(288, result);
    }

    @Test
    void testP2() {
        var s = new Solution6();
        var result = s.solveP2(s.mapInput(input));
        assertEquals(71503, result);
    }
}

