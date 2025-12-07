import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter array size: ");
        int size = readInt(scanner);

        if (size < 0) {
            size = 0;
        }

        int[] data = new int[size];
        CountDownLatch filled = new CountDownLatch(1);
        AtomicLong sumResult = new AtomicLong(0);
        AtomicReference<Double> averageResult = new AtomicReference<>(0.0);

        Thread fillThread = new Thread(() -> {
            Random random = new Random();
            for (int i = 0; i < data.length; i++) {
                data[i] = random.nextInt(100); // 0-99
            }
            filled.countDown();
            System.out.println("Array filled.");
        }, "Filler");

        Thread sumThread = new Thread(() -> {
            awaitLatch(filled);
            long sum = 0;
            for (int value : data) {
                sum += value;
            }
            sumResult.set(sum);
        }, "SumCalculator");

        Thread averageThread = new Thread(() -> {
            awaitLatch(filled);
            if (data.length == 0) {
                averageResult.set(0.0);
                return;
            }
            long sum = 0;
            for (int value : data) {
                sum += value;
            }
            averageResult.set(sum / (double) data.length);
        }, "AverageCalculator");

        sumThread.start();
        averageThread.start();
        fillThread.start();

        try {
            fillThread.join();
            sumThread.join();
            averageThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Execution interrupted.");
            return;
        }

        System.out.println("Array: " + Arrays.toString(data));
        System.out.println("Sum: " + sumResult.get());
        System.out.println("Average: " + averageResult.get());
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
}
