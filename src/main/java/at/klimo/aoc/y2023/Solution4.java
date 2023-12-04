package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;

import static java.util.stream.Collectors.toSet;

public class Solution4 implements Solution<String[], Integer> {

    @Override
    public Integer solveP1(String[] input) throws ImplementationException {
        return Arrays.stream(input)
            .map(Solution4::readCard)
            .mapToInt(ScratchCard::points)
            .sum();
    }

    @Override
    public Integer solveP2(String[] input) throws ImplementationException {
        return countCopies(new TreeSet<>(
            Arrays.stream(input)
                .map(Solution4::readCard)
                .toList()
        ));
    }

    public static int countCopies(SortedSet<ScratchCard> cards) {
        int cardCount = 0;
        var it = cards.iterator();
        List<Integer> copyNext = new ArrayList<>();
        for (int i = 0; it.hasNext(); i++) {
            var card = it.next();
            var winCount = card.winCount();
            int copies = copyNext.size() + 1;
            copyNext = new ArrayList<>(
                copyNext.stream()
                    .map(counter -> counter - 1)
                    .filter(counter -> counter > 0)
                    .toList()
            );
            cardCount += copies;
            if (winCount > 0) {
                for (int j = 0; j < copies; j++) {
                    copyNext.add(card.winCount());
                }
            }
        }
        return cardCount;
    }

    public static ScratchCard readCard(String line) {
        var parts = line.split(":");
        var nrs = parts[1].split(" \\| ");
        return new ScratchCard(
            Integer.parseInt(parts[0].replaceAll("Card", "").trim()),
            Arrays.stream(nrs[0].split(" "))
                .map(String::trim)
                .filter(NumberUtils::isCreatable)
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(toSet()),
            Arrays.stream(nrs[1].split(" "))
                .map(String::trim)
                .filter(NumberUtils::isCreatable)
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(toSet())
        );
    }

    public record ScratchCard(int id, Set<Integer> winners, Set<Integer> numbers) implements Comparable<ScratchCard> {
        @Override
        public int compareTo(ScratchCard o) {
            return Integer.valueOf(id).compareTo(Integer.valueOf(o.id));
        }

        public int winCount() {
            var numbersCopy = new HashSet<>(numbers);
            numbersCopy.retainAll(winners);
            return numbersCopy.size();
        }

        public int points() {
            return (int) Math.pow(2, winCount() - 1);
        }
    }
}
