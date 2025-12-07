import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter directory path: ");
        Path dir = Path.of(scanner.nextLine().trim()).toAbsolutePath().normalize();

        System.out.print("Enter search word: ");
        String searchWord = scanner.nextLine().trim();

        System.out.print("Enter path to banned words file: ");
        Path bannedPath = Path.of(scanner.nextLine().trim()).toAbsolutePath().normalize();

        System.out.print("Enter path for merged output file: ");
        Path outputPath = Path.of(scanner.nextLine().trim()).toAbsolutePath().normalize();

        if (searchWord.isEmpty()) {
            System.out.println("Search word cannot be empty.");
            return;
        }
        if (!Files.isDirectory(dir)) {
            System.out.println("Provided path is not a directory: " + dir);
            return;
        }
        if (!Files.exists(bannedPath)) {
            System.out.println("Banned words file not found: " + bannedPath);
            return;
        }

        CountDownLatch mergedReady = new CountDownLatch(1);
        Stats stats = new Stats();

        Thread mergeThread = new Thread(() -> mergeFiles(dir, searchWord, outputPath, stats, mergedReady), "Merger");
        Thread censorThread = new Thread(() -> censorOutput(outputPath, bannedPath, stats, mergedReady), "Censor");

        long start = System.currentTimeMillis();
        mergeThread.start();
        censorThread.start();

        try {
            mergeThread.join();
            censorThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted.");
            return;
        }
        long duration = System.currentTimeMillis() - start;

        System.out.println("Directory: " + dir);
        System.out.println("Search word: " + searchWord);
        System.out.println("Files scanned: " + stats.filesScanned);
        System.out.println("Files merged: " + stats.filesMerged);
        System.out.println("Banned words removed: " + stats.bannedRemoved);
        System.out.println("Output file: " + outputPath);
        System.out.println("Time: " + duration + " ms");
        if (!stats.errors.isEmpty()) {
            System.out.println("Errors:");
            stats.errors.forEach(err -> System.out.println(" - " + err));
        }
    }

    private static void mergeFiles(Path dir, String searchWord, Path output, Stats stats, CountDownLatch latch) {
        try {
            if (output.getParent() != null) {
                Files.createDirectories(output.getParent());
            }
        } catch (IOException e) {
            stats.errors.add("Failed to create output dirs: " + e.getMessage());
            latch.countDown();
            return;
        }

        Pattern wordPattern = Pattern.compile(Pattern.quote(searchWord));
        try (var stream = Files.walk(dir)) {
            List<String> allLines = new ArrayList<>();
            List<Path> matchedFiles = stream
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            for (Path file : matchedFiles) {
                stats.filesScanned++;
                try {
                    List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
                    boolean contains = lines.stream().anyMatch(line -> wordPattern.matcher(line).find());
                    if (contains) {
                        stats.filesMerged++;
                        allLines.add("=== " + file.toString() + " ===");
                        allLines.addAll(lines);
                    }
                } catch (IOException e) {
                    stats.errors.add("Failed to read " + file + ": " + e.getMessage());
                }
            }
            Files.write(output, allLines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            stats.errors.add("Merge failed: " + e.getMessage());
        } finally {
            latch.countDown();
        }
    }

    private static void censorOutput(Path output, Path bannedPath, Stats stats, CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        if (!Files.exists(output)) {
            stats.errors.add("Merged file not found, cannot censor.");
            return;
        }

        List<String> bannedWords;
        try {
            bannedWords = Files.readAllLines(bannedPath, StandardCharsets.UTF_8).stream()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            stats.errors.add("Failed to read banned words: " + e.getMessage());
            return;
        }
        if (bannedWords.isEmpty()) {
            stats.errors.add("Banned words list is empty.");
            return;
        }

        String content;
        try {
            content = Files.readString(output, StandardCharsets.UTF_8);
        } catch (IOException e) {
            stats.errors.add("Failed to read merged file: " + e.getMessage());
            return;
        }

        String censored = content;
        for (String word : bannedWords) {
            if (word.isEmpty()) {
                continue;
            }
            Pattern p = Pattern.compile(Pattern.quote(word));
            Matcher m = p.matcher(censored);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                stats.bannedRemoved++;
                m.appendReplacement(sb, "*".repeat(m.end() - m.start()));
            }
            m.appendTail(sb);
            censored = sb.toString();
        }

        try {
            Files.writeString(output, censored, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            stats.errors.add("Failed to write censored output: " + e.getMessage());
        }
    }

    private static class Stats {
        int filesScanned = 0;
        int filesMerged = 0;
        int bannedRemoved = 0;
        List<String> errors = new ArrayList<>();
    }
}
