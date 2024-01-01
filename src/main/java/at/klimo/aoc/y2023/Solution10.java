package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import at.klimo.chars.CharacterMatrix;
import at.klimo.common.PointXY;
import org.apache.commons.lang3.Range;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static java.util.List.of;
import static java.util.function.Predicate.not;

public class Solution10 implements Solution<CharacterMatrix, Long> {
    @Override
    public Long solveP1(CharacterMatrix input) throws ImplementationException {
        return createPipeline(input, start(input)).tiles.size() / 2L;
    }

    @Override
    public Long solveP2(CharacterMatrix input) throws ImplementationException {
        var pipeline = createPipeline(input, start(input));
        var enclosingRanges = enclosingRanges(input, pipeline);
        var enclosedPoints = input.stream()
            .map(CharacterMatrix.Value::coordinates)
            .filter(not(pipeline::contains))
            .filter(p -> enclosingRanges.stream().anyMatch(range -> range.contains(p)))
            .toList();
        return (long) enclosedPoints.size();
    }

    static List<Range<PointXY>> enclosingRanges(CharacterMatrix matrix, Pipeline pipeline) {
        var enclosingRanges = new ArrayList<Range<PointXY>>();
        for (int i = 1; i < matrix.length - 1; i++) {
            PointXY rangeStart = null;
            for (int j = 0; j < matrix.width; j++) {
                var cur = new PointXY(j, i);
                var below = new PointXY(j, i + 1);
                if (pipeline.contains(cur) &&
                    pipeline.contains(below) &&
                    Math.abs(pipeline.indexOf(cur) - pipeline.indexOf(below)) == 1) {
                    if (rangeStart == null) {
                        rangeStart = cur;
                    } else {
                        enclosingRanges.add(Range.between(rangeStart, cur));
                        rangeStart = null;
                    }
                }
            }
        }
        return enclosingRanges;
    }

    static Tile start(CharacterMatrix matrix) {
        return matrix.stream()
            .filter(val -> val.value() == 'S')
            .findAny()
            .map(Tile::new)
            .orElseThrow();
    }

    static Pipeline createPipeline(CharacterMatrix matrix, Tile start) {
        var tiles = new ArrayList<Tile>();
        Tile last = null;
        var cur = start;
        do {
            tiles.add(cur);
            var tmp = cur;
            cur = nextStep(matrix, cur, last);
            last = tmp;
        } while (!Objects.equals(cur.coordinates, start.coordinates));
        return new Pipeline(tiles, matrix::valueAt);
    }

    static Tile nextStep(CharacterMatrix matrix, Tile cur, Tile last) {
        var neighbours = matrix.neighbours(cur.coordinates);
        var next = neighbours.stream()
            .filter(n -> last == null || !Objects.equals(last.coordinates, n.coordinates()))
            .map(Tile::new)
            .filter(t -> cur.isConnected(t, matrix))
            .findAny();
        return next.orElseThrow();
    }

    static class Pipeline {
        final List<Tile> tiles;
        final Function<PointXY, Tile> tileAt;

        Pipeline(List<Tile> tiles, Function<PointXY, CharacterMatrix.Value> tileAt) {
            this.tiles = tiles;
            this.tileAt = tileAt.andThen(Tile::new);
        }

        boolean contains(PointXY t) {
            return tiles.contains(new Tile(Pipe.GROUND, t));
        }

        int indexOf(PointXY p) {
            return tiles.indexOf(tileAt.apply(p));
        }
    }

    record Tile(Pipe pipe, PointXY coordinates) {
        Tile(CharacterMatrix.Value val) {
            this(Pipe.fromType(val.value()), val.coordinates());
        }

        boolean isConnected(Tile other, CharacterMatrix matrix) {
            return pipe.connectedFields.apply(coordinates, matrix).contains(other.coordinates) &&
                other.pipe.connectedFields.apply(other.coordinates, matrix).contains(coordinates);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tile tile = (Tile) o;
            return Objects.equals(coordinates, tile.coordinates);
        }

        @Override
        public int hashCode() {
            return Objects.hash(coordinates);
        }
    }

    enum Pipe {
        VERTICAL('|', (p, m) -> of(
            p.neighbours(m.width, m.length).upper(),
            p.neighbours(m.width, m.length).lower()
        )),
        HORIZONTAL('-', (p, m) -> of(
            p.neighbours(m.width, m.length).left(),
            p.neighbours(m.width, m.length).right()
        )),
        NE_BEND('L', (p, m) -> of(
            p.neighbours(m.width, m.length).upper(),
            p.neighbours(m.width, m.length).right()
        )),
        NW_BEND('J', (p, m) -> of(
            p.neighbours(m.width, m.length).upper(),
            p.neighbours(m.width, m.length).left()
        )),
        SW_BEND('7', (p, m) -> of(
            p.neighbours(m.width, m.length).lower(),
            p.neighbours(m.width, m.length).left()
        )),
        SE_BEND('F', (p, m) -> of(
            p.neighbours(m.width, m.length).lower(),
            p.neighbours(m.width, m.length).right()
        )),
        GROUND('.', (p, m) -> emptyList()),
        START('S', (p, m) -> of(
            p.neighbours(m.width, m.length).upper(),
            p.neighbours(m.width, m.length).lower(),
            p.neighbours(m.width, m.length).left(),
            p.neighbours(m.width, m.length).right()
        ));

        final char type;

        final BiFunction<PointXY, CharacterMatrix, List<PointXY>> connectedFields;

        Pipe(char type, BiFunction<PointXY, CharacterMatrix, List<Optional<PointXY>>> connectedFields) {
            this.type = type;
            this.connectedFields = connectedFields.andThen(
                list -> list.stream().filter(Optional::isPresent).map(Optional::get).toList());
        }

        static Pipe fromType(char type) {
            return Arrays.stream(values())
                .filter(pipe -> pipe.type == type)
                .findAny()
                .orElseThrow();
        }
    }

    @Override
    public CharacterMatrix mapInput(String[] input) {
        return new CharacterMatrix(input);
    }
}
