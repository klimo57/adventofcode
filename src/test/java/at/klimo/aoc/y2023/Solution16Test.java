package at.klimo.aoc.y2023;

import at.klimo.aoc.AOCInputSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;

public class Solution16Test {

    Solution16 solution = new Solution16();

    @ParameterizedTest
    @AOCInputSource(
        values = {
            """
                .|...\\....
                |.-.\\.....
                .....|-...
                ........|.
                ..........
                .........\\
                ..../.\\\\..
                .-.-/..|..
                .|....-|.\\
                ..//.|....
                """
        },
        expectedType = Integer.class,
        expectationsAsInt = {46}
    )
    void testP1(String[] input, int expected) {
        var result = solution.solveP1(solution.mapInput(input));
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @AOCInputSource(
        values = {
            """
                .|...\\....
                |.-.\\.....
                .....|-...
                ........|.
                ..........
                .........\\
                ..../.\\\\..
                .-.-/..|..
                .|....-|.\\
                ..//.|....
                """
        },
        expectedType = Integer.class,
        expectationsAsInt = {51}
    )
    void testP2(String[] input, int expected) {
        var result = solution.solveP2(solution.mapInput(input));
        Assertions.assertEquals(expected, result);
    }
}
