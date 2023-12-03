package at.klimo.aoc.util;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Class to search for patterns in a string. The search can be configured like an Object in a builder pattern.
 * Besides the search pattern, it is configurable which part (e.g. char/string before the matched pattern) should be the
 * result and also if and how the resulting string should be mutated
 * <p>
 * TODO
 *  - add result configurations (e.g. after, self, index, endIndex)
 *  - add search configurations (e.g. forWord, forAnyDigit, forAnyNumber, ...)
 *
 * @param <T> type of the result
 */
public class StringSearch<T> {

    private Function<String, Stream<T>> searchFn;
    private BiFunction<String, Integer, Optional<String>> resultFn;
    private final Function<String, T> mutateFn;

    private StringSearch(Function<String, T> mutateFn) {
        this.mutateFn = mutateFn;
    }

    public StringSearch getBefore(int count) {
        this.resultFn = (s, i) -> i - count >= 0
            ? of(s.substring(i - count, i))
            : empty();
        return this;
    }

    public StringSearch forChar(char c) {
        this.searchFn = s -> IntStream.range(0, s.length())
            .filter(i -> s.charAt(i) == c)
            .mapToObj(i -> resultFn.apply(s, i))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(mutateFn);
        return this;
    }

    public List<T> evaluate(String s) {
        return searchFn.apply(s).toList();
    }

    public Stream<T> resultStream(String s) {
        return this.searchFn.apply(s);
    }

    public static <T> StringSearch<T> search(String s, Function<String, T> mutateFn) {
        return new StringSearch(mutateFn);
    }
}
