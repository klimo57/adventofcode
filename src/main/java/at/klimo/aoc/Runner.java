package at.klimo.aoc;

import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TimeZone;

/**
 * Run <code>mvn compile -q exec:java</code> to run today's puzzle.
 * To implement a solution, create a class implementing the {@link Solution} interface in the package for the
 * respective year (e.g. y2023) with the name "Solution{day}", e.g. Solution1.
 * The solution for the puzzle will be printed on the console
 */
public class Runner {
    
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("Enter year and day for the puzzle you want to run (default: today's puzzle):");
            var yearAndDay = requestPuzzleFromUser();
            var input = new Input(yearAndDay[0], yearAndDay[1]);
            System.out.println(SolutionFactory.forDayInYear(yearAndDay[0], yearAndDay[1]).execute(input.get()));
        } catch (Throwable e) {
            System.exit(handleError(e));
        }
        System.out.println("SUCCESS");
        System.exit(0);
    }

    private static int[] requestPuzzleFromUser() {
        try(var scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();
            // get the current date in EST (EST is used by the creator of AOC) and print today's solution
            var date = LocalDate.now(TimeZone.getTimeZone("EST").toZoneId());
            int year = date.getYear();
            int day = date.getDayOfMonth();
            if(StringUtils.isNotBlank(input)) {
                if(input.contains("/")) {
                    var yearAndDay = input.split("/");
                    year = Integer.parseInt(yearAndDay[0]);
                    day = Integer.parseInt(yearAndDay[1]);
                } else {
                    day = Integer.parseInt(input);
                }
            }
            return new int[]{year, day};
        } catch (NumberFormatException e) {
            System.out.println("Enter a year and date (yyyy/dd), a day (dd) or nothing for the current year and day");
            return requestPuzzleFromUser();
        }
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
