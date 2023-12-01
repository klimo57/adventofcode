package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;

import static org.apache.commons.lang3.math.NumberUtils.isCreatable;

public class Solution1 implements Solution {
    @Override
    public String execute(String[] input) throws ImplementationException {
        return "" + Arrays.stream(input)
            .mapToInt(line -> new UncalibratedLine(line).calibrate(line, 0, line.length() - 1))
            .sum();
    }

    public static class UncalibratedLine {

        final String line;

        CalibrationCharacter alpha;
        CalibrationCharacter omega;

        public UncalibratedLine(String line) {
            this.line = line;
        }

        public int calibrate(String line, int i, int j) {
            if(line.length() < 1) {
                throw new ImplementationException("no digits found in line " + this.line);
            }

            int nextI = i + 1;
            int nextJ = j - 1;
            int beginIndex = 1;
            int endIndex = line.length() - 1;

            var first = new CalibrationCharacter(line.charAt(0), i);
            var ldStart = LetteredDigit.fromStringStart(line);
            var last = new CalibrationCharacter(line.charAt(line.length() - 1), j);
            var ldEnd = LetteredDigit.fromStringEnd(line);

            if(alpha == null || alpha.index > i) {
                if(first.isValid()) {
                    alpha = first;
                } else if(ldStart.isPresent()) {
                    var ld = ldStart.get();
                    alpha = new CalibrationCharacter(ld.digit, i);
                    beginIndex = ld.name().length();
                    nextI = i + beginIndex;
                } else if(last.isValid() && (alpha == null || alpha.index > j)) {
                    alpha = last;
                } else if(ldEnd.isPresent()) {
                    alpha = new CalibrationCharacter(ldEnd.get().digit, j);
                }
            }
            if(omega == null || omega.index < j) {
                if(last.isValid()) {
                    omega = last;
                } else if(ldEnd.isPresent()) {
                    var ld = ldEnd.get();
                    omega = new CalibrationCharacter(ld.digit, j);
                    endIndex = line.length() - ld.name().length();
                    nextJ = j - ld.name().length();
                } else if(first.isValid() && (omega == null || omega.index < i)) {
                    omega = first;
                } else if(ldStart.isPresent()) {
                    omega = new CalibrationCharacter(ldStart.get().digit, i);
                }
            }
            if(alpha != null && omega != null && (line.length() <= 2 || nextI >= alpha.index && nextJ <= omega.index)) {
                return Integer.parseInt(String.valueOf(new char[]{alpha.value, omega.value}));
            }
            return calibrate(line.substring(beginIndex, endIndex), nextI, nextJ);
        }
    }

    private static class CalibrationCharacter {
        final char value;
        final int index;

        CalibrationCharacter(char value, int index) {
            this.value = value;
            this.index = index;
        }

        boolean isValid() {
            return NumberUtils.isCreatable("" + value);
        }
    }

    private enum LetteredDigit {
        ONE('1'), TWO('2'), THREE('3'), FOUR('4'), FIVE('5'), SIX('6'), SEVEN('7'), EIGHT('8'), NINE('9');

        final char digit;

        LetteredDigit(char digit) {
            this.digit = digit;
        }

        static Optional<LetteredDigit> fromStringStart(String s) {
            final var sanitized = s.trim().toLowerCase();
            return Arrays.stream(values())
                .filter(ld -> sanitized.startsWith(ld.name().toLowerCase()))
                .findAny();
        }

        static Optional<LetteredDigit> fromStringEnd(String s) {
            final var sanitized = s.trim().toLowerCase();
            return Arrays.stream(values())
                .filter(ld -> sanitized.endsWith(ld.name().toLowerCase()))
                .findAny();
        }
    }
}
