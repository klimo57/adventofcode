package at.klimo.aoc.y2024;

import at.klimo.aoc.AOCInputSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;

public class Solution3Test {

    Solution3 solution = new Solution3();

    @ParameterizedTest
    @AOCInputSource(
            values = {
                    "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
            },
            expectationsAsLong = {161L}
    )
    void testP1(String[] input, long expected) {
        var result = solution.solveP1(solution.mapInput(input));
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @AOCInputSource(
            values = {
                    "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
            },
            expectationsAsLong = {48L}
    )
    void testP2(String[] input, long expected) {
        var result = solution.solveP2(solution.mapInput(input));
        Assertions.assertEquals(expected, result);
    }
}
