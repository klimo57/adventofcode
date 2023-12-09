package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution9 implements Solution<Stream<List<Long>>, Long> {
    @Override
    public Long solveP1(Stream<List<Long>> input) throws ImplementationException {
        return input.mapToLong(Solution9::predictNextValue).sum();
    }

    @Override
    public Long solveP2(Stream<List<Long>> input) throws ImplementationException {
        return input.map(List::reversed).mapToLong(Solution9::predictNextValue).sum();
    }

    static long predictNextValue(List<Long> value) {
        var diffSequence = IntStream.iterate(value.size() - 1, i -> i > 0, i -> i - 1)
            .mapToObj(i -> value.get(i) - value.get(i - 1))
            .toList()
            .reversed();
        if (diffSequence.stream().allMatch(l -> l == 0L)) {
            return value.getLast();
        }
        return predictNextValue(diffSequence) + value.getLast();
    }

    @Override
    public Stream<List<Long>> mapInput(String[] input) {
        return Arrays.stream(input)
            .map(line -> Arrays.stream(line.split(" ")))
            .map(line -> line.map(Long::parseLong).toList());
    }
}
