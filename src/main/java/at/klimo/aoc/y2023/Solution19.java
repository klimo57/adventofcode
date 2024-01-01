package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiFunction;

import static at.klimo.aoc.y2023.Solution19.Result.ACCEPT;
import static at.klimo.aoc.y2023.Solution19.Result.NONE;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Solution19 implements Solution<Pair<Solution19.RuleSet, List<Solution19.Part>>, Long> {

    @Override
    public Long solveP1(Pair<RuleSet, List<Part>> input) throws ImplementationException {
        return input.getRight().stream()
            .filter(part -> input.getLeft().apply(part) == ACCEPT)
            .mapToLong(Part::rating)
            .sum();
    }

    @Override
    public Long solveP2(Pair<RuleSet, List<Part>> input) throws ImplementationException {


        var paths = new ArrayList<List<Rule>>();
        var rules = input.getLeft().rules.get("in");
        var path = new LinkedList<Rule>();
        for (var rule : rules) {
            path.add(rule);

        }

        return Solution.super.solveP2(input);
    }

    record Boundary(int lower, int upper) {
        Boundary() {
            this(1, 4000);
        }

        Boundary refine(char comp, int value) {
            return switch (comp) {
                case '<' -> new Boundary(lower, value);
                case '>' -> new Boundary(upper, value);
                default -> this;
            };
        }
    }

    public record RuleSet(Map<String, List<Rule>> rules) {
        Result apply(Part part) {
            var result = NONE;
            var ruleSet = rules.get("in");
            while (result == NONE) {
                for (var rule : ruleSet) {
                    if (rule.apply(part)) {
                        result = Result.fromString(rule.result);
                        ruleSet = rules.get(rule.result);
                        break;
                    }
                }
            }
            return result;
        }
    }

    enum Result {
        ACCEPT, REJECT, NONE;

        static Result fromString(String s) {
            return switch (s) {
                case "A" -> ACCEPT;
                case "R" -> REJECT;
                default -> NONE;
            };
        }
    }

    public record Rule(String description, BiFunction<Part, Character, Integer> propFn, char prop, int compValue,
                       char comparator, String result) {
        boolean apply(Part part) {
            return new IntComparison(propFn.apply(part, prop), compValue, comparator).compare();
        }

        Rule invert(String newResult) {
            return new Rule(
                "!(" + description + ")",
                propFn,
                prop,
                comparator == '<' ? compValue - 1 : compValue + 1,
                comparator == '<' ? '>' : '<',
                newResult
            );
        }
    }

    public record Part(int x, int m, int a, int s) {
        long rating() {
            return (long) x + (long) m + (long) a + (long) s;
        }

        int prop(char name) {
            return switch (name) {
                case 'x' -> x;
                case 'm' -> m;
                case 'a' -> a;
                case 's' -> s;
                default -> throw new IllegalArgumentException("unknown property: " + name);
            };
        }
    }

    @Override
    public Pair<RuleSet, List<Part>> mapInput(String[] input) {
        var rules = new HashMap<String, List<Rule>>();
        var parts = new ArrayList<Part>();
        boolean parseRules = true;
        for (var line : input) {
            if (isBlank(line)) {
                parseRules = false;
                continue;
            }
            if (parseRules) {
                var ruleDefinition = line.replace("}", "").split("\\{");
                rules.put(
                    ruleDefinition[0],
                    Arrays.stream(ruleDefinition[1].split(","))
                        .map(rule -> {
                            if (!rule.contains(":")) {
                                return new Rule(rule, (part, prop) -> -1, '\u0000', 1, '<', rule);
                            }
                            var ruleParts = rule.split(":");
                            return new Rule(
                                ruleParts[0],
                                (part, prop) -> part.prop(prop),
                                ruleParts[0].charAt(0),
                                Integer.parseInt(ruleParts[0].substring(2)),
                                ruleParts[0].charAt(1),
                                ruleParts[1]);
                        }).toList()
                );
            } else {
                var props = Arrays.stream(line.replaceAll("\\{|\\}", "").split(","))
                    .map(propDeclaration -> propDeclaration.split("="))
                    .collect(toMap(prop -> prop[0], prop -> Integer.parseInt(prop[1])));
                parts.add(new Part(props.get("x"), props.get("m"), props.get("a"), props.get("s")));
            }
        }
        return Pair.of(new RuleSet(rules), parts);
    }

    record IntComparison(int a, int b, char comparator) {
        boolean compare() {
            return switch (comparator) {
                case '<' -> a < b;
                case '>' -> a > b;
                default -> throw new UnsupportedOperationException("unsupported comparator: " + comparator);
            };
        }
    }
}
