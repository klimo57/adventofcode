package at.klimo.aoc.y2023;

import at.klimo.aoc.AOCInputSource;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solution17Test {

    Solution17 solution = new Solution17();

    @ParameterizedTest
    @AOCInputSource(
        values = {
            """
                2413432311323
                3215453535623
                3255245654254
                3446585845452
                4546657867536
                1438598798454
                4457876987766
                3637877979653
                4654967986887
                4564679986453
                1224686865563
                2546548887735
                4322674655533
                """
        },
        expectedType = Integer.class,
        expectationsAsInt = {102}
    )
    void testP1(String[] input, int expected) {
        var result = solution.solveP1(solution.mapInput(input));
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @AOCInputSource(
        values = {

        },
        expectedType = Integer.class,
        expectationsAsInt = {}
    )
    void testP2(String[] input, int expected) {
        var result = solution.solveP2(solution.mapInput(input));
        assertEquals(expected, result);
    }
}
