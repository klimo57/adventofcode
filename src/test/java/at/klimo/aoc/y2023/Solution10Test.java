package at.klimo.aoc.y2023;

import at.klimo.aoc.AOCInputSource;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solution10Test {

    Solution10 solution = new Solution10();

    @ParameterizedTest
    @AOCInputSource(
        values = {
            """
                -L|F7
                7S-7|
                L|7||
                -L-J|
                L|-JF
                """,
            """
                7-F7-
                .FJ|7
                SJLL7
                |F--J
                LJ.LJ
                """
        },
        expectationsAsLong = {4L, 8L}
    )
    void testP1(String[] input, long expected) {
        var result = solution.solveP1(solution.mapInput(input));
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @AOCInputSource(
        values = {
            """
                ...........
                .S-------7.
                .|F-----7|.
                .||.....||.
                .||.....||.
                .|L-7.F-J|.
                .|..|.|..|.
                .L--J.L--J.
                ...........
                """,
            """
                ..........
                .S------7.
                .|F----7|.
                .||....||.
                .||....||.
                .|L-7F-J|.
                .|..||..|.
                .L--JL--J.
                ..........
                """,
            """
                .F----7F7F7F7F-7....
                .|F--7||||||||FJ....
                .||.FJ||||||||L7....
                FJL7L7LJLJ||LJ.L-7..
                L--J.L7...LJS7F-7L7.
                ....F-J..F7FJ|L7L7L7
                ....L7.F7||L7|.L7L7|
                .....|FJLJ|FJ|F7|.LJ
                ....FJL-7.||.||||...
                ....L---J.LJ.LJLJ...
                """,
            """
                FF7FSF7F7F7F7F7F---7
                L|LJ||||||||||||F--J
                FL-7LJLJ||||||LJL-77
                F--JF--7||LJLJ7F7FJ-
                L---JF-JLJ.||-FJLJJ7
                |F|F-JF---7F7-L7L|7|
                |FFJF7L7F-JF7|JL---7
                7-L-JL7||F7|L7F-7F7|
                L.L7LFJ|||||FJL7||LJ
                L7JLJL-JLJLJL--JLJ.L
                """
        },
        expectationsAsLong = {4L, 4L, 8L, 10L}
    )
    void testP2(String[] input, long expected) {
        var result = solution.solveP2(solution.mapInput(input));
        assertEquals(expected, result);
    }
}
