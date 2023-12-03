package at.klimo.aoc.util;

public record PointXY(int x, int y) {
    @Override
    public String toString() {
        return "P[" + x + "|" + y + "]";
    }
}
