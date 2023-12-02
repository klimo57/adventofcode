package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

public class Solution2 implements Solution<Integer> {
    @Override
    public Integer puzzle1(String[] input) throws ImplementationException {
        return stream(input)
            .map(Solution2::readGame)
            .mapToInt(Solution2::applyRules)
            .sum();
    }

    @Override
    public Integer puzzle2(String[] input) throws ImplementationException {
        return stream(input)
            .map(Solution2::readGame)
            .mapToInt(Game::power)
            .sum();
    }

    private static Game readGame(String line) {
        String[] parts = line.split(":");
        int id = Integer.parseInt(parts[0].substring(parts[0].indexOf("Game ") + 5));
        return stream(parts[1].split(";"))
            .map(String::trim)
            .map(cubes -> Solution2.readColors(cubes, id))
            .reduce((left, right) -> new Game(
                left.id,
                Math.max(left.red, right.red),
                Math.max(left.green, right.green),
                Math.max(left.blue, right.blue)
            ))
            .orElseThrow();
    }

    private static Game readColors(String randomCubes, int id) {
        var cubesByColor = stream(randomCubes.split(","))
            .map(String::trim)
            .collect(toMap(
                cubes -> cubes.split(" ")[1],
                cubes -> Integer.parseInt(cubes.split(" ")[0]))
            );
        return new Game(
            id,
            cubesByColor.getOrDefault("red", 0),
            cubesByColor.getOrDefault("green", 0),
            cubesByColor.getOrDefault("blue", 0)
        );
    }

    private static int applyRules(Game game) {
        return game.red <= 12 && game.green <= 13 && game.blue <= 14 ? game.id : 0;
    }

    private record Game(int id, int red, int green, int blue) {
        private int power() {
            return red * green * blue;
        }
    }
}
