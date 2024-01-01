package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import at.klimo.chars.CharacterMatrix;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.reverse;
import static org.apache.commons.lang3.math.NumberUtils.max;

public class Solution13 implements Solution<List<CharacterMatrix>, Integer> {

    @Override
    public Integer solveP1(List<CharacterMatrix> input) throws ImplementationException {
        return input.stream()
            .mapToInt(matrix -> max(indexOfVerticalReflection(matrix), indexOfHorizontalReflection(matrix) * 100, 0))
            .sum();
    }

    @Override
    public Integer solveP2(List<CharacterMatrix> input) throws ImplementationException {
        return input.stream()
            .mapToInt(Solution13::findSolutionForCleanMirrors)
            .sum();
    }

    static int findSolutionForCleanMirrors(CharacterMatrix matrix) {
        int smudgedSolution = max(indexOfHorizontalReflection(matrix) * 100, indexOfVerticalReflection(matrix));
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix.width; x++) {
                var unsmudged = matrix.replaceCharAt(x, y, matrix.at(x, y) == '.' ? '#' : '.');
                var horizontal = indexOfHorizontalReflection(unsmudged, smudgedSolution / 100) * 100;
                if (horizontal >= 0 && horizontal != smudgedSolution) {
                    return horizontal;
                }
                var vertical = indexOfVerticalReflection(unsmudged, smudgedSolution);
                if (vertical >= 0 && vertical != smudgedSolution) {
                    return vertical;
                }
            }
        }
        System.err.println("no solution found for matrix " + matrix);
        return 0;
    }

    static int indexOfHorizontalReflection(CharacterMatrix matrix) {
        return indexOfHorizontalReflection(matrix, -1);
    }

    static int indexOfHorizontalReflection(CharacterMatrix matrix, int excludedSolution) {
        var firstRow = matrix.rowAsString(0);
        var lastRow = matrix.rowAsString(matrix.length - 1);
        if (matrix.length % 2 == 0 && Objects.equals(firstRow, lastRow)) {
            return matrix.length / 2;
        }
        for (int i = 1, j = matrix.length - 2; i < matrix.length - 1 && j > 0; i++, j--) {
            var nextFromStart = matrix.rowAsString(i);
            var nextFromEnd = matrix.rowAsString(j);
            if (Objects.equals(nextFromStart, lastRow) && isCompleteReflection(matrix.columns(), i, matrix.length)) {
                var solution = ((matrix.length - i) / 2) + i;
                if (solution != excludedSolution) {
                    return solution;
                }
            }
            if (Objects.equals(firstRow, nextFromEnd) && isCompleteReflection(matrix.columns(), 0, j + 1)) {
                var solution = (j + 1) / 2;
                if (solution != excludedSolution) {
                    return solution;
                }
            }
        }
        return -1;
    }

    static int indexOfVerticalReflection(CharacterMatrix matrix) {
        return indexOfVerticalReflection(matrix, -1);
    }

    static int indexOfVerticalReflection(CharacterMatrix matrix, int excludedSolution) {
        var firstCol = matrix.columnAsString(0);
        var lastCol = matrix.columnAsString(matrix.width - 1);
        if (matrix.width % 2 == 0 && Objects.equals(firstCol, lastCol)) {
            return matrix.width / 2;
        }
        for (int i = 1, j = matrix.width - 2; i < matrix.width - 1 && j > 0; i++, j--) {
            var nextFromStart = matrix.columnAsString(i);
            var nextFromEnd = matrix.columnAsString(j);
            if (Objects.equals(nextFromStart, lastCol) && isCompleteReflection(matrix.rows(), i, matrix.width)) {
                var solution = ((matrix.width - i) / 2) + i;
                if (solution != excludedSolution) {
                    return solution;
                }
            }
            if (Objects.equals(firstCol, nextFromEnd) && isCompleteReflection(matrix.rows(), 0, j + 1)) {
                var solution = (j + 1) / 2;
                if (solution != excludedSolution) {
                    return solution;
                }
            }
        }
        return -1;
    }

    static boolean isCompleteReflection(Stream<String> rowsOrColumns, int startIncl, int endExcl) {
        return (endExcl - startIncl) % 2 == 0 &&
            rowsOrColumns
                .map(s -> s.substring(startIncl, endExcl))
                .allMatch(s -> Objects.equals(s, reverse(s)));
    }

    @Override
    public List<CharacterMatrix> mapInput(String[] input) {
        var result = new ArrayList<CharacterMatrix>();
        var matrix = new ArrayList<String>();
        for (String line : input) {
            if (StringUtils.isBlank(line)) {
                result.add(new CharacterMatrix(matrix));
                matrix.clear();
                continue;
            }
            matrix.add(line);
        }
        result.add(new CharacterMatrix(matrix));
        return result;
    }
}
