package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import at.klimo.aoc.util.CharacterMatrix;
import at.klimo.aoc.util.PointXY;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static at.klimo.aoc.y2023.Solution16.Direction.*;
import static java.util.List.of;
import static org.apache.commons.lang3.StringUtils.indexOfAny;

public class Solution16 implements Solution<CharacterMatrix, Integer> {

    @Override
    public Integer solveP1(CharacterMatrix input) throws ImplementationException {
        var startValue = input.at(0, 0);
        if (startValue == '/') {
            return 1;
        }
        var startDirection = "\\|".contains("" + startValue) ? DOWN : RIGHT;
        return energizedFields(input, new PointXY(0, 0), startDirection).size();
    }

    @Override
    public Integer solveP2(CharacterMatrix input) throws ImplementationException {
        return Stream.of(
                startingPoints(input.row(0), DOWN),
                startingPoints(input.row(input.width - 1), UP),
                startingPoints(input.column(0), RIGHT),
                startingPoints(input.column(input.length - 1), LEFT)
            )
            .flatMapToInt(starts -> starts.mapToInt(start -> energizedFields(input, start.start, start.direction).size()))
            .max()
            .orElse(0);
    }

    static Stream<StartingPoint> startingPoints(List<CharacterMatrix.Value> starts, Direction defaultDir) {
        return starts.stream()
            .flatMap(val -> startDirections(val.value(), defaultDir)
                .stream()
                .map(dir -> new StartingPoint(val.coordinates(), dir)));
    }

    static List<Direction> startDirections(char start, Direction defaultDir) {
        return switch (start) {
            case '.' -> of(defaultDir);
            case '/', '\\' -> of(reflect(start, defaultDir));
            case '|', '-' -> split(start, defaultDir);
            default -> throw new IllegalArgumentException("'" + start + "' is not a valid character for a field");
        };
    }

    static Set<PointXY> energizedFields(CharacterMatrix matrix, PointXY start, Direction startDirection) {
        var energizedFields = new HashSet<PointXY>();
        collectEnergizedFields(matrix, startDirection, start, new HashSet<>(), energizedFields);
        return energizedFields;
    }

    static void collectEnergizedFields(CharacterMatrix matrix, Direction dir, PointXY start, Set<StartingPoint> previousStarts, Set<PointXY> energizedFields) {
        if (!previousStarts.add(new StartingPoint(start, dir))) {
            return;
        }
        var upcomingValues = dir.upcomingFields.apply(matrix, start);
        var upcoming = upcomingValues.stream().skip(1).map(val -> "" + val.value()).collect(Collectors.joining());
        var index = indexOfAny(upcoming, dir.terminatingFields);
        if (index < 0) {
            energizedFields.addAll(upcomingValues.stream().map(CharacterMatrix.Value::coordinates).toList());
            return;
        }
        var nextStart = nextStart(start, dir, index);
        energizedFields.addAll(upcomingValues.stream().map(CharacterMatrix.Value::coordinates).toList().subList(0, index + 1));
        char field = upcoming.charAt(index);
        if (field == '/' || field == '\\') {
            collectEnergizedFields(matrix, reflect(field, dir), nextStart, previousStarts, energizedFields);
        }
        if (field == '|' || field == '-') {
            var splitBeam = split(field, dir);
            collectEnergizedFields(matrix, splitBeam.get(0), nextStart, previousStarts, energizedFields);
            collectEnergizedFields(matrix, splitBeam.get(1), nextStart, previousStarts, energizedFields);
        }
    }

    static Direction reflect(char mirror, Direction dir) {
        if (mirror == '/') {
            return switch (dir) {
                case RIGHT -> UP;
                case DOWN -> LEFT;
                case LEFT -> DOWN;
                case UP -> RIGHT;
            };
        }
        if (mirror == '\\') {
            return switch (dir) {
                case RIGHT -> DOWN;
                case DOWN -> RIGHT;
                case LEFT -> UP;
                case UP -> LEFT;
            };
        }
        throw new ImplementationException("'" + mirror + "' is not a mirror");
    }

    static List<Direction> split(char splitter, Direction dir) {
        if (splitter == '|') {
            return dir == RIGHT || dir == LEFT ? of(UP, DOWN) : of(dir);
        }
        if (splitter == '-') {
            return dir == UP || dir == DOWN ? of(RIGHT, LEFT) : of(dir);
        }
        throw new ImplementationException("'" + splitter + "' is not a splitter");
    }

    static PointXY nextStart(PointXY start, Direction dir, int stop) {
        return switch (dir) {
            case RIGHT -> new PointXY(start.x() + stop + 1, start.y());
            case DOWN -> new PointXY(start.x(), start.y() + stop + 1);
            case LEFT -> new PointXY(start.x() - stop - 1, start.y());
            case UP -> new PointXY(start.x(), start.y() - stop - 1);
        };
    }

    record StartingPoint(PointXY start, Direction direction) {
    }

    @RequiredArgsConstructor
    enum Direction {
        RIGHT("/\\|", (m, p) -> m.row(p.y()).subList(p.x(), m.width)),
        DOWN("/\\-", (m, p) -> m.column(p.x()).subList(p.y(), m.length)),
        LEFT("/\\|", (m, p) -> m.row(p.y()).subList(0, p.x() + 1).reversed()),
        UP("/\\-", (m, p) -> m.column(p.x()).subList(0, p.y() + 1).reversed());

        final String terminatingFields;
        final BiFunction<CharacterMatrix, PointXY, List<CharacterMatrix.Value>> upcomingFields;
    }

    @Override
    public CharacterMatrix mapInput(String[] input) {
        return new CharacterMatrix(input);
    }
}
