package at.klimo.aoc.y2023;

import at.klimo.aoc.AOCInputSource;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solution18Test {

    Solution18 solution = new Solution18();

    @ParameterizedTest
    @AOCInputSource(
        values = {
            """
                R 6 (#70c710)
                D 5 (#0dc571)
                L 2 (#5713f0)
                D 2 (#d2c081)
                R 2 (#59c680)
                D 2 (#411b91)
                L 5 (#8ceee2)
                U 2 (#caa173)
                L 1 (#1b58a2)
                U 2 (#caa171)
                R 2 (#7807d2)
                U 3 (#a77fa3)
                L 2 (#015232)
                U 2 (#7a21e3)
                """
        },
        expectedType = Integer.class,
        expectationsAsInt = {62}
    )
    void testP1(String[] input, int expected) {
        var result = solution.solveP1(solution.mapInput(input));
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @AOCInputSource(
        values = {
            """
                R 6 (#70c710)
                D 5 (#0dc571)
                L 2 (#5713f0)
                D 2 (#d2c081)
                R 2 (#59c680)
                D 2 (#411b91)
                L 5 (#8ceee2)
                U 2 (#caa173)
                L 1 (#1b58a2)
                U 2 (#caa171)
                R 2 (#7807d2)
                U 3 (#a77fa3)
                L 2 (#015232)
                U 2 (#7a21e3)
                """
        },
        expectedType = Integer.class,
        expectationsAsInt = {-1}
    )
    void testP2(String[] input, int expected) {
        var result = solution.solveP2(solution.mapInput(input));
        assertEquals(expected, result);
    }
}
