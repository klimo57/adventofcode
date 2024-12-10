package at.klimo.aoc.y2024;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Character.getNumericValue;
import static java.lang.Integer.parseInt;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Solution1 implements Solution<Pair<List<Integer>, List<Integer>>, Long> {
    @Override
    public Pair<List<Integer>, List<Integer>> mapInput(String[] input) {
        List<Integer> left = new ArrayList<>(input.length);
        List<Integer> right = new ArrayList<>(input.length);
        Arrays.stream(input).forEach(line -> {
            var parts = line.split("\\s+");
            left.add(parseInt(parts[0]));
            right.add(parseInt(parts[parts.length - 1]));
        });
        Collections.sort(left);
        Collections.sort(right);
        return Pair.of(left, right);
    }

    @Override
    public Long solveP1(Pair<List<Integer>, List<Integer>> input) throws ImplementationException {
        return IntStream.range(0, input.getLeft().size())
                .boxed()
                .map(i -> Pair.of(input.getLeft().get(i), input.getRight().get(i)))
                .mapToLong(pair -> (long) Math.abs(pair.getLeft() - pair.getRight()))
                .sum();
    }

    @Override
    public Long solveP2(Pair<List<Integer>, List<Integer>> input) throws ImplementationException {
        Map<Integer, Long> counts = input.getRight()
                .stream()
                .collect(groupingBy(identity(), counting()));
        return input.getLeft()
                .stream()
                .mapToLong(x -> x * counts.getOrDefault(x, 0L))
                .sum();
    }
}
