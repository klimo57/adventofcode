package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongUnaryOperator;
import java.util.stream.IntStream;

import static java.util.function.LongUnaryOperator.identity;

public class Solution5 implements Solution<Pair<long[], List<Solution5.AlmanacMap>>, Long> {
    @Override
    public Long solveP1(Pair<long[], List<AlmanacMap>> input) throws ImplementationException {
        return Arrays.stream(input.getLeft())
            .map(
                input.getRight()
                    .stream()
                    .map(m -> (LongUnaryOperator) m)
                    .reduce(identity(), (acc, map) -> acc.andThen(map)))
            .min()
            .orElseThrow();
    }

    @Override
    public Long solveP2(Pair<long[], List<AlmanacMap>> input) throws ImplementationException {
        var reversedMaps = input.getRight().reversed();
        var seeds = input.getLeft();
        var seedRanges = IntStream.iterate(0, i -> i < seeds.length, i -> i + 2)
            .boxed()
            .map(i -> Range.between(seeds[i], seeds[i] + seeds[i + 1] - 1))
            .toList();
        // brute force our way backwards through the maps
        for (long i = 0; true; i++) {
            long possibleSolution = i;
            for (var map : reversedMaps) {
                possibleSolution = map.applyReversed(possibleSolution);
            }
            final var solution = possibleSolution;
            if (seedRanges.stream().anyMatch(range -> range.contains(solution))) {
                return i;
            }
        }
    }

    @Override
    public Pair<long[], List<AlmanacMap>> mapInput(String[] input) {
        var seedLineParts = input[0].split(" ");
        long[] seeds = Arrays.stream(seedLineParts, 1, seedLineParts.length)
            .mapToLong(Long::parseLong)
            .toArray();
        var maps = new ArrayList<AlmanacMap>(7);
        for (int i = 2; i < input.length; i++) {
            if (input[i].contains("map")) {
                int j;
                for (j = i; j < input.length && StringUtils.isNotBlank(input[j]); j++) {
                }
                maps.add(new AlmanacMap(Arrays.copyOfRange(input, i + 1, j)));
                i = j;
            }
        }
        return Pair.of(seeds, maps);
    }

    /**
     * a map in the island island almanac. consists of different converters that map the input to a (different) output
     */
    public static class AlmanacMap implements LongUnaryOperator {
        private final List<Converter> converters;

        AlmanacMap(String[] input) {
            converters = new ArrayList<>(
                Arrays.stream(input)
                    .map(Converter::fromString)
                    .toList()
            );
        }

        @Override
        public long applyAsLong(long operand) {
            return converters.stream()
                .filter(f -> f.in.contains(operand))
                .mapToLong(f -> f.applyAsLong(operand))
                .findAny()
                .orElse(operand);
        }

        public long applyReversed(long operand) {
            return converters.stream()
                .filter(f -> f.out.contains(operand))
                .mapToLong(f -> f.reverse(operand))
                .findAny()
                .orElse(operand);
        }
    }

    /**
     * converts values from its input range to the corresponding output range
     */
    private record Converter(Range<Long> in, Range<Long> out) implements Comparable<Converter>, LongUnaryOperator {
        long delta() {
            return out.getMinimum() - in.getMinimum();
        }

        @Override
        public int compareTo(Converter o) {
            return Long.compare(out.getMinimum(), o.out.getMinimum());
        }

        @Override
        public long applyAsLong(long operand) {
            return operand + delta();
        }

        public long reverse(long operand) {
            return operand - delta();
        }

        /**
         * convenience method to read the input
         */
        static Converter fromString(String s) {
            var parts = Arrays.stream(s.split(" ")).mapToLong(Long::parseLong).toArray();
            return new Converter(
                Range.between(parts[1], parts[1] + parts[2] - 1),
                Range.between(parts[0], parts[0] + parts[2] - 1)
            );
        }
    }
}
