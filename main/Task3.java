import java.time.Year;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Task3 {
    private static final List<Television> INVENTORY = Arrays.asList(
            new Television("Aurora X1", Year.now().getValue(), 1199.0, 55.0, "LG", 8),
            new Television("Aurora Mini", Year.now().getValue(), 599.0, 43.0, "LG", 7),
            new Television("Crystal 8K", 2023, 1999.0, 65.0, "Samsung", 9),
            new Television("Crystal 4K", 2022, 899.0, 50.0, "Samsung", 7),
            new Television("Bravia Pro", 2024, 1799.0, 65.0, "Sony", 10),
            new Television("Bravia Light", 2022, 749.0, 50.0, "Sony", 6),
            new Television("NanoVision", 2021, 649.0, 45.0, "Philips", 6),
            new Television("NanoVision Max", 2023, 1399.0, 58.0, "Philips", 8)
    );

    private static List<Television> filter(Predicate<Television> condition) {
        return INVENTORY.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    private static void show(String title, List<Television> tvs) {
        System.out.println(title + ":");
        tvs.forEach(tv -> System.out.println(" - " + tv));
        System.out.println();
    }

    public static void run() {
        System.out.println("Task 3:");

        show("All televisions", INVENTORY);

        double targetDiagonal = 55.0;
        show("Diagonal " + targetDiagonal + "\"", filter(tv -> tv.getDiagonal() == targetDiagonal));

        String targetMaker = "Samsung";
        show("Manufacturer " + targetMaker, filter(tv -> tv.getManufacturer().equalsIgnoreCase(targetMaker)));

        int currentYear = Year.now().getValue();
        show("Current year " + currentYear, filter(tv -> tv.getYear() == currentYear));

        double minPrice = 1000.0;
        show("Price > " + minPrice, filter(tv -> tv.getPrice() > minPrice));

        Comparator<Television> byPrice = Comparator.comparingDouble(Television::getPrice);
        show("Sorted by price (asc)", INVENTORY.stream()
                .sorted(byPrice)
                .collect(Collectors.toList()));
        show("Sorted by price (desc)", INVENTORY.stream()
                .sorted(byPrice.reversed())
                .collect(Collectors.toList()));

        Comparator<Television> byDiagonal = Comparator.comparingDouble(Television::getDiagonal);
        show("Sorted by diagonal (asc)", INVENTORY.stream()
                .sorted(byDiagonal)
                .collect(Collectors.toList()));

        Predicate<Television> userCriteria = tv -> tv.getManufacturer().equalsIgnoreCase("LG")
                && tv.getDiagonal() >= 50
                && tv.getPrice() <= 1500;
        Comparator<Television> byRating = Comparator.comparingInt(Television::getRating);

        List<Television> matched = filter(userCriteria);
        matched.stream().min(byRating).ifPresent(tv -> System.out.println("Worst by rating: " + tv));
        matched.stream().max(byRating).ifPresent(tv -> System.out.println("Best by rating: " + tv));
        System.out.println();
    }
}
