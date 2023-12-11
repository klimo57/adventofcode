package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import at.klimo.aoc.util.CharacterMatrix;
import at.klimo.aoc.util.PointXY;
import org.apache.commons.lang3.Range;

import java.util.ArrayList;
import java.util.List;

public class Solution11 implements Solution<CharacterMatrix, Long> {
    @Override
    public Long solveP1(CharacterMatrix input) throws ImplementationException {
        return sumOfShortestPaths(input, 2);
    }

    @Override
    public Long solveP2(CharacterMatrix input) throws ImplementationException {
        return sumOfShortestPaths(input, 1_000_000);
    }

    static long sumOfShortestPaths(CharacterMatrix matrix, int expansionFactor) {
        var expandingRows = expandingRowIndizes(matrix);
        var expandingColumns = expandingColumnIndizes(matrix);
        var galaxies = matrix.stream()
            .filter(val -> val.value() == '#')
            .map(CharacterMatrix.Value::coordinates)
            .toList();
        var mutableGalaxyList = new ArrayList<>(galaxies);
        var it = mutableGalaxyList.iterator();
        long sumOfShortestPaths = 0;
        while (it.hasNext()) {
            var start = it.next();
            it.remove();
            sumOfShortestPaths += mutableGalaxyList.stream()
                .mapToLong(end -> shortestPath(start, end, expandingRows, expandingColumns, expansionFactor))
                .sum();
        }
        return sumOfShortestPaths;
    }

    static long shortestPath(PointXY start, PointXY end, List<Integer> expandingRows, List<Integer> expandingColumns, int expansionFactor) {
        var horizontalRange = Range.between(start.x(), end.x());
        var verticalRange = Range.between(start.y(), end.y());
        var horizontalExpansion = expandingRows.stream().filter(verticalRange::contains).count();
        var horizontalDistance = horizontalRange.getMaximum() - horizontalRange.getMinimum() + horizontalExpansion * (expansionFactor - 1);
        var verticalExpansion = expandingColumns.stream().filter(horizontalRange::contains).count();
        var verticalDistance = verticalRange.getMaximum() - verticalRange.getMinimum() + verticalExpansion * (expansionFactor - 1);
        return horizontalDistance + verticalDistance;
    }

    static List<Integer> expandingRowIndizes(CharacterMatrix matrix) {
        var indizes = new ArrayList<Integer>();
        for (int i = 0; i < matrix.length; i++) {
            if (matrix.row(i).stream().allMatch(val -> val.value() == '.')) {
                indizes.add(i);
            }
        }
        return indizes;
    }

    static List<Integer> expandingColumnIndizes(CharacterMatrix matrix) {
        var indizes = new ArrayList<Integer>();
        for (int i = 0; i < matrix.width; i++) {
            if (matrix.column(i).stream().allMatch(val -> val.value() == '.')) {
                indizes.add(i);
            }
        }
        return indizes;
    }

    @Override
    public CharacterMatrix mapInput(String[] input) {
        return new CharacterMatrix(input);
    }
}
