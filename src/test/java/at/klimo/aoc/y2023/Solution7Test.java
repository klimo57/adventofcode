package at.klimo.aoc.y2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solution7Test {

    static String[] input = new String[]{
        "32T3K 765",
        "T55J5 684",
        "KK677 28",
        "KTJJT 220",
        "QQQJA 483",
    };

    @Test
    void solveP1() {
        var s = new Solution7();
        var result = s.solveP1(s.mapInput(input));
        assertEquals(6440L, result);
    }

    @Test
    void solveP2() {
        var s = new Solution7();
        var result = s.solveP2(s.mapInput(input));
        assertEquals(5905L, result);
    }
}
