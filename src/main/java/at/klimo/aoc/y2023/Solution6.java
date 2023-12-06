package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;

public class Solution6 implements Solution<List<Pair<Long, Long>>, Long> {

    @Override
    public Long solveP1(List<Pair<Long, Long>> input) throws ImplementationException {
        return input.stream()
            .mapToLong(e -> calculateRecordBeaterCount(e.getKey(), e.getValue()))
            .reduce(1, (a, b) -> a * b);
    }

    @Override
    public Long solveP2(List<Pair<Long, Long>> input) throws ImplementationException {
        return calculateRecordBeaterCount(
            input.stream()
                .map(ints -> Pair.of("" + ints.getLeft(), "" + ints.getRight()))
                .reduce((acc, next) -> Pair.of(acc.getLeft() + next.getLeft(), acc.getRight() + next.getRight()))
                .map(strings -> Pair.of(Long.parseLong(strings.getLeft()), Long.parseLong(strings.getRight())))
                .orElseThrow()
        );
    }

    public long calculateRecordBeaterCount(Pair<Long, Long> timeAndRecord) {
        return calculateRecordBeaterCount(timeAndRecord.getLeft(), timeAndRecord.getRight());
    }

    public long calculateRecordBeaterCount(long time, long record) {
        double sqrt = sqrt(pow(time / 2.0, 2) - record);
        long lowerBound = (long) ceil(nextAfter(time / 2.0 - sqrt, Double.MAX_VALUE));
        long upperBound = (long) (floor(nextAfter(time / 2.0 + sqrt, Double.MIN_VALUE)));
        return upperBound - lowerBound + 1;
    }

    @Override
    public List<Pair<Long, Long>> mapInput(String[] input) {
        var times = Arrays.stream(input[0].split(" "))
            .filter(part -> NumberUtils.isCreatable(part))
            .map(Long::parseLong)
            .toList();
        var records = Arrays.stream(input[1].split(" "))
            .filter(part -> NumberUtils.isCreatable(part))
            .map(Long::parseLong)
            .toList();
        var pairs = new ArrayList<Pair<Long, Long>>(times.size());
        for (int i = 0; i < times.size(); i++) {
            pairs.add(Pair.of(times.get(i), records.get(i)));
        }
        return pairs;
    }
}
