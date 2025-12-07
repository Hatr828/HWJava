import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter path to the numbers file: ");
        Path numbersPath = Path.of(scanner.nextLine().trim());

        System.out.print("How many random integers to generate: ");
        int count = readInt(scanner);

        System.out.print("Max value for random integers (positive): ");
        int maxValue = readInt(scanner);
        if (maxValue <= 0) {
            maxValue = 20;
        }
        final int finalMaxValue = maxValue;

        List<Integer> numbers = new ArrayList<>(count);
        CountDownLatch filled = new CountDownLatch(1);
        AtomicInteger primesFound = new AtomicInteger(0);
        AtomicInteger factorialsComputed = new AtomicInteger(0);

        Path primesPath = withSuffix(numbersPath, "_primes.txt");
        Path factorialsPath = withSuffix(numbersPath, "_factorials.txt");

        Thread writerThread = new Thread(() -> {
            Random random = new Random();
            for (int i = 0; i < count; i++) {
                numbers.add(random.nextInt(finalMaxValue) + 1);
            }
            writeNumbers(numbersPath, numbers);
            filled.countDown();
            System.out.println("Numbers file written: " + numbersPath);
        }, "Writer");

        Thread primeThread = new Thread(() -> {
            awaitLatch(filled);
            List<Integer> primes = numbers.stream()
                    .filter(Task2::isPrime)
                    .collect(Collectors.toList());
            primesFound.set(primes.size());
            writeLines(primesPath, primes.stream().map(String::valueOf).toList());
        }, "PrimeFinder");

        Thread factorialThread = new Thread(() -> {
            awaitLatch(filled);
            List<String> lines = new ArrayList<>();
            for (int n : numbers) {
                lines.add(n + "! = " + factorial(n));
                factorialsComputed.incrementAndGet();
            }
            writeLines(factorialsPath, lines);
        }, "FactorialCalculator");

        primeThread.start();
        factorialThread.start();
        writerThread.start();

        try {
            writerThread.join();
            primeThread.join();
            factorialThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Execution interrupted.");
            return;
        }

        System.out.println("Generated numbers: " + numbers.size());
        System.out.println("Primes found: " + primesFound.get() + " (saved to " + primesPath + ")");
        System.out.println("Factorials computed: " + factorialsComputed.get() + " (saved to " + factorialsPath + ")");
    }

    private static void writeNumbers(Path path, List<Integer> numbers) {
        String content = numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
        writeLines(path, List.of(content));
    }

    private static void writeLines(Path path, List<String> lines) {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("Failed to write " + path + ": " + e.getMessage());
        }
    }

    private static void awaitLatch(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid integer: ");
            }
        }
    }

    private static Path withSuffix(Path original, String suffix) {
        String fileName = original.getFileName().toString();
        int dot = fileName.lastIndexOf('.');
        String base = dot >= 0 ? fileName.substring(0, dot) : fileName;
        String ext = dot >= 0 ? fileName.substring(dot) : "";
        String newName = base + suffix + ext;
        Path parent = original.getParent();
        return parent == null ? Path.of(newName) : parent.resolve(newName);
    }

    private static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static BigInteger factorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}
