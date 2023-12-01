package at.klimo.aoc;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;

@FunctionalInterface
public interface Solution {
    String execute(String[] input) throws ImplementationException;
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
