package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.util.ArithmeticUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class Solution8 implements Solution<Solution8.Input, Long> {

    @Override
    public Long solveP1(Input input) throws ImplementationException {
        return shortestPath("AAA", List.of("ZZZ"), input.graph, input.directions);
    }

    @Override
    public Long solveP2(Input input) throws ImplementationException {
        return input.graph.keySet()
            .stream()
            .filter(p -> p.endsWith("A"))
            .mapToLong(start -> shortestPath(
                start,
                input.graph.keySet()
                    .stream()
                    .filter(p -> p.endsWith("Z"))
                    .toList(),
                input.graph,
                input.directions))
            .reduce(1, ArithmeticUtils::lcm);
    }

    /**
     * returns the shortest path from <code>from</code> to one of the ends in a graph
     */
    static long shortestPath(String from,
                             List<String> to,
                             Map<String, Pair<String, String>> graph,
                             Directions directions) {
        var pos = from;
        long steps = 1;
        while (true) {
            pos = directions.proceed(graph.get(pos));
            if (to.contains(pos)) {
                break;
            }
            steps++;
        }
        return steps;
    }

    @RequiredArgsConstructor
    static class Directions {
        final char[] directions;
        int pointer = 0;

        String proceed(Pair<String, String> paths) {
            String next = directions[pointer++] == 'L' ? paths.getLeft() : paths.getRight();
            if (pointer >= directions.length) {
                pointer = 0;
            }
            return next;
        }
    }

    @Override
    public Input mapInput(String[] input) {
        return new Input(
            new Directions(input[0].toCharArray()),
            IntStream.range(2, input.length)
                .mapToObj(i -> input[i])
                .map(line -> line.split("="))
                .map(parts -> Pair.of(
                    parts[0].trim(),
                    Arrays.stream(parts[1].split(","))
                        .map(nodeString -> nodeString.replaceAll("[\\(\\)\\s]", ""))
                        .toArray(String[]::new)
                ))
                .collect(toMap(
                    Pair::getLeft,
                    pair -> Pair.of(pair.getRight()[0], pair.getRight()[1])
                ))
        );
    }

    public record Input(Directions directions, Map<String, Pair<String, String>> graph) {
    }
}
