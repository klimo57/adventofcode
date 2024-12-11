package at.klimo.aoc.y2024;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution3 implements Solution<String[], Long> {

    @Override
    public Long solveP1(String[] input) throws ImplementationException {
        List<String> multiplications = new ArrayList<>();
        Arrays.stream(input).forEach(line -> {
            Matcher matcher = Pattern.compile("mul\\([0-9]{1,3},[0-9]{1,3}\\)").matcher(line);
            while (matcher.find()) {
                multiplications.add(matcher.group());
            }
        });
        return multiplications.stream().mapToLong(this::multiply).sum();
    }

    @Override
    public Long solveP2(String[] input) throws ImplementationException {
        AtomicBoolean enabled = new AtomicBoolean(true);
        List<String> multiplications = new ArrayList<>();
        Arrays.stream(input).forEach(line -> {
            Matcher matcher = Pattern.compile("mul\\([0-9]{1,3},[0-9]{1,3}\\)|(don't\\(\\))|(do\\(\\))").matcher(line);
            while (matcher.find()) {
                String match = matcher.group();
                if("don't()".equals(match)) {
                    enabled.set(false);
                } else if("do()".equals(match)) {
                    enabled.set(true);
                } else {
                    if(enabled.get()) {
                        multiplications.add(match);
                    }
                }
            }
        });
        return multiplications.stream().mapToLong(this::multiply).sum();
    }

    private Long multiply(String multiplication) {
        long a = Long.parseLong(multiplication.substring(multiplication.indexOf('(') + 1, multiplication.indexOf(',')));
        long b = Long.parseLong(multiplication.substring(multiplication.indexOf(',') + 1, multiplication.indexOf(')')));
        return a * b;
    }
}
