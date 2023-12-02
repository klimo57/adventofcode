package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

public class Solution2 implements Solution<Integer> {

    /**
     * puzzle 1 looks for games with a bag that could contain 12 red, 13 green and 14 blue cubes
     * the solution is the sum of the games ids
     */
    @Override
    public Integer puzzle1(String[] input) throws ImplementationException {
        return stream(input)
            .map(Solution2::readGame)
            .filter(game -> game.red <= 12 && game.green <= 13 && game.blue <= 14)
            .mapToInt(Game::id)
            .sum();
    }

    /**
     * puzzle 2 looks for the fewest number of cubes of each color that could have been in the bag
     * the solution is the sum of the products of the number of cubes of each game
     */
    @Override
    public Integer puzzle2(String[] input) throws ImplementationException {
        return stream(input)
            .map(Solution2::readGame)
            .mapToInt(Game::power)
            .sum();
    }

    /**
     * reads a line and converts it to a game with an id and the fewest number of cubes of each color
     * that could have been in the bag
     */
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

    /**
     * returns a game for each of the shown cubes
     */
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

    /**
     * the data of a game
     *
     * @param id    the game's id
     * @param red   the number of red cubes
     * @param green the number of green cubes
     * @param blue  the number of blue cubes
     */
    private record Game(int id, int red, int green, int blue) {
        /**
         * @return the product of the number of red, green and blue cubes
         */
        private int power() {
            return red * green * blue;
        }
    }
}
