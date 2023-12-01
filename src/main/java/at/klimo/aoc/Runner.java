package at.klimo.aoc;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.TimeZone;

public class Runner {

    public static void main(String[] args) {
        try {
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
