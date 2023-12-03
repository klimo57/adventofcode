package at.klimo.aoc;

import java.util.Arrays;
import java.util.Objects;

public interface Solution<I, T> {
    default String puzzle1(String[] input) throws ImplementationException {
        return mapOutput(solveP1(mapInput(input)));
    }

    default String puzzle2(String[] input) throws ImplementationException {
        return mapOutput(solveP2(mapInput(input)));
    }

    default I mapInput(String[] input) {
        //noinspection unchecked
        return (I) input;
    }

    default String mapOutput(T output) {
        return output.toString();
    }

    T solveP1(I input) throws ImplementationException;

    default T solveP2(I input) throws ImplementationException {
        return solveP1(input);
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
