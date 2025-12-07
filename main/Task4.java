import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter path to the file: ");
        Path path = Path.of(scanner.nextLine().trim());
        System.out.print("Enter banned words: ");
        List<String> bannedWords = Arrays.stream(scanner.nextLine().trim().split("\\s+"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        if (bannedWords.isEmpty()) {
            System.out.println("No banned words provided.");
            return;
        }

        try {
            Map<String, Integer> counts = new LinkedHashMap<>();
            for (String word : bannedWords) {
                counts.putIfAbsent(word, 0);
            }

            List<String> lines = Files.readAllLines(path);
            StringBuilder result = new StringBuilder();
            Pattern tokenPattern = Pattern.compile("\\S+|\\s+"); 

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                Matcher matcher = tokenPattern.matcher(line);
                while (matcher.find()) {
                    String token = matcher.group();
                    if (token.trim().isEmpty()) {
                        result.append(token);
                        continue;
                    }
                    if (counts.containsKey(token)) {
                        counts.put(token, counts.get(token) + 1);
                        result.append("*".repeat(token.length()));
                    } else {
                        result.append(token);
                    }
                }
                if (i < lines.size() - 1) {
                    result.append(System.lineSeparator());
                }
            }

            Files.writeString(path, result.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            int total = counts.values().stream().mapToInt(Integer::intValue).sum();
            System.out.println("Censorship report:");
            System.out.println("Total removed words: " + total);
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Failed to process the file: " + e.getMessage());
        }
    }
}
