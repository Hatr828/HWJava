import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter path to the file: ");
        Path path = Path.of(scanner.nextLine().trim());

        try {
            List<String> lines = Files.readAllLines(path);
            if (lines.isEmpty()) {
                System.out.println("File is empty.");
                return;
            }

            String longestLine = "";
            for (String line : lines) {
                if (line.length() > longestLine.length()) {
                    longestLine = line;
                }
            }

            System.out.println("Longest line (" + longestLine.length() + " characters):");
            System.out.println(longestLine);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
