package at.klimo.aoc.y2024;

import at.klimo.aoc.AOCInputSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;

class Solution1Test {

    Solution1 solution = new Solution1();

    @ParameterizedTest
    @AOCInputSource(
            values = {
                    """
                        3   4
                        4   3
                        2   5
                        1   3
                        3   9
                        3   3
                        """
            },
            expectedType = Integer.class,
            expectationsAsInt = {11}
    )
    void testP1(String[] input, int expected) {
        var result = solution.solveP1(solution.mapInput(input));
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @AOCInputSource(
            values = {
                    """
                        3   4
                        4   3
                        2   5
                        1   3
                        3   9
                        3   3
                        """
            },
            expectedType = Integer.class,
            expectationsAsInt = {31}
    )
    void testP2(String[] input, int expected) {
        var result = solution.solveP2(solution.mapInput(input));
        Assertions.assertEquals(expected, result);
    }
}
