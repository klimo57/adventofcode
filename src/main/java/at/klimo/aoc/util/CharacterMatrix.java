package at.klimo.aoc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CharacterMatrix {

    private final char[][] matrix;

    public final int length;
    public final int width;

    public CharacterMatrix(String[] input) {
        this.matrix = Arrays.stream(input).map(String::toCharArray).toArray(char[][]::new);
        length = matrix.length;
        width = length > 0 ? matrix[0].length : 0;
    }

    public Value valueAt(PointXY coordinates) {
        return new Value(at(coordinates), coordinates);
    }

    public char at(PointXY coordinates) {
        return at(coordinates.x(), coordinates.y());
    }

    public char at(int x, int y) {
        return matrix[y][x];
    }

    public List<Value> neighbours(PointXY coordinates) {
        var neighbours = new ArrayList<Value>();
        for (int i = coordinates.y() - 1; i <= coordinates.y() + 1; i++) {
            for (int j = coordinates.x() - 1; j <= coordinates.x() + 1; j++) {
                if (j >= 0 && j < width && i >= 0 && i < length && !(j == coordinates.x() && i == coordinates.y())) {
                    neighbours.add(new Value(at(j, i), new PointXY(j, i)));
                }
            }
        }
        return neighbours;
    }

    public Predicate<PointXY> includes() {
        return coordinates ->
            coordinates.x() >= 0 && coordinates.x() < width &&
                coordinates.y() >= 0 && coordinates.y() < length;
    }

    public Stream<Value> stream() {
        var values = new ArrayList<Value>(length * width);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                values.add(new Value(at(j, i), new PointXY(j, i)));
            }
        }
        return values.stream();
    }

    public record Value(char value, PointXY coordinates) {
    }
}
