package at.klimo.aoc.y2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solution4Test {

    final String[] input = new String[]{
        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11",
    };

    @Test
    void testP1() {
        var result = new Solution4().solveP1(input);
        assertEquals(13, result);
    }

    @Test
    void testP2() {
        var result = new Solution4().solveP2(input);
        assertEquals(30, result);
    }
}
