package at.klimo.aoc;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.TimeZone;

/**
 * Run <code>mvn compile -q exec:java</code> to run today's puzzle.
 * To implement a solution, create a class implementing the {@link Solution} interface in the package for the
 * respective year (e.g. y2023) with the name "Solution{day}", e.g. Solution1.
 * The solution for the puzzle will be printed on the console
 */
public class Runner {

    public static void main(String[] args) {
        try {
            // get the current date in EST (EST is used by the creator of AOC) and print today's solution
            var date = LocalDate.now(TimeZone.getTimeZone("EST").toZoneId());
            int year = date.getYear();
            int day = date.getDayOfMonth();
            var input = new Input(year, day);
            System.out.println(SolutionFactory.forDayInYear(year, day).execute(input.get()));
        } catch (Throwable e) {
            System.exit(handleError(e));
        }
        System.out.println("SUCCESS");
        System.exit(0);
    }

    @SuppressWarnings("SwitchLabeledRuleCanBeCodeBlock")
    private static int handleError(Throwable error) {
        //noinspection CallToPrintStackTrace
        error.printStackTrace();
        return switch (error) {
            case ReflectiveOperationException noSolutionFoundError -> 2;
            case MalformedURLException inputRequestError -> 3;
            case ImplementationException implementationError -> 4;
            case Error fatalError -> 130;
            case null, default -> 1;
        };
    }
}
