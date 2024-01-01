package at.klimo.aoc.y2023;

import at.klimo.aoc.AOCInputSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solution21Test {

    Solution21 solution;

    @BeforeEach
    void setup() {
        this.solution = new Solution21();
    }

    @ParameterizedTest
    @AOCInputSource(
        values = {
            """
                ...........
                .....###.#.
                .###.##..#.
                ..#.#...#..
                ....#.#....
                .##..S####.
                .##..#...#.
                .......##..
                .##.#.####.
                .##..##.##.
                ...........
                """
        },
        expectedType = Integer.class,
        expectationsAsInt = {16}
    )
    void testP1(String[] input, int expected) {
//        solution.steps = 6;
        var result = solution.solveP1(solution.mapInput(input));
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @AOCInputSource(
        values = {},
        expectedType = Integer.class,
        expectationsAsInt = {}
    )
    void testP2(String[] input, int expected) {
        var result = solution.solveP2(solution.mapInput(input));
        assertEquals(expected, result);
    }
}
