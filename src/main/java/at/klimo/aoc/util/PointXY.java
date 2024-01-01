package at.klimo.aoc.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public record PointXY(int x, int y) implements Comparable<PointXY> {

    public Neighbours neighbours(int gridWidth, int gridLength) {
        return new Neighbours(this, gridWidth, gridLength);
    }

    public Neighbours neighbours(CharacterMatrix matrix) {
        return neighbours(matrix.width, matrix.length);
    }

    @Override
    public int compareTo(PointXY o) {
        return Comparator.comparing(PointXY::y).thenComparing(PointXY::x).compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointXY pointXY = (PointXY) o;
        return x == pointXY.x && y == pointXY.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "P[" + x + "|" + y + "]";
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Neighbours {
        private final PointXY home;
        private final int gridWidth;
        private final int gridLength;

        public List<PointXY> all() {
            return List.of(
                    upperLeft(), upper(), upperRight(),
                    left(), right(),
                    lowerLeft(), lower(), lowerRight()
                ).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        }

        public List<PointXY> straight() {
            return List.of(upper(), lower(), left(), right())
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        }

        public Optional<PointXY> upperLeft() {
            if (home.x < 1 || home.y < 1) {
                return empty();
            }
            return of(new PointXY(home.x - 1, home.y - 1));
        }

        public Optional<PointXY> upper() {
            if (home.y < 1) {
                return empty();
            }
            return of(new PointXY(home.x, home.y - 1));
        }

        public Optional<PointXY> upperRight() {
            if (home.y < 1 || home.x >= gridWidth - 2) {
                return empty();
            }
            return of(new PointXY(home.x + 1, home.y - 1));
        }

        public Optional<PointXY> left() {
            if (home.x < 1) {
                return empty();
            }
            return of(new PointXY(home.x - 1, home.y));
        }

        public Optional<PointXY> right() {
            if (home.x >= gridWidth - 1) {
                return empty();
            }
            return of(new PointXY(home.x + 1, home.y));
        }

        public Optional<PointXY> lowerLeft() {
            if (home.x < 1 || home.y >= gridLength - 1) {
                return empty();
            }
            return of(new PointXY(home.x - 1, home.y + 1));
        }

        public Optional<PointXY> lower() {
            if (home.y >= gridLength - 1) {
                return empty();
            }
            return of(new PointXY(home.x, home.y + 1));
        }

        public Optional<PointXY> lowerRight() {
            if (home.x >= gridWidth - 1 || home.y >= gridLength - 1) {
                return empty();
            }
            return of(new PointXY(home.x + 1, home.y + 1));
        }
    }
}
