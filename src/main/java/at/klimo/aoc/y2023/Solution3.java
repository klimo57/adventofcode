package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import at.klimo.aoc.util.CharacterMatrix;
import at.klimo.aoc.util.PointXY;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution3 implements Solution<CharacterMatrix, Integer> {
    @Override
    public Integer solveP1(final CharacterMatrix engineSchematics) throws ImplementationException {
        return readNumbers(engineSchematics)
            .filter(number -> number.neighbours()
                .filter(engineSchematics.includes())
                .map(engineSchematics::at)
                .anyMatch(c -> c != '.'))
            .mapToInt(NumberInSchematic::value)
            .sum();
    }

    @Override
    public Integer solveP2(final CharacterMatrix engineSchematics) throws ImplementationException {
        var gearCoordinates = engineSchematics.stream()
            .filter(val -> val.value() == '*')
            .map(CharacterMatrix.Value::coordinates)
            .toList();
        return readNumbers(engineSchematics)
            .map(number -> Pair.of(number, number.neighbours().filter(gearCoordinates::contains).findAny().orElse(null))) // CAVEAT: could there be more than 1 gear at a partNr?
            .filter(numberAndGear -> numberAndGear.getRight() != null)
            .collect(Collectors.groupingBy(numberAndGear -> numberAndGear.getRight()))
            .values()
            .stream()
            .filter(numbers -> numbers.size() >= 2)
            .mapToInt(numbers -> numbers.stream().mapToInt(n -> n.getLeft().value()).reduce(1, (a, b) -> a * b))
            .sum();
    }

    public static Stream<NumberInSchematic> readNumbers(CharacterMatrix engineSchematics) {
        var numbers = new ArrayList<NumberInSchematic>();
        for (int i = 0; i < engineSchematics.length; i++) {
            for (int j = 0; j < engineSchematics.width; j++) {
                if (Character.isDigit(engineSchematics.at(j, i))) {
                    String partNr = "";
                    int k = j;
                    for (; k < engineSchematics.width && Character.isDigit(engineSchematics.at(k, i)); k++) {
                        partNr += engineSchematics.at(k, i);
                    }
                    numbers.add(new NumberInSchematic(Integer.parseInt(partNr), new PointXY(j, i), new PointXY(k - 1, i)));
                    j += partNr.length() - 1;
                }
            }
        }
        return numbers.stream();
    }

    public record NumberInSchematic(int value, PointXY start, PointXY end) {
        Stream<PointXY> neighbours() {
            return IntStream.range(start.y() - 1, end.y() + 2)
                .boxed()
                .flatMap(y ->
                    IntStream.range(start.x() - 1, end.x() + 2)
                        .filter(x -> y < start.y() || y > end.y() || x < start.x() || x > end.x())
                        .mapToObj(x -> new PointXY(x, y))
                );
        }
    }

    @Override
    public CharacterMatrix mapInput(String[] input) {
        return new CharacterMatrix(input);
    }
}
