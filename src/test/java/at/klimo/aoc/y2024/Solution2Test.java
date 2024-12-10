package at.klimo.aoc.y2024;

import at.klimo.aoc.AOCInputSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;

public class Solution2Test {

    Solution2 solution = new Solution2();

    @ParameterizedTest
    @AOCInputSource(
            values = {
                    """
                        7 6 4 2 1
                        1 2 7 8 9
                        9 7 6 2 1
                        1 3 2 4 5
                        8 6 4 4 1
                        1 3 6 7 9
                        """
            },
            expectationsAsLong = {2L}
    )
    void testP1(String[] input, long expected) {
        var result = solution.solveP1(solution.mapInput(input));
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @AOCInputSource(
            values = {
                    """
                        7 6 4 2 1
                        1 2 7 8 9
                        9 7 6 2 1
                        1 3 2 4 5
                        8 6 4 4 1
                        1 3 6 7 9
                        15 16 18 22 19
                        """
            },
            expectationsAsLong = {5L}
    )
    void testP2(String[] input, long expected) {
        var result = solution.solveP2(solution.mapInput(input));
        Assertions.assertEquals(expected, result);
    }
}
