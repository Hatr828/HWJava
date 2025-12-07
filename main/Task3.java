import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter source directory path: ");
        Path source = Path.of(scanner.nextLine().trim()).toAbsolutePath().normalize();

        System.out.print("Enter destination directory path: ");
        Path destination = Path.of(scanner.nextLine().trim()).toAbsolutePath().normalize();

        if (!Files.isDirectory(source)) {
            System.out.println("Source directory does not exist: " + source);
            return;
        }
        if (source.equals(destination) || destination.startsWith(source)) {
            System.out.println("Destination must be different.");
            return;
        }

        CopyStats stats = new CopyStats();
        Thread worker = new Thread(() -> copyDirectory(source, destination, stats), "DirectoryCopier");

        long start = System.currentTimeMillis();
        worker.start();
        try {
            worker.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Copy interrupted.");
            return;
        }
        long duration = System.currentTimeMillis() - start;

        System.out.println("Source: " + source);
        System.out.println("Destination: " + destination);
        System.out.println("Directories created: " + stats.directories);
        System.out.println("Files copied: " + stats.files);
        System.out.println("Bytes copied: " + stats.bytes);
        System.out.println("Time: " + duration + " ms");
        if (!stats.errors.isEmpty()) {
            System.out.println("Errors (" + stats.errors.size() + "):");
            stats.errors.stream().limit(5).forEach(err -> System.out.println(" - " + err));
        }
    }

    private static void copyDirectory(Path source, Path destination, CopyStats stats) {
        try (var stream = Files.walk(source)) {
            stream.forEach(path -> {
                Path target = destination.resolve(source.relativize(path));
                try {
                    if (Files.isDirectory(path)) {
                        Files.createDirectories(target);
                        stats.directories++;
                    } else {
                        if (target.getParent() != null) {
                            Files.createDirectories(target.getParent());
                        }
                        long size = Files.size(path);
                        Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                        stats.files++;
                        stats.bytes += size;
                    }
                } catch (IOException e) {
                    stats.errors.add("Failed to copy " + path + ": " + e.getMessage());
                }
            });
        } catch (IOException e) {
            stats.errors.add("Failed to traverse source directory: " + e.getMessage());
        }
    }

    private static class CopyStats {
        int directories = 0;
        int files = 0;
        long bytes = 0;
        List<String> errors = new ArrayList<>();
    }
}
