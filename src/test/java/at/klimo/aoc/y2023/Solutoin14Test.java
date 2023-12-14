package at.klimo.aoc.y2023;

import at.klimo.aoc.AOCInputSource;
import at.klimo.aoc.util.CharacterMatrix;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solutoin14Test {

    Solution14 solution = new Solution14();

    @ParameterizedTest
    @AOCInputSource(
        values = {
            """
                O....#....
                O.OO#....#
                .....##...
                OO.#O....O
                .O.....O#.
                O.#..O.#.#
                ..O..#O..O
                .......O..
                #....###..
                #OO..#....
                """
        },
        expectedType = Integer.class,
        expectationsAsInt = {136}
    )
    void testP1(String[] input, int expected) {
        var result = solution.solveP1(solution.mapInput(input));
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @AOCInputSource(
        values = {
            """
                O....#....
                O.OO#....#
                .....##...
                OO.#O....O
                .O.....O#.
                O.#..O.#.#
                ..O..#O..O
                .......O..
                #....###..
                #OO..#....
                """
        },
        expectedType = Integer.class,
        expectationsAsInt = {64}
    )
    void testP2(String[] input, int expected) {
        var result = solution.solveP2(solution.mapInput(input));
        assertEquals(expected, result);
    }

    @Test
    void testTiltNorth() {
        var result = Solution14.tilt(new CharacterMatrix("""
                O....#....
                O.OO#....#
                .....##...
                OO.#O....O
                .O.....O#.
                O.#..O.#.#
                ..O..#O..O
                .......O..
                #....###..
                #OO..#....
                """.lines().toArray(String[]::new)));
        assertEquals(result, new CharacterMatrix(
            """
                OOOO.#.O..
                OO..#....#
                OO..O##..O
                O..#.OO...
                ........#.
                ..#....#.#
                ..O..#.O.O
                ..O.......
                #....###..
                #....#....
                """.lines().toArray(String[]::new)
        ));
    }
}
