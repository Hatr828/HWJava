import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter path to the first file: ");
        Path first = Path.of(scanner.nextLine().trim());
        System.out.print("Enter path to the second file: ");
        Path second = Path.of(scanner.nextLine().trim());

        try {
            List<String> firstLines = Files.readAllLines(first);
            List<String> secondLines = Files.readAllLines(second);

            if (firstLines.equals(secondLines)) {
                System.out.println("Files are identical line by line.");
                return;
            }

            int max = Math.max(firstLines.size(), secondLines.size());
            System.out.println("Non-matching lines:");
            for (int i = 0; i < max; i++) {
                String fromFirst = i < firstLines.size() ? firstLines.get(i) : null;
                String fromSecond = i < secondLines.size() ? secondLines.get(i) : null;
                if (!Objects.equals(fromFirst, fromSecond)) {
                    int humanIndex = i + 1;
                    System.out.println("Line " + humanIndex + ":");
                    System.out.println("  First : " + fromFirst);
                    System.out.println("  Second: " + fromSecond);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
