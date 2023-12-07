package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class Solution7 implements Solution<SortedSet<Solution7.Hand>, Long> {

    @Override
    public SortedSet<Hand> mapInput(String[] input) {
        return new TreeSet<>(
            Arrays.stream(input)
                .map(line -> line.split(" "))
                .map(parts -> new Hand(
                    parts[0].chars().mapToObj(Card::fromChar).toList(),
                    Integer.parseInt(parts[1])
                ))
                .toList()
        );
    }

    @Override
    public Long solveP1(SortedSet<Hand> input) throws ImplementationException {
        var hands = input.iterator();
        long result = 0;
        for (int i = 1; hands.hasNext(); i++) {
            result += i * hands.next().bet;
        }
        return result;
    }

    @Override
    public Long solveP2(SortedSet<Hand> input) throws ImplementationException {
        var sortWithJokers = new TreeSet<>(new HandComparatorWithJoker());
        sortWithJokers.addAll(input);
        return solveP1(sortWithJokers);
    }

    public static class HandComparatorWithJoker implements Comparator<Hand> {

        @Override
        public int compare(Hand o1, Hand o2) {
            int comp = HandType.forHandWithJokers(o1).compareTo(HandType.forHandWithJokers(o2));
            if (comp != 0) {
                return comp;
            }
            for (int i = 0; i < o1.cards.size(); i++) {
                comp = o1.cards.get(i).compareToWithJokers(o2.cards.get(i));
                if (comp != 0) {
                    return comp;
                }
            }
            return 0;
        }
    }

    public static class Hand implements Comparable<Hand> {

        final List<Card> cards;
        final List<Card> sortedCards;
        final int bet;

        Hand(Collection<Card> cards, int bet) {
            if (cards.size() != 5) {
                throw new ImplementationException("a hand must have 5 cards");
            }
            this.cards = List.copyOf(cards);
            this.sortedCards = cards.stream()
                .collect(toMap(identity(), ignored -> 1, (a, b) -> a + b))
                .entrySet()
                .stream()
                .map(Pair::of)
                .sorted(
                    comparingInt((Pair<Card, Integer> l) -> l.getRight())
                        .thenComparing(Pair::getLeft)
                        .reversed()
                )
                .map(Pair::getLeft)
                .toList();
            this.bet = bet;
        }

        @Override
        public int compareTo(Hand o) {
            int comp = HandType.forHand(this).compareTo(HandType.forHand(o));
            if (comp != 0) {
                return comp;
            }
            for (int i = 0; i < cards.size(); i++) {
                comp = cards.get(i).compareTo(o.cards.get(i));
                if (comp != 0) {
                    return comp;
                }
            }
            return 0;
        }


        @Override
        public String toString() {
            return cards.stream().map(c -> "" + c.charRepresentation).collect(Collectors.joining());
        }
    }

    enum HandType {
        HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND;

        static HandType forHand(Hand hand) {
            if (hand.cards.size() != 5) {
                throw new ImplementationException("not a hand: " + hand);
            }
            var grouped = hand.cards.stream().collect(groupingBy(identity()));
            return switch (grouped.size()) {
                case 5 -> HIGH_CARD;
                case 4 -> PAIR;
                case 3 -> grouped.values().stream().map(Collection::size).anyMatch(size -> size >= 3)
                    ? THREE_OF_A_KIND
                    : TWO_PAIR;
                case 2 -> grouped.values().stream().map(Collection::size).anyMatch(size -> size >= 4)
                    ? FOUR_OF_A_KIND
                    : FULL_HOUSE;
                case 1 -> FIVE_OF_A_KIND;
                default -> throw new ImplementationException("impossible scenario: " + hand);
            };
        }

        static HandType forHandWithJokers(Hand hand) {
            if (hand.cards.size() != 5) {
                throw new ImplementationException("not a hand: " + hand);
            }
            var mostCommonCard = hand.cards.stream()
                .filter(c -> c != Card.JACK)
                .collect(groupingBy(identity()))
                .values()
                .stream()
                .max(comparingInt(List::size))
                .map(List::getFirst)
                .orElse(Card.JACK);
            return forHand(new Hand(
                hand.cards.stream()
                    .map(c -> c == Card.JACK ? mostCommonCard : c)
                    .toList(),
                hand.bet
            ));
        }
    }

    @AllArgsConstructor
    enum Card {
        TWO('2'), THREE('3'), FOUR('4'), FIVE('5'),
        SIX('6'), SEVEN('7'), EIGHT('8'), NINE('9'),
        TEN('T'), JACK('J'), QUEEN('Q'), KING('K'),
        ACE('A');

        char charRepresentation;

        int strength() {
            return ordinal() + 2;
        }

        static Card fromChar(int c) {
            return Arrays.stream(values())
                .filter(card -> card.charRepresentation == c)
                .findAny()
                .orElseThrow();
        }

        int compareToWithJokers(Card o) {
            // we are the same
            if (this == o) {
                return 0;
            }
            // none of us are jokers
            if (this != JACK && o != JACK) {
                return compareTo(o);
            }
            // whoever is a joker is less than the other
            return this == JACK ? -1 : 1;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
