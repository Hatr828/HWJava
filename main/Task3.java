import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Task3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter path for the new file: ");
        Path path = Path.of(scanner.nextLine().trim());
        System.out.print("Enter ints: ");
        String[] parts = scanner.nextLine().trim().split("\\s+");

        if (parts.length == 1 && parts[0].isEmpty()) {
            return;
        }

        int[] numbers = new int[parts.length];
        try {
            for (int i = 0; i < parts.length; i++) {
                numbers[i] = Integer.parseInt(parts[i]);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error, not int. " + e.getMessage());
            return;
        }

        List<String> output = new ArrayList<>();
        output.add(join(numbers));
        output.add(join(filter(numbers, true)));
        output.add(join(filter(numbers, false)));
        output.add(join(reverse(numbers)));

        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, output, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Data saved to " + path);
        } catch (IOException e) {
            System.out.println("Failed to write: " + e.getMessage());
        }
    }

    private static String join(int[] numbers) {
        return java.util.Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));
    }

    private static int[] filter(int[] numbers, boolean even) {
        return java.util.Arrays.stream(numbers)
                .filter(n -> even == (n % 2 == 0))
                .toArray();
    }

    private static int[] reverse(int[] numbers) {
        int[] reversed = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            reversed[i] = numbers[numbers.length - 1 - i];
        }
        return reversed;
    }
}
