package at.klimo.aoc;

import java.util.Arrays;
import java.util.Objects;

@FunctionalInterface
public interface Solution<T> {
    T puzzle1(String[] input) throws ImplementationException;

    default T puzzle2(String[] input) throws ImplementationException {
        return puzzle1(input);
    }
}

class SolutionFactory {
    static Solution forDayInYear(int year, int day) throws ReflectiveOperationException {
        Class<?> solution = Class.forName("at.klimo.aoc.y" + year + ".Solution" + day);
        Arrays.stream(solution.getInterfaces())
            .filter(c -> Objects.equals(c, Solution.class))
            .findAny()
            .orElseThrow(() -> new ClassNotFoundException("Found class " + solution.getSimpleName() + " but its not an implementation of Solution"));
        return (Solution) solution.getConstructor().newInstance();
    }
}
