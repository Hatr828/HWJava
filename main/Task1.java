import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class Task1 {
    private static final Predicate<Integer> IS_LEAP = year -> Year.isLeap(year);
    private static final BiFunction<LocalDate, LocalDate, Long> DAYS_BETWEEN =
            (start, end) -> Math.abs(ChronoUnit.DAYS.between(start, end));
    private static final BiFunction<LocalDate, LocalDate, Long> FULL_SUNDAYS_BETWEEN = (start, end) -> {
        LocalDate from = start.isAfter(end) ? end : start;
        LocalDate to = start.isAfter(end) ? start : end;

        LocalDate firstSunday = from.plusDays(
                (DayOfWeek.SUNDAY.getValue() - from.getDayOfWeek().getValue() + 7) % 7);
        if (firstSunday.isAfter(to)) {
            return 0L;
        }

        long weeks = ChronoUnit.DAYS.between(firstSunday, to) / 7;
        return weeks + 1;
    };
    private static final Function<LocalDate, DayOfWeek> DAY_OF_WEEK = LocalDate::getDayOfWeek;

    public static void run() {
        System.out.println("Task 1:");

        int sampleYear = 2024;
        LocalDate apolloLanding = LocalDate.of(1969, 7, 20);
        LocalDate now = LocalDate.now();

        System.out.println(sampleYear + " leap year? " + IS_LEAP.test(sampleYear));
        System.out.println("Days between " + apolloLanding + " and " + now + ": "
                + DAYS_BETWEEN.apply(apolloLanding, now));
        System.out.println("Full Sundays between " + apolloLanding + " and " + now + ": "
                + FULL_SUNDAYS_BETWEEN.apply(apolloLanding, now));
        System.out.println(apolloLanding + " was " + DAY_OF_WEEK.apply(apolloLanding));
        System.out.println();
    }
}
