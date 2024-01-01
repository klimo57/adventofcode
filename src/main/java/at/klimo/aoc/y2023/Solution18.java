package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import at.klimo.aoc.util.CharacterMatrix;
import at.klimo.aoc.util.PointXY;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.IntStream;

import static at.klimo.aoc.y2023.Solution18.Direction.*;
import static java.lang.Math.max;
import static org.apache.commons.lang3.BooleanUtils.toInteger;

public class Solution18 implements Solution<Solution18.Dig, Integer> {

    @Override
    public Integer solveP1(Dig input) throws ImplementationException {
        return area(input.corners);
    }

    @Override
    public Integer solveP2(Dig input) throws ImplementationException {
        return null;
    }

    static int area(List<PointXY> vertices) {
        if (vertices.size() < 3) {
            return 0;
        }
        int n = vertices.size();
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int xi = vertices.get(i).x();
            int yip1 = vertices.get((i + 1) % n).y();
            int yim1 = vertices.get((i + n - 1) % n).y();
            sum += vertices.get(i).x() * (vertices.get((i + 1) % n).y() - vertices.get((i + n - 1) % n).y());
        }
        return Math.abs(sum) / 2;
//        return Math.abs(
//            vertices.getFirst().x() * (vertices.get(1).y() - vertices.getLast().y()) +
//                IntStream.range(1, vertices.size() - 1)
//                    .map(i -> vertices.get(i).x() * (vertices.get(i + 1).y() - vertices.get(i - 1).y()))
//                    .sum() +
//                vertices.getLast().x() * (vertices.getFirst().y() - vertices.get(vertices.size() - 2).y())
//        ) / 2;
    }

    record Dig(CharacterMatrix digSite, List<PointXY> corners, Map<String, List<PointXY>> edgeColors) {
    }

    @AllArgsConstructor
    enum Direction {
        RIGHT('R'),
        LEFT('L'),
        UP('U'),
        DOWN('D');

        final char id;

        static Direction fromId(char id) {
            return Arrays.stream(values())
                .filter(dir -> dir.id == id)
                .findAny()
                .orElseThrow();
        }

        static Direction oppositeOf(Direction dir) {
            return switch (dir) {
                case RIGHT -> LEFT;
                case LEFT -> RIGHT;
                case DOWN -> UP;
                case UP -> DOWN;
            };
        }
    }

    @Override
    public Dig mapInput(String[] input) {
        var inputs = Arrays.stream(input)
            .map(line -> line.split(" "))
            .map(Input::new)
            .toList();
        var dimensions = calculateDimension(
            inputs,
            inputs.stream()
                .map(Input::dir)
                .filter(dir -> dir == RIGHT || dir == LEFT)
                .findFirst()
                .orElseThrow(),
            inputs.stream()
                .map(Input::dir)
                .filter(dir -> dir == DOWN || dir == UP)
                .findFirst()
                .orElseThrow()
        );
        return createAndFillDig(
            inputs,
            new PointXY(dimensions.getLeft() / 2, dimensions.getRight() / 2),
            dimensions.getLeft(),
            dimensions.getRight()
        );
    }

    static Dig createAndFillDig(List<Input> inputs, PointXY start, int width, int length) {
        var edgeColors = new HashMap<String, List<PointXY>>();
        var corners = new ArrayList<PointXY>(inputs.size());
        var lastPos = start; // start in the middle
        var matrix = new CharacterMatrix(
            IntStream.range(0, length)
                .mapToObj(i -> StringUtils.repeat('.', width))
                .toList()
        );
        matrix = matrix.replaceCharAt(lastPos, '#');

        for (var digLine : inputs) {
            var edges = new ArrayList<PointXY>(digLine.distance);
            try {
                if (digLine.dir == RIGHT) {
                    int i = lastPos.x() + 1;
                    for (; i <= lastPos.x() + digLine.distance; i++) {
                        var coordinates = new PointXY(i, lastPos.y());
                        matrix = matrix.replaceCharAt(coordinates, '#');
                        edges.add(coordinates);
                    }
                    lastPos = new PointXY(i - 1, lastPos.y());
                }
                if (digLine.dir == LEFT) {
                    int i = lastPos.x() - 1;
                    for (; i >= lastPos.x() - digLine.distance; i--) {
                        var coordinates = new PointXY(i, lastPos.y());
                        matrix = matrix.replaceCharAt(coordinates, '#');
                        edges.add(coordinates);
                    }
                    lastPos = new PointXY(i + 1, lastPos.y());
                }
            } catch (IndexOutOfBoundsException e) {
                return createAndFillDig(inputs, new PointXY(width, start.y()), width * 2, length);
            }
            try {
                if (digLine.dir == DOWN) {
                    int i = lastPos.y() + 1;
                    for (; i <= lastPos.y() + digLine.distance; i++) {
                        var coordinates = new PointXY(lastPos.x(), i);
                        matrix = matrix.replaceCharAt(coordinates, '#');
                        edges.add(coordinates);
                    }
                    lastPos = new PointXY(lastPos.x(), i - 1);
                }
                if (digLine.dir == UP) {
                    int i = lastPos.y() - 1;
                    for (; i >= lastPos.y() - digLine.distance; i--) {
                        var coordinates = new PointXY(lastPos.x(), i);
                        matrix = matrix.replaceCharAt(coordinates, '#');
                        edges.add(coordinates);
                    }
                    lastPos = new PointXY(lastPos.x(), i + 1);
                }
            } catch (IndexOutOfBoundsException e) {
                return createAndFillDig(inputs, new PointXY(start.x(), length), width, length * 2);
            }
            corners.add(lastPos);
            edgeColors.put(digLine.colorCode, edges);
        }

        int minX = matrix.stream().filter(val -> val.value() == '#').mapToInt(val -> val.coordinates().x()).min().orElseThrow();
        int maxX = matrix.stream().filter(val -> val.value() == '#').mapToInt(val -> val.coordinates().x()).max().orElseThrow();
        int minY = matrix.stream().filter(val -> val.value() == '#').mapToInt(val -> val.coordinates().y()).min().orElseThrow();
        int maxY = matrix.stream().filter(val -> val.value() == '#').mapToInt(val -> val.coordinates().y()).max().orElseThrow();
        if (minX > 0 || maxX < matrix.width - 1 || minY > 0 || maxY < matrix.length - 1) {
            return createAndFillDig(inputs, new PointXY(start.x() - minX, start.y() - minY), maxX - minX + 1, maxY - minY + 1);
        }

        return new Dig(matrix, corners, edgeColors);
    }

    static Pair<Integer, Integer> calculateDimension(List<Input> inputs, Direction horizontalStart, Direction verticalStart) {
        int widthR = 0, widthL = 0;
        int lengthD = 0, lengthU = 0;
        int rSum = 0, lSum = 0, dSum = 0, uSum = 0;
        boolean addToR = false, addToL = false, addToD = false, addToU = false;
        for (var digLine : inputs) {
            switch (digLine.dir) {
                case RIGHT:
                    rSum = rSum * toInteger(addToR) + digLine.distance;
                    addToR = true;
                    addToL = false;
                    widthR = max(widthR, rSum);
                    break;
                case LEFT:
                    lSum = lSum * toInteger(addToL) + digLine.distance;
                    addToL = true;
                    addToR = false;
                    widthL = max(widthL, lSum);
                    break;
                case DOWN:
                    dSum = dSum * toInteger(addToD) + digLine.distance;
                    addToD = true;
                    addToU = false;
                    lengthD = max(lengthD, dSum);
                    break;
                case UP:
                    uSum = uSum * toInteger(addToU) + digLine.distance;
                    addToU = true;
                    addToD = false;
                    lengthU = max(lengthU, uSum);
                    break;
            }
        }
        return Pair.of(max(widthR, widthL) * 2, max(lengthD, lengthU) * 2);
    }

    static PointXY startPosition(List<Input> inputs, int matrixWidth, int matrixLength) {
        return new PointXY(
            inputs.stream()
                .map(Input::dir)
                .filter(dir -> dir == RIGHT || dir == LEFT)
                .findFirst()
                .map(dir -> dir == RIGHT ? 0 : matrixWidth - 1)
                .orElseThrow(),
            inputs.stream()
                .map(Input::dir)
                .filter(dir -> dir == DOWN || dir == UP)
                .findFirst()
                .map(dir -> dir == DOWN ? 0 : matrixLength - 1)
                .orElseThrow()
        );
    }

    record Input(Direction dir, int distance, String colorCode) {
        Input(String[] args) {
            this(Direction.fromId(args[0].charAt(0)), Integer.parseInt(args[1]), args[2].replaceAll("\\(|\\)", ""));
        }
    }
}
