package at.klimo.aoc.y2023;

import at.klimo.aoc.AOCInputSource;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solution9Test {

    Solution9 solution = new Solution9();

    @ParameterizedTest
    @AOCInputSource(
        values = {
            """
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45    
                """
        },
        expectationsAsLong = {114L}
    )
    void testP1(String[] input, long expected) {
        var result = solution.solveP1(solution.mapInput(input));
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @AOCInputSource(
        values = {
            """
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45    
                """
        },
        expectationsAsLong = {2L}
    )
    void testP2(String[] input, long expected) {
        var result = solution.solveP2(solution.mapInput(input));
        assertEquals(expected, result);
    }
}
