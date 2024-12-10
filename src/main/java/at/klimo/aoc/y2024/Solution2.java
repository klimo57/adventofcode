package at.klimo.aoc.y2024;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution2 implements Solution<List<List<Integer>>, Long> {
    @Override
    public List<List<Integer>> mapInput(String[] input) {
        return Arrays.stream(input)
                .map(line -> Arrays.stream(line.split(" "))
                        .map(Integer::parseInt)
                        .toList())
                .map(report -> report.getFirst() > report.getLast() ? report.reversed() : report)
                .toList();
    }

    @Override
    public Long solveP1(List<List<Integer>> input) throws ImplementationException {
        return input.stream()
                .filter(levels -> isSafe(levels, false))
                .count();
    }

    @Override
    public Long solveP2(List<List<Integer>> input) throws ImplementationException {
        return input.stream()
                .filter(levels -> isSafe(levels, true))
                .count();
    }

    private boolean isSafe(List<Integer> levels, boolean useDampener) {
        for(int i = 1; i < levels.size(); i++) {
            int prev = levels.get(i - 1);
            int cur = levels.get(i);
            if(prev >= cur || cur - prev > 3) {
                if(useDampener) {
                    return isSafe(Stream.concat(levels.subList(0, i - 1).stream(), levels.subList(i, levels.size()).stream()).toList(), false) ||
                            isSafe(Stream.concat(levels.subList(0, i).stream(), levels.subList(i +  1, levels.size()).stream()).toList(), false);
                }
                return false;
            }
        }
        return true;
    }
}
