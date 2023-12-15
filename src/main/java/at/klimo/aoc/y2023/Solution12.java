package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.StringUtils.containsOnly;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Solution12 implements Solution<List<Pair<String, List<Integer>>>, Long> {
    @Override
    public Long solveP1(List<Pair<String, List<Integer>>> input) throws ImplementationException {
        return (long) input
            .stream()
            .mapToInt(pair -> permutate(pair.getLeft(), pair.getRight()))
            .sum();
    }

    @Override
    public Long solveP2(List<Pair<String, List<Integer>>> input) throws ImplementationException {
        return null;
    }

    static int permutate(String springs, List<Integer> groups) {
        var sanitized = springs.replaceAll("^\\.+|\\.+$", "");

        if (groups.isEmpty()) {
            return StringUtils.isBlank(sanitized) ? 1 : 0;
        }
        if (groups.stream().mapToInt(Integer::intValue).sum() + groups.size() - 1 > springs.length()) {
            return 0;
        }

        var firstGroupSize = groups.get(0);
        if (firstGroupSize > sanitized.length()) {
            return 0;
        }

        var first = sanitized.charAt(0);
        if (first == '?') {
            var withoutFirst = sanitized.substring(1);
            if (isNotBlank(withoutFirst) && withoutFirst.charAt(0) != '.') {
                return permutate("#" + withoutFirst, groups) + permutate(withoutFirst, groups);
            }
            return permutate("#" + withoutFirst, groups);
        }
        if (first == '#') {
            // match first group or fail
            var firstGroup = sanitized.substring(0, firstGroupSize);
            if (containsOnly(firstGroup, '#', '?')) {
                if (sanitized.length() == firstGroup.length()) {
                    return 1;
                }
                var charAfterGroup = sanitized.charAt(firstGroupSize);
                if (charAfterGroup == '.' || charAfterGroup == '?') {
                    return permutate(sanitized.substring(firstGroupSize + 1), groups.subList(1, groups.size()));
                }
            }
            return 0;
        }
        throw new IllegalArgumentException("faulty input");
    }

    static int calculatePossibleCombinations(Pair<String, List<Integer>> conditionRecord) {
        try {
            var smallestGroup = conditionRecord.getRight().stream().mapToInt(Integer::intValue).min().orElseThrow();
            var springGroups = Arrays.stream(conditionRecord.getLeft().split("\\.+"))
                .filter(StringUtils::isNotBlank)
                .filter(s -> s.length() >= smallestGroup)
                .toList();
            if (springGroups.size() == conditionRecord.getRight().size()) {
                return IntStream.range(0, springGroups.size())
                    .map(i -> calculatePossibleCombinations(springGroups.get(i), conditionRecord.getRight().get(i)))
                    .reduce(1, (a, b) -> a * b);
            }
            var remainingGroups = new ArrayList<>(conditionRecord.getRight());
            var possibleCombinations = new ArrayList<Integer>();
            for (var sg : springGroups) {
                var fittingGroups = new ArrayList<Integer>();
                var remainingSpace = sg.length();
                var it = remainingGroups.iterator();
                while (it.hasNext()) {
                    var groupSize = it.next();
                    remainingSpace -= groupSize;
                    if (remainingSpace >= 0) {
                        fittingGroups.add(groupSize);
                        it.remove();
                    } else {
                        break;
                    }
                    remainingSpace--;
                }
                possibleCombinations.add(calculatePossibleCombinations(sg, fittingGroups));
            }
            return possibleCombinations.stream().mapToInt(Integer::intValue).reduce(1, (a, b) -> a * b);
        } catch (Exception e) {
            System.err.println("error for condition record " + conditionRecord.getLeft() + " " + conditionRecord.getRight().stream().map(Object::toString).collect(Collectors.joining(",")));
            throw e;
        }
    }

    static int calculatePossibleCombinations(String springs, List<Integer> groups) {
        try {
            if (groups.isEmpty()) {
                return 0;
            }
            if (groups.size() == 1) {
                return calculatePossibleCombinations(springs, groups.get(0));
            }
            if (groups.size() == 2) {
                return IntStream.range(groups.get(0) + 1, springs.length() - groups.get(1) + 1)
                    .map(i -> calculatePossibleCombinations(springs.substring(i), groups.get(1)))
                    .sum();
            }

            var parts = springs.split("#", 2);
            var beforeFirstDamaged = parts[0];
            var remainder = parts[1];
            if (remainder.length() > groups.get(0)) {

            }

            var startOfSubstring = groups.get(0);
            for (; springs.charAt(startOfSubstring) == '#'; startOfSubstring++) {
            }
            return calculatePossibleCombinations(springs.substring(startOfSubstring + 1), groups.subList(1, groups.size()));
        } catch (Exception e) {
            System.err.println("error for springs " + springs + " | " + groups.stream().map(Object::toString).collect(Collectors.joining(",")));
            throw e;
        }
    }

    static int calculatePossibleCombinations(String springs, int groupSize) {
        if (springs.length() == 0) {
            return 0;
        }
        if (groupSize == springs.length()) {
            return 1;
        }
        if (groupSize == 1) {
            return springs.length();
        }
        int firstDamagedSpring = springs.indexOf('#');
        if (firstDamagedSpring == 0 || firstDamagedSpring == springs.length() - 1) {
            return 1;
        }
        if (firstDamagedSpring > 0) {
            var substrStart = Math.max(springs.lastIndexOf('#') + 1 - groupSize, 0);
            var substrEnd = Math.min(firstDamagedSpring + groupSize, springs.length());
            try {
                var substr = springs.substring(substrStart, substrEnd);
                if (!Objects.equals(springs, substr)) {
                    return calculatePossibleCombinations(substr, groupSize);
                }
            } catch (Exception e) {
                System.err.println("err: " + springs + " (" + groupSize + ") " + " [" + substrStart + ", " + substrEnd + ")");
                throw e;
            }
        }
        return 1 + calculatePossibleCombinations(springs.substring(1), groupSize);
    }

    @Override
    public List<Pair<String, List<Integer>>> mapInput(String[] input) {
        return Arrays.stream(input)
            .map(line -> line.split(" "))
            .map(parts -> Pair.of(
                parts[0],
                Arrays.stream(parts[1].split(","))
                    .map(Integer::parseInt)
                    .toList()
            ))
            .map(pair -> {
                // reverse input if last hashtag is closer to the end of the string than the first hashtag is to the beginning
                var firstDamage = pair.getLeft().indexOf('#');
                if (firstDamage >= 0) {
                    var lastDamage = pair.getLeft().lastIndexOf('#');
                    if (pair.getLeft().length() - lastDamage - 1 < firstDamage) {
                        return Pair.of(StringUtils.reverse(pair.getLeft()), pair.getRight().reversed());
                    }
                }
                return pair;
            })
            .toList();
    }
}
