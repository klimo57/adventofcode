package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import at.klimo.aoc.util.CharacterMatrix;
import at.klimo.aoc.util.PointXY;

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
        return input.stream()
            .map(CharacterMatrix.Value::coordinates)
            .filter(not(pipeline::contains))
            .filter(pipeline::encloses)
            .count();
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
        static final List<Pipe> pipesToCountForConfinement = List.of(Pipe.NE_BEND, Pipe.NW_BEND, Pipe.SW_BEND, Pipe.SE_BEND);

        final List<Tile> tiles;
        final Function<PointXY, Tile> tileAt;

        Pipeline(List<Tile> tiles, Function<PointXY, CharacterMatrix.Value> tileAt) {
            this.tiles = tiles;
            this.tileAt = tileAt.andThen(Tile::new);
        }

        boolean contains(PointXY t) {
            return tiles.contains(new Tile(Pipe.GROUND, t));
        }

        boolean encloses(PointXY p) {
            var loop = tiles.stream().map(Tile::coordinates).toList();
            var up = isConfinedUpwards(p, loop);
            var right = isConfinedRightwards(p, loop);
            var down = isConfinedDownwards(p, loop);
            var left = isConfinedLeftwards(p, loop);
            var encloses = isConfinedUpwards(p, loop) &&
                isConfinedRightwards(p, loop) &&
                isConfinedDownwards(p, loop) &&
                isConfinedLeftwards(p, loop);
            return encloses;
        }

        boolean isConfinedUpwards(PointXY p, List<PointXY> loop) {
            var loopTilesToBorder = 0;
            for (int i = p.y() - 1; i >= 0; i--) {
                var toCheck = new PointXY(p.x(), i);
                var tile = tileAt.apply(toCheck);
                if (loop.contains(toCheck) && tile.pipe != Pipe.VERTICAL) {
                    loopTilesToBorder++;
                }
            }
            return loopTilesToBorder % 2 == 1;
        }

        boolean isConfinedRightwards(PointXY p, List<PointXY> loop) {
            var loopTilesToBorder = 0;
            var rightmostPointOfLoop = loop.stream().mapToInt(PointXY::x).max().orElse(0);
            for (int i = p.x() + 1; i <= rightmostPointOfLoop; i++) {
                var toCheck = new PointXY(i, p.y());
                var tile = tileAt.apply(toCheck);
                if (loop.contains(toCheck) && tile.pipe != Pipe.HORIZONTAL) {
                    loopTilesToBorder++;
                }
            }
            return loopTilesToBorder % 2 == 1;
        }

        boolean isConfinedDownwards(PointXY p, List<PointXY> loop) {
            var loopTilesToBorder = 0;
            var lowestPointOfLoop = loop.stream().mapToInt(PointXY::y).max().orElse(0);
            for (int i = p.y() + 1; i <= lowestPointOfLoop; i++) {
                var toCheck = new PointXY(p.x(), i);
                var tile = tileAt.apply(toCheck);
                if (loop.contains(toCheck) && tile.pipe != Pipe.VERTICAL) {
                    loopTilesToBorder++;
                }
            }
            return loopTilesToBorder % 2 == 1;
        }

        boolean isConfinedLeftwards(PointXY p, List<PointXY> loop) {
            var loopTilesToBorder = 0;
            for (int i = p.x() - 1; i >= 0; i--) {
                var toCheck = new PointXY(i, p.y());
                var tile = tileAt.apply(toCheck);
                if (loop.contains(toCheck) && tile.pipe != Pipe.HORIZONTAL) {
                    loopTilesToBorder++;
                }
            }
            return loopTilesToBorder % 2 == 1;
        }
    }

    record Tile(Pipe pipe, PointXY coordinates) {
        Tile(CharacterMatrix.Value val) {
            this(Pipe.fromType(val.value()), val.coordinates());
        }

        int x() {
            return coordinates.x();
        }

        int y() {
            return coordinates.y();
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
