package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.Optional;

public class Solution1 implements Solution<String[], String> {
    @Override
    public String solveP1(String[] input) throws ImplementationException {
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

        /**
         * returns the "calibration code" of a line. the calibration code consists of the first and the last digit
         * in the line, either written out in letters (e.g. "one") or as the digit itself (1)<br><br>
         * <p>
         * for efficiency, the line is looked at from both ends. if a digit is found, its index is compared to any
         * digit already found to find out which one is first.<br><br>
         * <p>
         * for example, when looking at the string <i>1asd3f</i>, the first and last characters are '1' and 'f'.
         * since 'f' is not a digit, it is ignored. '1' on the other hand will be noticed as first <b>and</b> last digit. <br>
         * the next digits are 'a' and '3'. again, 'a' can be discarded, but '3' is a digit. by comparing the indizes
         * the digits where found at, it can be determined that '3' is not the first digit, but will indeed replace '1'
         * as the last digit that can be found. <br>
         * through the fact that there is no character with an index smaller than our already determined first digit or
         * greater than the last one, the calculation is already finished
         */
        public int calibrate(String line, int i, int j) {
            // throw on empty lines
            if (line.length() < 1) {
                throw new ImplementationException("no digits found in line " + this.line);
            }

            // calculate the indizes and substring range for the next run
            int nextI = i + 1;
            int nextJ = j - 1;
            int beginIndex = 1;
            int endIndex = line.length() - 1;

            // look for digits
            var first = new CalibrationCharacter(line.charAt(0), i);
            var ldStart = LetteredDigit.fromStringStart(line);
            var last = new CalibrationCharacter(line.charAt(line.length() - 1), j);
            var ldEnd = LetteredDigit.fromStringEnd(line);

            // check if we still need to look for a first digit
            if (alpha == null || alpha.index > i) {
                if (first.isValid()) {
                    alpha = first;
                } else if (ldStart.isPresent()) {
                    // if the digit was written out, the indizes need to be corrected
                    var ld = ldStart.get();
                    alpha = new CalibrationCharacter(ld.digit, i);
                    beginIndex = ld.name().length();
                    nextI = i + beginIndex;
                } else if (last.isValid() && (alpha == null || alpha.index > j)) {
                    alpha = last;
                } else if (ldEnd.isPresent()) {
                    // use j as the index here to make sure we catch digits from the other side
                    alpha = new CalibrationCharacter(ldEnd.get().digit, j);
                }
            }
            if (omega == null || omega.index < j) {
                if (last.isValid()) {
                    omega = last;
                } else if (ldEnd.isPresent()) {
                    // if the digit was written out, the indizes need to be corrected
                    var ld = ldEnd.get();
                    omega = new CalibrationCharacter(ld.digit, j);
                    endIndex = line.length() - ld.name().length();
                    nextJ = j - ld.name().length();
                } else if (first.isValid() && (omega == null || omega.index < i)) {
                    omega = first;
                } else if (ldStart.isPresent()) {
                    // use i as the index here to make sure we catch digits from the other side
                    omega = new CalibrationCharacter(ldStart.get().digit, i);
                }
            }
            /*
             * we are finished if we have both digits, and
             *  a) there are no more digits or
             *  b) the indizes of our digits are further on the outsides of the line than all indizes yet to come
             */
            if (alpha != null && omega != null && (line.length() <= 2 || nextI >= alpha.index && nextJ <= omega.index)) {
                return Integer.parseInt(String.valueOf(new char[]{alpha.value, omega.value}));
            }
            return calibrate(line.substring(beginIndex, endIndex), nextI, nextJ);
        }
    }

    /**
     * A calibration character contains of a value and the index on which it was found.
     * It is valid if it is a digit.
     */
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

        /**
         * get the lettered digit at the start of a string (if there is one)
         */
        static Optional<LetteredDigit> fromStringStart(String s) {
            final var sanitized = s.trim().toLowerCase();
            return Arrays.stream(values())
                .filter(ld -> sanitized.startsWith(ld.name().toLowerCase()))
                .findAny();
        }

        /**
         * get the lettered digit at the end of a string (if there is one)
         */
        static Optional<LetteredDigit> fromStringEnd(String s) {
            final var sanitized = s.trim().toLowerCase();
            return Arrays.stream(values())
                .filter(ld -> sanitized.endsWith(ld.name().toLowerCase()))
                .findAny();
        }
    }
}
