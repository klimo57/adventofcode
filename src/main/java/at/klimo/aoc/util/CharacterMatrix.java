package at.klimo.aoc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CharacterMatrix {

    private final char[][] matrix;

    public final int length;
    public final int width;

    public CharacterMatrix(String[] input) {
        this(Arrays.stream(input).map(String::toCharArray).toArray(char[][]::new));
    }

    public CharacterMatrix(char[][] matrix) {
        this.matrix = matrix;
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

    public List<Value> row(int index) {
        return IntStream.range(0, width)
            .mapToObj(i -> new PointXY(i, index))
            .map(p -> new Value(at(p), p))
            .toList();
    }

    public List<Value> column(int index) {
        return IntStream.range(0, length)
            .mapToObj(i -> new PointXY(index, i))
            .map(p -> new Value(at(p), p))
            .toList();
    }

    public CharacterMatrix insertRow(char[] chars, int index, char filler) {
        char[] newLine = Arrays.copyOf(chars, width);
        if (chars.length < width) {
            Arrays.fill(newLine, chars.length, newLine.length, filler);
        }
        char[][] newMatrix = new char[length + 1][width];
        for (int i = 0; i < newMatrix.length; i++) {
            if (i < index) {
                newMatrix[i] = matrix[i];
            }
            if (i == index) {
                newMatrix[i] = newLine;
            }
            if (i > index) {
                newMatrix[i] = matrix[i - 1];
            }
        }
        return new CharacterMatrix(newMatrix);
    }

    public CharacterMatrix insertColumn(char[] chars, int index, char filler) {
        char[] newColumn = Arrays.copyOf(chars, length);
        if (chars.length < length) {
            Arrays.fill(newColumn, chars.length, newColumn.length, filler);
        }
        char[][] newMatrix = new char[length][width + 1];
        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[i].length; j++) {
                if (j < index) {
                    newMatrix[i][j] = matrix[i][j];
                }
                if (j == index) {
                    newMatrix[i][j] = newColumn[i];
                }
                if (j > index) {
                    newMatrix[i][j] = matrix[i][j - 1];
                }
            }
        }
        return new CharacterMatrix(newMatrix);
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
