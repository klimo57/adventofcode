package at.klimo.aoc.y2023;

import at.klimo.chars.CharacterMatrix;
import at.klimo.common.PointXY;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Solution3Test {

    static final String[] input = new String[]{
        "467..114..",
        "...*......",
        "..35..633.",
        "......#...",
        "617*......",
        ".....+.58.",
        "..592.....",
        "......755.",
        "...$.*....",
        ".664.598..",
    };

    @Test
    void testPuzzle1() {
        var result = new Solution3().solveP1(new CharacterMatrix(input));
        assertEquals(4361, result);
    }

    @Test
    void should_get_all_numbers() {
        var numbers = Solution3.readNumbers(new CharacterMatrix(input)).toList();
        assertEquals(10, numbers.size());
        var values = numbers.stream().map(Solution3.NumberInSchematic::value).toList();
        assertTrue(values.contains(467));
        assertTrue(values.contains(114));
        assertTrue(values.contains(35));
        assertTrue(values.contains(633));
        assertTrue(values.contains(617));
        assertTrue(values.contains(58));
        assertTrue(values.contains(592));
        assertTrue(values.contains(755));
        assertTrue(values.contains(664));
        assertTrue(values.contains(598));
    }

    @Test
    void should_get_correct_coordinates() {
        var numbers = Solution3.readNumbers(new CharacterMatrix(input)).toList();
        for (Solution3.NumberInSchematic n : numbers) {
            if (n.value() == 467) {
                assertEquals(new PointXY(0, 0), n.start());
                assertEquals(new PointXY(2, 0), n.end());
            }
            if (n.value() == 114) {
                assertEquals(new PointXY(5, 0), n.start());
                assertEquals(new PointXY(7, 0), n.end());
            }
            if (n.value() == 633) {
                assertEquals(new PointXY(6, 2), n.start());
                assertEquals(new PointXY(8, 2), n.end());
            }
            if (n.value() == 58) {
                assertEquals(new PointXY(7, 5), n.start());
                assertEquals(new PointXY(8, 5), n.end());
            }
            if (n.value() == 664) {
                assertEquals(new PointXY(1, 9), n.start());
                assertEquals(new PointXY(3, 9), n.end());
            }
        }
    }

    @Test
    void should_get_all_neighbours() {
        var numbers = Solution3.readNumbers(new CharacterMatrix(input)).toList();
        for (Solution3.NumberInSchematic n : numbers) {
            var neighbours = n.neighbours().toList();
            assertEquals(n.value() > 99 ? 12 : 10, neighbours.size());
        }
    }

    @Test
    void should_filter_out_coordinates_outside_schematics() {
        final var schematics = new CharacterMatrix(input);
        var numbers = Solution3.readNumbers(schematics).toList();
        for (Solution3.NumberInSchematic n : numbers) {
            var neighbours = n.neighbours().filter(schematics.includes()).toList();
            if (n.value() == 467) {
                assertEquals(5, neighbours.size());
            }
            if (n.value() == 114) {
                assertEquals(7, neighbours.size());
            }
            if (n.value() == 35) {
                assertEquals(10, neighbours.size());
            }
            if (n.value() == 633) {
                assertEquals(12, neighbours.size());
            }
            if (n.value() == 617) {
                assertEquals(9, neighbours.size());
            }
            if (n.value() == 598) {
                assertEquals(7, neighbours.size());
            }
        }
    }

    @Test
    void testPuzzle2() {
        var result = new Solution3().solveP2(new CharacterMatrix(input));
        assertEquals(467835, result);
    }
}
