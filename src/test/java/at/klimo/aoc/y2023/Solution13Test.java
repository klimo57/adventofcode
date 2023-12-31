package at.klimo.aoc.y2023;

import at.klimo.aoc.AOCInputSource;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solution13Test {

    Solution13 solution = new Solution13();

    @ParameterizedTest
    @AOCInputSource(
        values = {
            """
                #.##..##.
                ..#.##.#.
                ##......#
                ##......#
                ..#.##.#.
                ..##..##.
                #.#.##.#.
                """,
            """
                #...##..#
                #....#..#
                ..##..###
                #####.##.
                #####.##.
                ..##..###
                #....#..#
                """,
            """
                .###...####..
                ###...#....#.
                ###...#....#.
                .###...####..
                ###.#........
                #####.#....#.
                ##.#..######.
                ####.#..##..#
                ..#....####..
                #.##.##.##.##
                #.#...#.##.#.
                .###.##.##.##
                #.#....#..#..
                ..###.##..##.
                ##.#..#....##
                """,
            """
                ###.#........
                ###.#........
                .....###..###
                .#.##.##..##.
                .##...#.##.#.
                ##..#..####..
                #..#.#.####.#
                #..####....#.
                #....#..##..#
                .#.###......#
                .#.#.#......#
                .#.####.##.##
                #.####.#..#.#
                ......######.
                ##.####.##.##
                ..###.##..##.
                #####.######.
                """,
            """
                ##.....##.....###
                .####..##..####..
                #.#.#.####.#.#.##
                ....##.##.##.....
                #####......######
                #..#..#..#..#..##
                #.####.##.####.##
                .###..#..#..###..
                #..###.##.###..##
                ###.#..##..#.####
                #.#..#####...#.##
                #.#.#.#..#.#.#.##
                .##.#..##..#.##..
                """,
            """
                #.#..#..#
                .######..
                ###..###.
                ..#..#...
                #.####.#.
                #..##..#.
                #.#..#.##
                .#.##.#.#
                ###..###.
                #.#..#.##
                #......##
                #......##
                #......##
                #......##
                #.#..#.##
                """,
            """
                ..######..###
                ###....####..
                ####..####...
                .#.####.#...#
                #.#....#.#...
                #........#...
                #........#...
                ##......##...
                #........#...
                """,
            """
                #..##...###
                #..##......
                #..##..#...
                #..##...###
                #####..#.##
                #..#..#####
                #..####.#.#
                .##.#.#.#..
                .....#####.
                ####..#####
                #..#.#.###.
                #..#.#..#.#
                #..#.##....
                """,
            """
                ##########....##.
                ##.#..#.###.#.##.
                ...####...##..##.
                #.######.#.##....
                ##.####.###.#...#
                ##......##.#.#..#
                .#..##..#.##.#..#
                ##..##..###.##..#
                #...##...########
                ....##....####..#
                ..######..#.##..#
                ...#..#....#..##.
                .#.#..#.#.####..#
                """
        },
        expectedType = Integer.class,
        expectationsAsInt = {5, 400, 200, 100, 16, 1200, 5, 2, 5}
    )
    void testP1(String[] input, int expected) {
        var result = solution.solveP1(solution.mapInput(input));
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @AOCInputSource(
        values = {
            """
                #.##..##.
                ..#.##.#.
                ##......#
                ##......#
                ..#.##.#.
                ..##..##.
                #.#.##.#.
                """,
            """
                #...##..#
                #....#..#
                ..##..###
                #####.##.
                #####.##.
                ..##..###
                #....#..#
                """,
            """
                #..##...###
                #..##..#...
                #..##..#...
                #..##...###
                #####..#.##
                #.##..#####
                #..####.#.#
                .##.#.#.#..
                .....#####.
                ####..#####
                #..#.#.###.
                #..#.#..#.#
                #..#.##....
                """,
            """
                ##########....##.
                ##.#..#.###.#.##.
                ...####...##..##.
                #.######.#.##....
                ##.####.###.#...#
                ##......##.#.#..#
                .#..##..#.##.#..#
                ##..##..###.##..#
                #...##...########
                ....##....####..#
                ..######..#.##..#
                ...#..#....#..##.
                .#.#..#.#.####..#
                """
        },
        expectedType = Integer.class,
        expectationsAsInt = {300, 100, 2, 15}
    )
    void testP2(String[] input, int expected) {
        var result = solution.solveP2(solution.mapInput(input));
        assertEquals(expected, result);
    }
}
