package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Solution15 implements Solution<String[], Integer> {

    @Override
    public Integer solveP1(String[] input) throws ImplementationException {
        return Arrays.stream(input)
            .mapToInt(Solution15::hash)
            .sum();
    }

    @Override
    public Integer solveP2(String[] input) throws ImplementationException {
        var boxes = new Box[256];
        for (var op : input) {
            if (op.contains("=")) {
                var lens = new Lens(op.split("="));
                if (boxes[lens.hash] == null) {
                    boxes[lens.hash] = new Box(lens.hash, lens);
                } else {
                    boxes[lens.hash].add(lens);
                }
            } else if (op.contains("-")) {
                var label = op.substring(0, op.length() - 1);
                var hash = hash(label);
                if (boxes[hash] != null) {
                    boxes[hash].remove(label);
                }
            }
        }
        return Arrays.stream(boxes).filter(Objects::nonNull).mapToInt(Box::focusingPower).sum();
    }

    static int hash(String s) {
        return s.chars().reduce(0, (acc, next) -> ((acc + next) * 17) % 256);
    }

    record Box(int id, List<Lens> lenses) {
        Box(int id, Lens lens) {
            this(id, new ArrayList<>(List.of(lens)));
        }

        void add(Lens lens) {
            int index = indexOf(lens.label);
            if (index < 0) {
                lenses.addLast(lens);
            } else {
                lenses.remove(index);
                lenses.add(index, lens);
            }
        }

        void remove(String label) {
            int index = indexOf(label);
            if (index >= 0) {
                lenses.remove(index);
            }
        }

        int indexOf(String label) {
            for (int i = 0; i < lenses.size(); i++) {
                if (Objects.equals(lenses.get(i).label, label)) {
                    return i;
                }
            }
            return -1;
        }

        int focusingPower() {
            return IntStream.range(0, lenses.size()).map(i -> (id + 1) * (i + 1) * lenses.get(i).focalLength).sum();
        }
    }

    record Lens(String label, int focalLength, int hash) {
        Lens(String[] opParts) {
            this(opParts[0], Integer.parseInt(opParts[1]), Solution15.hash(opParts[0]));
        }
    }

    @Override
    public String[] mapInput(String[] input) {
        return input[0].split(",");
    }
}
